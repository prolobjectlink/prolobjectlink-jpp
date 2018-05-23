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
package org.logicware.pdb.persistent;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EnumMap;
import java.util.Map;

import org.logicware.pdb.AbstractWrapper;
import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseRequest;
import org.logicware.pdb.DatabaseResponse;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.Wrapper;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public final class RemoteDatabaseThread extends AbstractWrapper implements Runnable, Wrapper {

	private final int id;
	private Socket socket;
	private DatabaseEngine database;
	private RemoteDatabaseServer server;
	private final EnumMap<DatabaseType, Map<String, DatabaseEngine>> dbTypes;
	private static Deque<DatabaseRequest> queue = new ArrayDeque<DatabaseRequest>();

	RemoteDatabaseThread(int id, Socket socket, RemoteDatabaseServer server) {
		this.dbTypes = new EnumMap<DatabaseType, Map<String, DatabaseEngine>>(DatabaseType.class);
		this.socket = socket;
		this.server = server;
		this.id = id;
	}

	void putDatabases(DatabaseType type, Map<String, DatabaseEngine> databases) {
		dbTypes.put(type, databases);
	}

	Map<String, DatabaseEngine> getDatabases(DatabaseType type) {
		return dbTypes.get(type);
	}

	public final Socket getSocket() {
		return socket;
	}

	public final void setSocket(Socket socket) {
		this.socket = socket;
	}

	public final RemoteDatabaseServer getServer() {
		return server;
	}

	public final void setServer(RemoteDatabaseServer server) {
		this.server = server;
	}

	public void run() {

		try {

			LoggerUtils.debug(getClass(), "request " + id + " at " + socket.getInetAddress().getHostAddress()
					+ " on port " + socket.getPort());

//			LoggerUtils.debug(getClass(), ">> " + queue);

			DatabaseResponse response = new DatabaseResponse();

			ObjectOutputStream serverOutputStream = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream serverInputStream = new ObjectInputStream(socket.getInputStream());

			// read data from client
			DatabaseRequest request = unwrap(serverInputStream.readObject(), DatabaseRequest.class);

			LoggerUtils.debug(getClass(), request);

			// server database operations area
			// (insert,update,delete,query,...)

			Transaction tx = null;

			switch (request.getType()) {
			case CONNECT:
				DatabaseType t = request.getDatabaseType();
				Map<String, DatabaseEngine> dbs = dbTypes.get(t);
				database = dbs.get(request.getDatabaseName());
				if (database == null) {
					response.set(false);
				} else {
					response.set(true);
				}
				// write data back to client
				LoggerUtils.debug(getClass(), response);
				serverOutputStream.writeObject(response);
				break;
			case CONSTAINS:
				Serializable a = request.getArgument(0);
				boolean b = database.contains(a);
				response.set(b);
				break;
			case QUERY:
				Serializable k1 = request.getArgument(0);
				response.set(database.createQuery(k1));
				break;
			case PROCEDURE:
				Serializable a1 = request.getArgument(0);
				Serializable a2 = request.getArgument(1);
				String functor = (String) a1;
				String[] args = (String[]) a2;
				response.set(database.createProcedureQuery(functor, args));
				break;
			case CREATE:
				database.create();
				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case BEGIN:
				tx = database.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case DROP:
				database.drop();
				break;
			case CLEAR:
				database.clear();
				database.commit();
				break;
			case INSERT:

				Class<?> ca = (Class<?>) request.getArgument(0);
				Serializable[] sa = (Serializable[]) request.getArgument(1);
				PersistentContainer pc = database.containerOf(ca);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				pc.insert(sa);
				tx.commit();
				tx.close();

				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);

				break;
			case UPDATE:
				Serializable j = request.getArgument(0);
				Serializable[] sb = (Serializable[]) j;
				if (sb.length == 2) {
					Serializable m = sb[0];
					Serializable u = sb[1];
					database.update(m, u);
					database.commit();
				}
				break;
			case DELETE:
				Serializable c = request.getArgument(0);
				database.delete(c);
				database.commit();
				break;
			case INCLUDE:
				Serializable p = request.getArgument(0);
				String path = (String) p;
				Serializable s = request.getArgument(1);
				StringBuilder pl = (StringBuilder) s;
				// create file at some location in server
				String location = "upl" + path;
				File toBeInclude = new File(location);
				PrintWriter w = new PrintWriter(toBeInclude);
				w.write("" + pl + "");
				w.close();
				database.include(location);
				break;
			case BACKUP:
				// database.backup(directory, zipFileName);
				// send back zipFileName
				break;
			case RESTORE:
				// get client backup
				// database.restore(directory, zipFileName);
				response.setVoid();
				break;
			case COMMIT:
//				database.commit();
				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);

				break;
			case ROLLBACK:
//				database.rollback();
				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);

				break;
			case CLOSE:
//				database.close();
				response.setVoid();
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			default:

				queue.add(request);

				// request add result
				response.setVoid();

				// write data back to client
				serverOutputStream.writeObject(response);
				LoggerUtils.debug(getClass(), response);

				break;
			}

			// close in/out streams
			serverInputStream.close();
			serverOutputStream.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		}

	}

}
