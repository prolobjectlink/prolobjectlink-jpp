/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.persistent;

import static org.prolobjectlink.db.OperationType.ACTIVE;
import static org.prolobjectlink.db.OperationType.BACKUP;
import static org.prolobjectlink.db.OperationType.BEGIN;
import static org.prolobjectlink.db.OperationType.CLEAR;
import static org.prolobjectlink.db.OperationType.CLOSE;
import static org.prolobjectlink.db.OperationType.COMMIT;
import static org.prolobjectlink.db.OperationType.CONNECT;
import static org.prolobjectlink.db.OperationType.CONSTAINS_CLASS;
import static org.prolobjectlink.db.OperationType.CONSTAINS_INDICATOR;
import static org.prolobjectlink.db.OperationType.CONSTAINS_OBJECT;
import static org.prolobjectlink.db.OperationType.CONSTAINS_PREDICATE;
import static org.prolobjectlink.db.OperationType.CONSTAINS_STRING;
import static org.prolobjectlink.db.OperationType.CREATE;
import static org.prolobjectlink.db.OperationType.DEFRAG;
import static org.prolobjectlink.db.OperationType.DELETE_ARRAY;
import static org.prolobjectlink.db.OperationType.DELETE_CLASS;
import static org.prolobjectlink.db.OperationType.DROP;
import static org.prolobjectlink.db.OperationType.EXIST;
import static org.prolobjectlink.db.OperationType.INCLUDE;
import static org.prolobjectlink.db.OperationType.INSERT;
import static org.prolobjectlink.db.OperationType.PROCEDURE;
import static org.prolobjectlink.db.OperationType.QUERY_CLASS;
import static org.prolobjectlink.db.OperationType.QUERY_OBJECT;
import static org.prolobjectlink.db.OperationType.QUERY_PREDICATE;
import static org.prolobjectlink.db.OperationType.QUERY_STRING;
import static org.prolobjectlink.db.OperationType.RESTORE;
import static org.prolobjectlink.db.OperationType.ROLLBACK;
import static org.prolobjectlink.db.OperationType.UPDATE;

import java.io.Serializable;
import java.net.URL;

import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseRequest;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.RemoteDatabase;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.engine.AbstractDatabaseEngine;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;

/** @author Jose Zalacain @since 1.0 */
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
