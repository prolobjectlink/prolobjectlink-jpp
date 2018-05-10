/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;

import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseServer;
import org.logicware.pdb.DatabaseServerType;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public class AbstractDatabaseServer implements DatabaseServer {

	protected int port;
	protected volatile int nextId;
	protected volatile boolean stop;
	protected Thread listenerThread;
	protected ExecutorService running;
	protected ServerSocket serverSocket;
	protected SocketAddress socketAddress;
	protected DatabaseEngine database;
	protected static DatabaseServer server;

	public boolean isRunning() {
		return serverSocket != null;
	}

	public void shutdown() throws InterruptedException {
		running.shutdown();
		stop = true;
		if (listenerThread != null) {
			try {
				listenerThread.join(1000);
			} catch (InterruptedException e) {
				LoggerUtils.error(getClass(), LoggerConstants.INTERRUPTED_ERROR, e);
				Thread.currentThread().interrupt();
			}
		}
	}

	public String getURL() {
		try {
			return "rempdb://" + InetAddress.getLocalHost() + ":" + port;
		} catch (UnknownHostException e) {
			LoggerUtils.error(getClass(), LoggerConstants.UNKNOWN_HOST, e);
		}
		return "Unknown Host";
	}

	public String getName() {
		return "Prolobjectlink TCP Server";
	}

	public DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	public void startup() throws IOException {
		listenerThread = Thread.currentThread();
		while (!stop && serverSocket != null) {
			running.submit(new DatabaseThread(database, serverSocket, nextId++));
		}

		if (serverSocket != null) {
			serverSocket.close();
			serverSocket = null;
		}
	}

}
