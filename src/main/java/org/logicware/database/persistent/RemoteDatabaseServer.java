/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.database.persistent;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.logicware.AbstractPlatform;
import org.logicware.Platform;
import org.logicware.database.DatabaseEngine;
import org.logicware.database.DatabaseSchema;
import org.logicware.database.DatabaseServer;
import org.logicware.database.DatabaseServerType;
import org.logicware.database.DatabaseType;
import org.logicware.database.DatabaseUser;
import org.logicware.database.Protocol;
import org.logicware.database.Schema;
import org.logicware.database.Settings;
import org.logicware.database.StorageGraph;
import org.logicware.database.StorageManager;
import org.logicware.database.StorageMode;
import org.logicware.database.util.JavaMaps;
import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;

public final class RemoteDatabaseServer extends AbstractPlatform implements Platform, DatabaseServer {

	private int threadId;
	private int serverPort;
	private volatile boolean stop;
	private final Settings settings;
	private ServerSocket serverSocket;
	private final ExecutorService running;
	private static DatabaseServer server;
	private final DatabaseUser systemAdmin;
	private final Map<String, RemoteDatabaseThread> threads;

	private static final String PROTOCOL = Protocol.class.getPackage().getName();

	public static final DatabaseServer getInstance(Settings settings, DatabaseUser systemAdmin) {
		if (server == null) {
			server = new RemoteDatabaseServer(settings, systemAdmin, 5370);
		}
		return server;
	}

	private RemoteDatabaseServer(Settings settings, DatabaseUser systemAdmin, int serverPort) {
		this.threads = new HashMap<String, RemoteDatabaseThread>();
		this.running = Executors.newCachedThreadPool();
		this.systemAdmin = systemAdmin;
		this.serverPort = serverPort;
		this.settings = settings;
		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocket(serverPort);
				Runtime.getRuntime().addShutdownHook(new Thread() {
					@Override
					public void run() {
						shutdown();
					}
				});
			} catch (IOException e) {
				LoggerUtils.error(getClass(), LoggerConstants.IO, e);
			}
		}
	}

	public Map<String, DatabaseEngine> relationals() throws IOException {
		Map<String, DatabaseEngine> relationals = JavaMaps.hashMap();

		// load relational databases
		String relational = "dat/relational";
		File rdat = new File(relational);
		for (File file : rdat.listFiles()) {
			String name = file.getName();

			// create schema instance at "name" location an load
			Schema schema = new DatabaseSchema(relational + "/" + name, settings.getProvider(), settings, systemAdmin)
					.load();

			StorageGraph storage = settings.createStorageGraph(relational + "/" + name + "/database", schema,
					StorageMode.STORAGE_POOL);

			// create remote URL to embedded URL
			URL embeddedURL = new URL("file:/" + relational + "/" + name);

			relationals.put(name, new EmbeddedRelational(name, embeddedURL, schema, systemAdmin, storage));
		}
		return relationals;
	}

	public Map<String, DatabaseEngine> hierarchicals() throws IOException {
		Map<String, DatabaseEngine> hierarchicals = JavaMaps.hashMap();

		// load hierarchical databases
		String hierarchical = "dat/hierarchical";
		File hdat = new File(hierarchical);
		for (File file : hdat.listFiles()) {
			String name = file.getName();

			// create schema instance at "name" location an load
			Schema schema = new DatabaseSchema(hierarchical + "/" + name, settings.getProvider(), settings, systemAdmin)
					.load();

			// create storage instance at "name" location
			StorageManager storage = settings.createStorageManager(hierarchical + "/" + name + "/database",
					StorageMode.STORAGE_POOL);

			// create remote URL to embedded URL
			URL embeddedURL = new URL("file:/" + hierarchical + "/" + name);

			// create database using storage and schema
			hierarchicals.put(name, new EmbeddedHierarchical(name, embeddedURL, schema, systemAdmin, storage));
		}
		return hierarchicals;
	}

	public boolean isRunning() {
		return serverSocket != null;
	}

	public synchronized void shutdown() {
		stop = true;
	}

	public String getURL() {
		try {
			return "rempdb://" + InetAddress.getLocalHost() + ":" + serverPort;
		} catch (UnknownHostException e) {
			LoggerUtils.error(getClass(), LoggerConstants.UNKNOWN_HOST, e);
		}
		return "Unknown Host";
	}

	public String getName() {
		return "Prolobjectlink Server";
	}

	public DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	public synchronized void startup() throws IOException {

		LoggerUtils.info(getClass(), getName() + " is starting...");

		// protocol system register
		System.setProperty("java.protocol.handler.pkgs", PROTOCOL);

		// load existing databases
		Map<String, DatabaseEngine> hierarchicals = hierarchicals();
		Map<String, DatabaseEngine> relationals = relationals();

		LoggerUtils.info(getClass(), getName() + " is started");

		// listening loop
		while (!stop && serverSocket != null) {
			Socket socket = serverSocket.accept();
			String clientAddress = socket.getInetAddress().getHostAddress();
			RemoteDatabaseThread thread = threads.get(clientAddress);
			if (thread == null) {
				thread = new RemoteDatabaseThread(threadId++, socket, this);
				thread.putDatabases(DatabaseType.HIERARCHICAL, hierarchicals);
				thread.putDatabases(DatabaseType.RELATIONAL, relationals);
				threads.put(clientAddress, thread);
			}
			thread.setSocket(socket);
			thread.setServer(this);
			running.submit(thread);
		}

		LoggerUtils.info(getClass(), getName() + " is shutting down...");

		// close all databases after stop listening loop
		for (DatabaseEngine e : hierarchicals.values()) {
			e.close();
		}
		hierarchicals.clear();
		for (DatabaseEngine e : relationals.values()) {
			e.close();
		}
		relationals.clear();

		// shutdown the executor
		running.shutdown();

		// clear active threads
		threads.clear();

		// close server socket
		if (serverSocket != null) {
			serverSocket.close();
			serverSocket = null;
		}

		LoggerUtils.info(getClass(), getName() + " is shutdown");

	}

}
