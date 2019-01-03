/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.logicware.db.persistent;

import static org.logicware.db.OperationType.ACTIVE;
import static org.logicware.db.OperationType.BACKUP;
import static org.logicware.db.OperationType.BEGIN;
import static org.logicware.db.OperationType.CLEAR;
import static org.logicware.db.OperationType.CLOSE;
import static org.logicware.db.OperationType.COMMIT;
import static org.logicware.db.OperationType.CONNECT;
import static org.logicware.db.OperationType.CONSTAINS_CLASS;
import static org.logicware.db.OperationType.CONSTAINS_INDICATOR;
import static org.logicware.db.OperationType.CONSTAINS_OBJECT;
import static org.logicware.db.OperationType.CONSTAINS_PREDICATE;
import static org.logicware.db.OperationType.CONSTAINS_STRING;
import static org.logicware.db.OperationType.CREATE;
import static org.logicware.db.OperationType.DEFRAG;
import static org.logicware.db.OperationType.DELETE_ARRAY;
import static org.logicware.db.OperationType.DELETE_CLASS;
import static org.logicware.db.OperationType.DROP;
import static org.logicware.db.OperationType.EXIST;
import static org.logicware.db.OperationType.INCLUDE;
import static org.logicware.db.OperationType.INSERT;
import static org.logicware.db.OperationType.PROCEDURE;
import static org.logicware.db.OperationType.QUERY_CLASS;
import static org.logicware.db.OperationType.QUERY_OBJECT;
import static org.logicware.db.OperationType.QUERY_PREDICATE;
import static org.logicware.db.OperationType.QUERY_STRING;
import static org.logicware.db.OperationType.RESTORE;
import static org.logicware.db.OperationType.ROLLBACK;
import static org.logicware.db.OperationType.UPDATE;

import java.io.Serializable;
import java.net.URL;

import org.logicware.db.DatabaseMode;
import org.logicware.db.DatabaseRequest;
import org.logicware.db.DatabaseUser;
import org.logicware.db.PersistentContainer;
import org.logicware.db.Predicate;
import org.logicware.db.ProcedureQuery;
import org.logicware.db.Query;
import org.logicware.db.RemoteDatabase;
import org.logicware.db.Schema;
import org.logicware.db.Transaction;
import org.logicware.db.TypedQuery;
import org.logicware.db.engine.AbstractDatabaseEngine;
import org.logicware.db.etc.Settings;
import org.logicware.db.tx.PersistentContainerTransaction;
import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;

public abstract class RemoteDatabaseClient extends AbstractDatabaseEngine implements RemoteDatabase {

	private boolean connected = false;
	private final Transaction transaction;
	private final DatabaseRequest activeRequest;

	RemoteDatabaseClient(Settings settings, URL url, String name, Schema schema, DatabaseUser owner) {
		super(settings, url, name, schema, owner);
		this.activeRequest = new DatabaseRequest(url.getHost(), url.getPort(), name, getType());
		this.transaction = new PersistentContainerTransaction(this);
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

	public final void backup(String dir, String zip) {
		activeRequest.setType(BACKUP);
		activeRequest.clearArguments();
		activeRequest.addArgument(dir);
		activeRequest.addArgument(zip);
		activeRequest.send();
	}

	public final void restore(String dir, String zip) {
		activeRequest.setType(RESTORE);
		activeRequest.clearArguments();
		activeRequest.addArgument(dir);
		activeRequest.addArgument(zip);
		activeRequest.send();
	}

	public final RemoteDatabase connect(String name) {
		activeRequest.setType(CONNECT);
		activeRequest.clearArguments();
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

	public final boolean exist() {
		activeRequest.clearArguments();
		activeRequest.setType(EXIST);
		return activeRequest.send();
	}

	public final void open() {
		begin();
	}

	public final <O> void insert(O... os) {
		Class<?> c = os.getClass();
		Class<?> cl = c.getComponentType();
		activeRequest.clearArguments();
		activeRequest.setType(INSERT);
		activeRequest.addArgument(cl);
		activeRequest.addArgument(os);
		activeRequest.send();
	}

	public final <O> void update(O match, O update) {
		Serializable m = (Serializable) match;
		Serializable u = (Serializable) update;
		checkReplacementObject(match, update);
		Serializable[] a = { m, u };
		Class<?> mc = match.getClass();
		activeRequest.clearArguments();
		activeRequest.setType(UPDATE);
		activeRequest.addArgument(mc);
		activeRequest.addArgument(a);
		activeRequest.send();
	}

	public final void delete(Class<?> c) {
		activeRequest.setType(DELETE_CLASS);
		activeRequest.clearArguments();
		activeRequest.addArgument(c);
		activeRequest.send();
	}

	public final <O> void delete(O... os) {
		Class<?> c = os.getClass();
		Class<?> cl = c.getComponentType();
		activeRequest.setType(DELETE_ARRAY);
		activeRequest.clearArguments();
		activeRequest.addArgument(cl);
		activeRequest.addArgument(os);
		activeRequest.send();
	}

	public final Query createQuery(String s) {
		activeRequest.setType(QUERY_STRING);
		activeRequest.clearArguments();
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		Serializable s = (Serializable) o;
		activeRequest.setType(QUERY_OBJECT);
		activeRequest.clearArguments();
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(Class<O> c) {
		activeRequest.setType(QUERY_CLASS);
		activeRequest.clearArguments();
		activeRequest.addArgument(c);
		return activeRequest.send();
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> p) {
		activeRequest.setType(QUERY_PREDICATE);
		activeRequest.clearArguments();
		activeRequest.addArgument(classOf(p));
		activeRequest.addArgument(p);
		return activeRequest.send();
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		activeRequest.setType(PROCEDURE);
		activeRequest.clearArguments();
		activeRequest.addArgument(functor);
		activeRequest.addArgument(args);
		return activeRequest.send();
	}

	public final boolean contains(String s) {
		activeRequest.setType(CONSTAINS_STRING);
		activeRequest.clearArguments();
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> boolean contains(O o) {
		Serializable s = (Serializable) o;
		activeRequest.setType(CONSTAINS_OBJECT);
		activeRequest.clearArguments();
		activeRequest.addArgument(s);
		return activeRequest.send();
	}

	public final <O> boolean contains(Class<O> c) {
		activeRequest.setType(CONSTAINS_CLASS);
		activeRequest.clearArguments();
		activeRequest.addArgument(c);
		return activeRequest.send();
	}

	public final <O> boolean contains(Predicate<O> p) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS_PREDICATE);
		activeRequest.addArgument(classOf(p));
		activeRequest.addArgument(p);
		return activeRequest.send();
	}

	public final boolean contains(String functor, int arity) {
		activeRequest.clearArguments();
		activeRequest.setType(CONSTAINS_INDICATOR);
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
		activeRequest.setType(DEFRAG);
		activeRequest.send();
	}

	public final boolean isOpen() {
		return isConnected();
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
		for (PrologClause c : engine) {
			b.append(c);
		}
		activeRequest.clearArguments();
		activeRequest.setType(INCLUDE);
		activeRequest.addArgument(path);
		activeRequest.addArgument(b);
		activeRequest.send();
		engine.dispose();
	}

	public final Transaction getTransaction() {
		return transaction;
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

	public final boolean isActive() {
		activeRequest.clearArguments();
		activeRequest.setType(ACTIVE);
		return activeRequest.send();
	}

	public final void close() {
		activeRequest.clearArguments();
		activeRequest.setType(CLOSE);
		activeRequest.send();
		connected = false;
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.REMOTE;
	}

}
