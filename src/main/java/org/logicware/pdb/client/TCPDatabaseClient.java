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
package org.logicware.pdb.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.logicware.pdb.AbstractWrapper;
import org.logicware.pdb.DatabaseClient;
import org.logicware.pdb.DatabaseSession;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public class TCPDatabaseClient extends AbstractWrapper implements DatabaseClient, DatabaseSession {

	private Socket socket;
	private final int port;
	private final String server;
	private final Transaction transaction;

	private static byte[] byteBuffer;

	public TCPDatabaseClient(String server, int port) {
		this.transaction = new DefaultTransaction(this);
		this.server = server;
		this.port = port;
	}

	public final void begin() {
		try {
			socket = new Socket(server, port);
		} catch (UnknownHostException e) {
			LoggerUtils.error(getClass(), LoggerConstants.UNKNOWN_HOST, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		}
	}

	public final void commit() {

		System.out.println("Connected to server...sending echo string");

		try {

			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(byteBuffer);

			int bytesRcvd;
			int totalBytesRcvd = 0;
			while (totalBytesRcvd < byteBuffer.length) {
				if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd, byteBuffer.length - totalBytesRcvd)) == -1)
					throw new SocketException("Connection closed prematurely");
				totalBytesRcvd += bytesRcvd;
			}

			out.close();
			in.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		}

		System.out.println("Received: " + new String(byteBuffer));
	}

	public final void rollback() {
		// TODO Auto-generated method stub

	}

	public final Transaction geTransaction() {
		return transaction;
	}

	public final void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// do nothing
			} finally {
				socket = null;
			}
		}
	}

	public static void main(String[] args) {

		// Test for correct # of args
		if ((args.length < 2) || (args.length > 3))
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

		String server = args[0];
		int servPort = (args.length == 3) ? Integer.parseInt(args[1]) : 7;
		byteBuffer = args[2].getBytes();

		TCPDatabaseClient c = new TCPDatabaseClient(server, servPort);
		c.geTransaction().begin();
		c.geTransaction().commit();
		c.geTransaction().close();
		c.close();

	}
}
