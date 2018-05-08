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
import java.io.InputStream;
import java.io.OutputStream;
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

	private static final int BUFSIZE = 32;

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
			Socket s = serverSocket.accept();
			TCPDatabaseThread c = new TCPDatabaseThread(s, this, nextThreadId++);
			Thread thread = new Thread(c, name + " thread");
			thread.setDaemon(isDaemon);
			thread.start();
			running.add(c);
		}
		serverSocket.close();
	}

	public void stop() throws IOException {
		if (!stop) {
			stop = true;
			if (serverSocket != null) {
				serverSocket.close();
				serverSocket = null;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");
		int servPort = Integer.parseInt(args[0]);
		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);
		int recvMsgSize; // Size of received message
		byte[] byteBuffer = new byte[BUFSIZE]; // Receive buffer
		for (;;) { // Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept();
			// Get client connection
			System.out.println("Handling client at " + clntSock.getInetAddress().getHostAddress() + " on port "
					+ clntSock.getPort());
			InputStream in = clntSock.getInputStream();
			OutputStream out = clntSock.getOutputStream();
			// Receive until client closes connection, indicated by-i return
			while ((recvMsgSize = in.read(byteBuffer)) != -1)
				out.write(byteBuffer, 0, recvMsgSize);
			clntSock.close();
			// Close the socket. We are done with this client!
		}
		/* NOT REACHED */
	}

}
