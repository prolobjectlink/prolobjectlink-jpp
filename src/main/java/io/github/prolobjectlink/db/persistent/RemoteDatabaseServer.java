/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.persistent;

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

import io.github.prolobjectlink.AbstractPlatform;
import io.github.prolobjectlink.Platform;
import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.DatabaseSchema;
import io.github.prolobjectlink.db.DatabaseServer;
import io.github.prolobjectlink.db.DatabaseServerType;
import io.github.prolobjectlink.db.DatabaseType;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.Protocol;
import io.github.prolobjectlink.db.Schema;
import io.github.prolobjectlink.db.StorageGraph;
import io.github.prolobjectlink.db.StorageManager;
import io.github.prolobjectlink.db.StorageMode;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.util.JavaMaps;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public class RemoteDatabaseServer extends AbstractPlatform implements Platform, DatabaseServer {

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

	protected RemoteDatabaseServer(Settings settings, DatabaseUser systemAdmin, int serverPort) {
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
		String relational = "db/pdb/relational";
		File rdat = new File(relational);
		File[] files = rdat.listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName();

				// create schema instance at "name" location an load
				Schema schema = new DatabaseSchema(relational + "/" + name, settings.getProvider(), settings,
						systemAdmin).load();

				StorageGraph storage = settings.createStorageGraph(relational + "/" + name + "/database", schema,
						StorageMode.STORAGE_POOL);

				// create remote URL to embedded URL
				URL embeddedURL = new URL("file:/" + relational + "/" + name);

				relationals.put(name, new EmbeddedRelational(name, embeddedURL, schema, systemAdmin, storage));
			}
		}
		return relationals;
	}

	public Map<String, DatabaseEngine> hierarchicals() throws IOException {
		Map<String, DatabaseEngine> hierarchicals = JavaMaps.hashMap();

		// load hierarchical databases
		String hierarchical = "db/pdb/hierarchical";
		File hdat = new File(hierarchical);
		File[] files = hdat.listFiles();
		if (files != null) {
			for (File file : files) {
				String name = file.getName();

				// create schema instance at "name" location an load
				Schema schema = new DatabaseSchema(hierarchical + "/" + name, settings.getProvider(), settings,
						systemAdmin).load();

				// create storage instance at "name" location
				StorageManager storage = settings.createStorageManager(hierarchical + "/" + name + "/database",
						StorageMode.STORAGE_POOL);

				// create remote URL to embedded URL
				URL embeddedURL = new URL("file:/" + hierarchical + "/" + name);

				// create database using storage and schema
				hierarchicals.put(name, new EmbeddedHierarchical(name, embeddedURL, schema, systemAdmin, storage));
			}
		}
		return hierarchicals;
	}

	public final boolean isRunning() {
		return serverSocket != null;
	}

	public final synchronized void shutdown() {
		stop = true;
	}

	public final String getURL() {
		try {
			return "rempdb://" + InetAddress.getLocalHost() + ":" + serverPort;
		} catch (UnknownHostException e) {
			LoggerUtils.error(getClass(), LoggerConstants.UNKNOWN_HOST, e);
		}
		return "Unknown Host";
	}

	public String getVersion() {
		return "1.0.0";
	}

	public final String getName() {
		return "Prolobjectlink Server";
	}

	public final DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	public final synchronized void startup() throws IOException {

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
