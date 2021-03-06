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
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.util.EnumMap;
import java.util.Map;

import io.github.prolobjectlink.AbstractWrapper;
import io.github.prolobjectlink.Wrapper;
import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.DatabaseRequest;
import io.github.prolobjectlink.db.DatabaseResponse;
import io.github.prolobjectlink.db.DatabaseType;
import io.github.prolobjectlink.db.PersistentContainer;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.Query;
import io.github.prolobjectlink.db.Transaction;
import io.github.prolobjectlink.db.TypedQuery;
import io.github.prolobjectlink.db.tools.Backup;
import io.github.prolobjectlink.db.tools.Restore;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public final class RemoteDatabaseThread extends AbstractWrapper implements Runnable, Wrapper {

	private final int id;
	private Socket socket;
	private DatabaseEngine database;
	private RemoteDatabaseServer server;
	private static final Backup backuper = new Backup();
	private static final Restore restorer = new Restore();
	private final EnumMap<DatabaseType, Map<String, DatabaseEngine>> dbTypes;

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

			DatabaseResponse response = new DatabaseResponse();

			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());

			// read data from client
			DatabaseRequest request = unwrap(is.readObject(), DatabaseRequest.class);

			LoggerUtils.debug(getClass(), request);

			// server database operations area
			// (insert,update,delete,query,...)

			String dir = null;
			String zip = null;
			String string = null;
			boolean exist = false;
			Class<?> clazz = null;
			Transaction tx = null;
			Predicate<?> p = null;
			Serializable[] sa = null;
			PersistentContainer pc = null;

			switch (request.getType()) {
			case CONNECT: // OK
				DatabaseType t = request.getDatabaseType();
				Map<String, DatabaseEngine> dbs = dbTypes.get(t);
				database = dbs.get(request.getDatabaseName());
				if (database == null) {
					response.set(false);
				} else {
					response.set(true);
				}
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CREATE:
				database.create();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CLEAR:
				tx = database.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				database.clear();
				tx.commit();
				tx.close();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case DROP:
				database.drop();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case BEGIN: // OK
				tx = database.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case INSERT: // OK
				clazz = (Class<?>) request.getArgument(0);
				sa = (Serializable[]) request.getArgument(1);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				pc.insert(sa);
				tx.commit();
				tx.close();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case UPDATE: // OK
				clazz = (Class<?>) request.getArgument(0);
				sa = (Serializable[]) request.getArgument(1);
				if (sa.length == 2) {
					pc = database.containerOf(clazz);
					tx = pc.getTransaction();
					if (!tx.isActive()) {
						tx.begin();
					}
					pc.update(sa[0], sa[1]);
					tx.commit();
					tx.close();
				}
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case DELETE_ARRAY: // OK
				clazz = (Class<?>) request.getArgument(0);
				sa = (Serializable[]) request.getArgument(1);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				pc.delete(sa);
				tx.commit();
				tx.close();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case DELETE_CLASS:// OK
				clazz = (Class<?>) request.getArgument(0);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				pc.delete(clazz);
				tx.commit();
				tx.close();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case QUERY_STRING: // OK
				string = (String) request.getArgument(0);
				pc = database.getContainer();
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				Query k = pc.createQuery(string);
				tx.commit();
				tx.close();
				response.set(k);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case QUERY_OBJECT: // OK
				Object s = request.getArgument(0);
				pc = database.getContainer();
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				TypedQuery<?> u = pc.createQuery(s);
				tx.commit();
				tx.close();
				response.set(u);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case QUERY_CLASS: // OK
				clazz = (Class<?>) request.getArgument(0);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				TypedQuery<?> q = pc.createQuery(clazz);
				tx.commit();
				tx.close();
				response.set(q);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case QUERY_PREDICATE: // OK
				clazz = (Class<?>) request.getArgument(0);
				p = (Predicate<?>) request.getArgument(1);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				q = pc.createQuery(p);
				tx.commit();
				tx.close();
				response.set(q);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case PROCEDURE:
				Serializable a1 = request.getArgument(0);
				Serializable a2 = request.getArgument(1);
				String functor = (String) a1;
				String[] args = (String[]) a2;
				response.set(database.createProcedureQuery(functor, args));
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CONSTAINS_STRING: // OK
				string = (String) request.getArgument(0);
				pc = database.getContainer();
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				exist = pc.contains(string);
				tx.commit();
				tx.close();
				response.set(exist);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CONSTAINS_OBJECT: // OK
				Object h = request.getArgument(0);
				pc = database.getContainer();
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				exist = pc.contains(h);
				tx.commit();
				tx.close();
				response.set(exist);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CONSTAINS_CLASS: // OK
				clazz = (Class<?>) request.getArgument(0);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				exist = pc.contains(clazz);
				tx.commit();
				tx.close();
				response.set(exist);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CONSTAINS_PREDICATE: // OK
				clazz = (Class<?>) request.getArgument(0);
				p = (Predicate<?>) request.getArgument(1);
				pc = database.containerOf(clazz);
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				exist = pc.contains(p);
				tx.commit();
				tx.close();
				response.set(exist);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CONSTAINS_INDICATOR: // OK
				string = (String) request.getArgument(0);
				int arity = (Integer) request.getArgument(1);
				pc = database.getContainer();
				tx = pc.getTransaction();
				if (!tx.isActive()) {
					tx.begin();
				}
				exist = pc.contains(string, arity);
				tx.commit();
				tx.close();
				response.set(exist);
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case INCLUDE:
				String path = (String) request.getArgument(0);
				StringBuilder pl = (StringBuilder) request.getArgument(1);
				// create file at some location in server
				String location = "upl" + path;
				File toBeInclude = new File(location);
				PrintWriter w = new PrintWriter(toBeInclude);
				w.write("" + pl + "");
				w.close();
				database.getContainer().include(location);
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case BACKUP: // OK
				dir = (String) request.getArgument(0);
				zip = (String) request.getArgument(1);
				String dbname = request.getDatabaseName();
				String a = database.getStorageLocation();
				String b = a.substring(0, a.indexOf(dbname));
				backuper.createBackup(b, dir, zip);
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case RESTORE: // OK
				dir = (String) request.getArgument(0);
				zip = (String) request.getArgument(1);
				restorer.restoreBackup(dir, zip);
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case COMMIT: // OK don't do any thing
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case ROLLBACK: // ???
				// TODO i need save every db change
				// and over roll-back request
				// load every db change and restore
				// before state. I need a change log.
				database.rollback();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			case CLOSE: // ??? No effect occurs
				database.close();
				response.setVoid();
				os.writeObject(response);
				LoggerUtils.debug(getClass(), response);
				break;
			default:

				break;
			}

			// close in/out streams
			is.close();
			os.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		}

	}

}
