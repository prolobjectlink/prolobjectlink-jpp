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
package org.logicware.pdb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class DatabaseServer {

	private static final int N = 100;
	private static final int PORT = 5370;

	public static void main(String[] args) throws IOException {
		ServerSocket socket = new ServerSocket(PORT);
		while (true) {
			final Socket connection = socket.accept();
			Runnable task = new Runnable() {
				public void run() {

					// TODO
				}
			};
			Executors.newFixedThreadPool(N).execute(task);
		}
	}
}
