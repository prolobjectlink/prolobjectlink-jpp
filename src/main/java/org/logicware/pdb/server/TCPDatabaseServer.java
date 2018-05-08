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
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.logicware.pdb.DatabaseServer;
import org.logicware.pdb.DatabaseServerType;

public class TCPDatabaseServer implements DatabaseServer {

	private int port;
	private boolean isDaemon;
	private volatile boolean stop;
	private volatile int nextThreadId;
	private ServerSocket serverSocket;
	private final Set<TCPDatabaseThread> running;

	public TCPDatabaseServer(int port, boolean isDaemon) {
		this.running = Collections.synchronizedSet(new HashSet<TCPDatabaseThread>());
		this.isDaemon = isDaemon;
		this.port = port;
	}

	public boolean isRunning() {
		return serverSocket != null;
	}

	public boolean isDaemon() {
		return isDaemon;
	}

	public String getURL() throws UnknownHostException {
		return "rempdb://" + InetAddress.getLocalHost() + port;
	}

	public String getName() {
		return "Prolobjectlink TCP Server";
	}

	public DatabaseServerType getType() {
		return DatabaseServerType.TCP;
	}

	public void start() throws IOException {
		serverSocket = new ServerSocket(port);
		String name = Thread.currentThread().getName();
		while (!stop) {
			Socket clientSocket = serverSocket.accept();
			TCPDatabaseThread c = new TCPDatabaseThread(clientSocket, nextThreadId++);
			Thread thread = new Thread(c, name + " thread");
			thread.setDaemon(isDaemon);
			thread.start();
			running.add(c);
		}
		serverSocket.close();
		serverSocket = null;
	}

	public void stop() throws IOException {
		stop = true;
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");
		int servPort = Integer.parseInt(args[0]);
		new TCPDatabaseServer(servPort, true).start();
	}

}
