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

import org.logicware.pdb.DatabaseClient;

public class TCPDatabaseClient implements DatabaseClient {

	public static void main(String[] args) throws IOException {

		// Test for correct # of args
		if ((args.length < 2) || (args.length > 3))
			throw new IllegalArgumentException("Parameter(s): <Server> <Word> [<Port>]");

		while (true) {

			String server = args[0];
			// Server name or IP address
			// Convert input String to bytes using the default character
			// encoding
			byte[] byteBuffer = args[1].getBytes();
			int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
			// Create socket that is connected to server on specified port
			Socket socket = new Socket(server, servPort);
			System.out.println("Connected to server...sending echo string");
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			out.write(byteBuffer); // Send the encoded string to the server
			// Receive the same string back from the server
			int totalBytesRcvd = 0; // Total bytes received so far
			int bytesRcvd;
			// Bytes received in last read
			while (totalBytesRcvd < byteBuffer.length) {
				if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd, byteBuffer.length - totalBytesRcvd)) == -1)
					throw new SocketException("Connection closed prematurely");
				totalBytesRcvd += bytesRcvd;
			}
			System.out.println("Received: " + new String(byteBuffer));
			socket.close(); // Close the socket and its streams
		}

	}
}
