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

import static io.github.prolobjectlink.db.OperationType.ACTIVE;
import static io.github.prolobjectlink.db.OperationType.BACKUP;
import static io.github.prolobjectlink.db.OperationType.BEGIN;
import static io.github.prolobjectlink.db.OperationType.CLEAR;
import static io.github.prolobjectlink.db.OperationType.CLOSE;
import static io.github.prolobjectlink.db.OperationType.COMMIT;
import static io.github.prolobjectlink.db.OperationType.CONNECT;
import static io.github.prolobjectlink.db.OperationType.CONSTAINS_CLASS;
import static io.github.prolobjectlink.db.OperationType.CONSTAINS_INDICATOR;
import static io.github.prolobjectlink.db.OperationType.CONSTAINS_OBJECT;
import static io.github.prolobjectlink.db.OperationType.CONSTAINS_PREDICATE;
import static io.github.prolobjectlink.db.OperationType.CONSTAINS_STRING;
import static io.github.prolobjectlink.db.OperationType.CREATE;
import static io.github.prolobjectlink.db.OperationType.DEFRAG;
import static io.github.prolobjectlink.db.OperationType.DELETE_ARRAY;
import static io.github.prolobjectlink.db.OperationType.DELETE_CLASS;
import static io.github.prolobjectlink.db.OperationType.DROP;
import static io.github.prolobjectlink.db.OperationType.EXIST;
import static io.github.prolobjectlink.db.OperationType.INCLUDE;
import static io.github.prolobjectlink.db.OperationType.INSERT;
import static io.github.prolobjectlink.db.OperationType.PROCEDURE;
import static io.github.prolobjectlink.db.OperationType.QUERY_CLASS;
import static io.github.prolobjectlink.db.OperationType.QUERY_OBJECT;
import static io.github.prolobjectlink.db.OperationType.QUERY_PREDICATE;
import static io.github.prolobjectlink.db.OperationType.QUERY_STRING;
import static io.github.prolobjectlink.db.OperationType.RESTORE;
import static io.github.prolobjectlink.db.OperationType.ROLLBACK;
import static io.github.prolobjectlink.db.OperationType.UPDATE;

import java.io.Serializable;
import java.net.URL;

import io.github.prolobjectlink.db.DatabaseMode;
import io.github.prolobjectlink.db.DatabaseRequest;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.PersistentContainer;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.ProcedureQuery;
import io.github.prolobjectlink.db.Query;
import io.github.prolobjectlink.db.RemoteDatabase;
import io.github.prolobjectlink.db.Schema;
import io.github.prolobjectlink.db.Transaction;
import io.github.prolobjectlink.db.TypedQuery;
import io.github.prolobjectlink.db.engine.AbstractDatabaseEngine;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.tx.PersistentContainerTransaction;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;

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
