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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import org.logicware.pdb.AbstractWrapper;
import org.logicware.pdb.DataTransferObject;
import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.Wrapper;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public class DatabaseThread extends AbstractWrapper implements Runnable, Wrapper {

	private final int id;
	private final Socket socket;
	private final DatabaseEngine database;

	public DatabaseThread(DatabaseEngine database, ServerSocket serverSocket, int id) throws IOException {
		this.socket = serverSocket.accept();
		this.database = database;
		this.id = id;
	}

	public void run() {

		try {

			System.out.println("Handling client " + id + " at " + socket.getInetAddress().getHostAddress() + " on port "
					+ socket.getPort());

			ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());

			// read data from client
			DataTransferObject<?, ?> dto = unwrap(serverInputStream.readObject(), DataTransferObject.class);

			Serializable serializable = dto.getQuery();

			// server database work area
			// (insert,update,delete,find)

			switch (dto.getType()) {
			case SCHEMA:

				break;
			case CREATE:
				database.create();
//				dto.setResult(TRUE);
				break;
			case DROP:
				database.drop();
//				dto.setResult(TRUE);
				break;
			case CONSTAINS:
				database.contains(serializable);
				break;
			case INSERT:
//				session.insert(serializable);
//				dto.setResult(TRUE);
				break;
			case UPDATE:

//				dto.setResult(TRUE);
				break;
			case DELETE:
				database.delete(serializable);
//				dto.setResult(TRUE);
				break;
			case FIND:

				break;
			default:
				break;
			}

			System.out.println(dto);

			// write data back to client
			serverOutputStream.writeObject(dto);

			serverInputStream.close();
			serverOutputStream.close();

			socket.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		}

	}

}
