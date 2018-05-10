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
package org.logicware.pdb.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DataTransferObject;
import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseService;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.RemoteDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.dto.ClearDatabaseDto;
import org.logicware.pdb.dto.ContainsObjectDto;
import org.logicware.pdb.dto.CreateDatabaseDto;
import org.logicware.pdb.dto.DefragDatabaseDto;
import org.logicware.pdb.dto.DeleteObjectDto;
import org.logicware.pdb.dto.DropDatabaseDto;
import org.logicware.pdb.dto.InsertObjectDto;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;
import org.logicware.pdb.prolog.PredicateIndicator;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

public abstract class AbstractRemoteDatabase extends AbstractDatabaseService implements RemoteDatabase {

	private Socket socket;
	private final int port;
	private final String server;
	private DataTransferObject<?, ?> dto;

	public AbstractRemoteDatabase(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, URL url, String name, Schema schema, DatabaseUser owner, String server,
			int port) {
		super(provider, properties, converter, containerFactory, url, name, schema, owner);
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

		try {

			ObjectInputStream clientInputStream = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream clientOutputStream = new ObjectOutputStream(socket.getOutputStream());

			System.out.println("Sended: " + dto.getQuery());

			// client send data object
			clientOutputStream.writeObject(dto);

			// client received data object
			dto = unwrap(clientInputStream.readObject(), DataTransferObject.class);

			// handle result
			System.out.println("Received: " + dto.getResult());

			clientOutputStream.close();
			clientInputStream.close();

		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		}

	}

	public final void rollback() {
		if (dto != null) {
			dto.close();
		}
		dto = null;
	}

	public final void close() {
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// do nothing
				// close silently
			} finally {
				socket = null;
			}
		}
	}

	public String getStorageLocation() {
		return "" + getURL() + "";
	}

	public DatabaseEngine create() {
		dto = new CreateDatabaseDto();
		return this;
	}

	public DatabaseService drop() {
		dto = new DropDatabaseDto();
		return this;
	}

	public void open() {
		begin();
	}

	public <O> void insert(O... facts) {
		dto = new InsertObjectDto<O[]>(facts);
	}

	public <O> void update(O match, O update) {
		// TODO Auto-generated method stub

	}

	public void delete(Class<?> clazz) {
		dto = new DeleteObjectDto<Class<?>>(clazz);
	}

	public <O> void delete(O... facts) {
		dto = new DeleteObjectDto<O[]>(facts);
	}

	public Query createQuery(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public <O> TypedQuery<O> createQuery(O o) {
		// TODO Auto-generated method stub
		return null;
	}

	public <O> TypedQuery<O> createQuery(Class<O> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		// TODO Auto-generated method stub
		return null;
	}

	public PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public String locationOf(Class<?> clazz) {
		return "" + getURL() + "";
	}

	public void include(String path) {
		// TODO Auto-generated method stub

	}

	public boolean isOpen() {
		return socket != null && socket.isConnected();
	}

	public void flush() {
		commit();
	}

	public void clear() {
		dto = new ClearDatabaseDto();
	}

	public void backup(String directory, String zipFileName) {
		// TODO Auto-generated method stub

	}

	public void restore(String directory, String zipFileName) {
		// TODO Auto-generated method stub

	}

	public boolean contains(String string) {
		getTransaction().begin();
		dto = new ContainsObjectDto<String>(string);
		getTransaction().commit();
		getTransaction().close();
		return (Boolean) dto.getResult();
	}

	public <O> boolean contains(O o) {
		getTransaction().begin();
		// TODO INSTRUMENT THIS OBJECT
//		dto = new ContainsObjectDto<O>(o);
		getTransaction().commit();
		getTransaction().close();
		return (Boolean) dto.getResult();
	}

	public <O> boolean contains(Class<O> clazz) {
		getTransaction().begin();
		dto = new ContainsObjectDto<Class<?>>(clazz);
		getTransaction().commit();
		getTransaction().close();
		return (Boolean) dto.getResult();
	}

	public <O> boolean contains(Predicate<O> predicate) {
		getTransaction().begin();
		dto = new ContainsObjectDto<Predicate<O>>(predicate);
		getTransaction().commit();
		getTransaction().close();
		return (Boolean) dto.getResult();
	}

	public boolean contains(String functor, int arity) {
		PredicateIndicator pi = new PredicateIndicator(functor, arity);
		getTransaction().begin();
		dto = new ContainsObjectDto<PredicateIndicator>(pi);
		getTransaction().commit();
		getTransaction().close();
		return (Boolean) dto.getResult();
	}

	public void defragment() {
		dto = new DefragDatabaseDto();
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.REMOTE;
	}

	public DatabaseType getType() {
		// TODO Auto-generated method stub
		return null;
	}

//	public URL getURL() {
//		try {
//			System.setProperty("java.protocol.handler.pkgs", URLContentType.class.getPackage().getName());
//			return new URL("rempdb://" + socket.getInetAddress() + ":" + socket.getPort() + "/" + getName());
//		} catch (MalformedURLException e) {
//			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
//		}
//		return null;
//	}

}
