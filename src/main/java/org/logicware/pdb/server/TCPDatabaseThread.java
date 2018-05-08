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

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TCPDatabaseThread implements Runnable {

	private static final int BUFSIZE = 32;
	private final Socket socket;

	public TCPDatabaseThread(Socket socket, int i) {
		this.socket = socket;
	}

	public void run() {

		int recvMsgSize; // Size of received message
		byte[] byteBuffer = new byte[BUFSIZE]; // Receive buffer

		try {

			System.out.println(
					"Handling client at " + socket.getInetAddress().getHostAddress() + " on port " + socket.getPort());

			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();

			// Receive until client closes connection, indicated by-i return
			while ((recvMsgSize = in.read(byteBuffer)) != -1)
				out.write(byteBuffer, 0, recvMsgSize);

			socket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
