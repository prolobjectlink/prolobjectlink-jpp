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

import static org.logicware.pdb.OperationType.BEGIN;
import static org.logicware.pdb.OperationType.CLEAR;
import static org.logicware.pdb.OperationType.CLOSE;
import static org.logicware.pdb.OperationType.COMMIT;
import static org.logicware.pdb.OperationType.CONNECT;
import static org.logicware.pdb.OperationType.CONSTAINS;
import static org.logicware.pdb.OperationType.CREATE;
import static org.logicware.pdb.OperationType.DELETE;
import static org.logicware.pdb.OperationType.DROP;
import static org.logicware.pdb.OperationType.INCLUDE;
import static org.logicware.pdb.OperationType.INSERT;
import static org.logicware.pdb.OperationType.PROCEDURE;
import static org.logicware.pdb.OperationType.QUERY;
import static org.logicware.pdb.OperationType.ROLLBACK;
import static org.logicware.pdb.OperationType.UPDATE;

import java.net.URL;

import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseRequest;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.RemoteDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.common.AbstractDatabaseService;
import org.logicware.pdb.prolog.PrologClause;
import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologProvider;

public abstract class RemoteDatabaseClient extends AbstractDatabaseService implements RemoteDatabase {

	private boolean connected = false;
	private final DatabaseRequest activeRequest;

	RemoteDatabaseClient(Settings settings, URL url, String name, Schema schema, DatabaseUser owner) {
		super(settings, url, name, schema, owner);
		this.activeRequest = new DatabaseRequest(url.getHost(), url.getPort(), name, getType());
	}

	public final PersistentContainer getContainer() {
		return this;
	}

	public final String getStorageLocation() {
		return "" + getURL() + "";
	}

	public final boolean isConnected() {
		return connected;
	}

	public final void backup(String directory, String zipFileName) {
		// TODO Auto-generated method stub

	}

	public final void restore(String directory, String zipFileName) {
		// TODO Auto-generated method stub

	}

	public final RemoteDatabase connect(String name) {
		activeRequest.clearArguments();
		activeRequest.setType(CONNECT);
		connected = activeRequest.send();
		return this;
	}

	public final RemoteDatabase create() {
		activeRequest.clearArguments();
		activeRequest.setType(CREATE);
		activeRequest.send();
		return this;
	}

	public final RemoteDatabase drop() {
		activeRequest.clearArguments();
		activeRequest.setType(DROP);
		activeRequest.send();
		return this;
	}

	public final void open() {
		begin();
	}

	public final <O> void insert(O... os) {
		activeRequest.clearArguments();
		activeRequest.setType(INSERT);
		activeRequest.addArgument(os);
		activeRequest.send();
	}

	public final <O> void update(O match, O update) {
		Object[] a = { match, update };
		activeRequest.clearArguments();
		activeRequest.setType(UPDATE);
		activeRequest.addArgument(a);
		activeRequest.send();
	}

	public final void delete(Class<?> c) {
		activeRequest.clearArguments();
		activeRequest.setType(DELETE);
		activeRequest.addArgument(c);
		activeRequest.send();
	}

	public final <O> void delete(O... os) {
		activeRequest.clearArguments();
		activeRequest.setType(DELETE);
		activeRequest.addArgument(os);
		activeRequest.send();
	}

	public final Query createQuery(String s) {
		activeRequest.clearArguments();
		activeRequest.setType(QUERY);
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		// TODO INSTRUMENT THIS OBJECT
		activeRequest.clearArguments();
		activeRequest.setType(QUERY);
//		dto.addArgument(o);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		activeRequest.clearArguments();
		activeRequest.setType(QUERY);
		activeRequest.addArgument(clazz);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> p) {
		activeRequest.clearArguments();
		activeRequest.setType(QUERY);
		activeRequest.addArgument(p);
		return activeRequest.send();
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		activeRequest.clearArguments();
		activeRequest.setType(PROCEDURE);
		activeRequest.addArgument(functor);
		activeRequest.addArgument(args);
		return activeRequest.send();
	}

	public final boolean contains(String s) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS);
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> boolean contains(O o) {
		// TODO INSTRUMENT THIS OBJECT
		activeRequest.clearArguments();
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS);
		// activeRequest.addArgument(o);
		return activeRequest.send();
	}

	public final <O> boolean contains(Class<O> c) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS);
		activeRequest.addArgument(c);
		return activeRequest.send();
	}

	public final <O> boolean contains(Predicate<O> p) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS);
		activeRequest.addArgument(p);
		return activeRequest.send();
	}

	public final boolean contains(String functor, int arity) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS);
		activeRequest.addArgument(functor);
		activeRequest.addArgument(arity);
		return activeRequest.send();
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public final String locationOf(Class<?> clazz) {
		return "" + getURL() + "";
	}

	public final void defragment() {
		activeRequest.clearArguments();
		activeRequest.setType(QUERY);
		activeRequest.send();
	}

	public final boolean isOpen() {
		return connected;
	}

	public final void flush() {
		commit();
	}

	public final void clear() {
		activeRequest.clearArguments();
		activeRequest.setType(CLEAR);
		activeRequest.send();
	}

	public final void include(String path) {
		PrologProvider p = getProvider();
		PrologEngine engine = p.newEngine();
		StringBuilder b = new StringBuilder();
		activeRequest.clearArguments();
		activeRequest.setType(INCLUDE);
		for (PrologClause c : engine) {
			b.append(c);
		}
		activeRequest.addArgument(path);
		activeRequest.addArgument(b);
		activeRequest.send();
		engine.dispose();
	}

	public final void begin() {
		activeRequest.clearArguments();
		activeRequest.setType(BEGIN);
		activeRequest.send();
	}

	public final void commit() {
		activeRequest.clearArguments();
		activeRequest.setType(COMMIT);
		activeRequest.send();
	}

	public final void rollback() {
		activeRequest.clearArguments();
		activeRequest.setType(ROLLBACK);
		activeRequest.send();
	}

	public void close() {
		activeRequest.clearArguments();
		activeRequest.setType(CLOSE);
		activeRequest.send();
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.REMOTE;
	}

}
