/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.memory;

import java.net.URL;

import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.MemoryDatabase;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.VolatileContainer;
import org.prolobjectlink.db.engine.AbstractDatabaseEngine;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.prolog.PrologContainerQuery;
import org.prolobjectlink.db.prolog.PrologTypedQuery;
import org.prolobjectlink.db.tx.VolatileTransaction;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractMemoryDatabase extends AbstractDatabaseEngine implements MemoryDatabase {

	private boolean closed;
	private final VolatileContainer storage;
	private final Transaction transaction = new VolatileTransaction();

	AbstractMemoryDatabase(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer container) {
		super(settings, url, name, schema, owner);
		this.storage = container;
		this.closed = false;
	}

	public final void backup(String directory, String zipFileName) {
		// do nothing
	}

	public final void restore(String directory, String zipFileName) {
		// do nothing
	}

	public final String getStorageLocation() {
		return "";
	}

	public final void open() {
		closed = false;
	}

	public final <O> void insert(O... facts) {
		storage.add(facts);
	}

	public final <O> void update(O match, O update) {
		storage.modify(match, update);
	}

	public final void delete(Class<?> clazz) {
		storage.remove(clazz);
	}

	public final <O> void delete(O... facts) {
		storage.remove(facts);
	}

	public final boolean contains(String string) {
		return storage.contains(string);
	}

	public final <O> boolean contains(O object) {
		return storage.contains(object);
	}

	public final <O> boolean contains(Class<O> clazz) {
		return storage.contains(clazz);
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		return storage.contains(predicate);
	}

	public final boolean contains(String functor, int arity) {
		return storage.contains(functor, arity);
	}

	public final Query createQuery(String string) {
		return new PrologContainerQuery(storage.findAll(string));
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		return new PrologTypedQuery<O>(storage.findAll(o));
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		return new PrologTypedQuery<O>(storage.findAll(clazz));
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		return new PrologTypedQuery<O>(storage.findAll(predicate));
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		throw new UnsupportedOperationException("createProcedureQuery(String functor, String... args)");
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		throw new UnsupportedOperationException("containerOf(Class<?> clazz)");
	}

	public final PersistentContainer getContainer() {
		throw new UnsupportedOperationException("containerOf(Class<?> clazz)");
	}

	public final String locationOf(Class<?> clazz) {
		String location = "/" + clazz.getName().replace('.', '/');
		return location.substring(0, location.lastIndexOf('/'));
	}

	public final void include(String path) {
		getEngine().include(path);
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final boolean isOpen() {
		return !closed;
	}

	public final void flush() {
		// do nothing
	}

	public final void clear() {
		storage.clear();
	}

	public final void close() {
		closed = true;
	}

	public final void begin() {
		closed = false;
	}

	public final void commit() {
		getTransaction().commit();
	}

	public final void rollback() {
		getTransaction().rollback();
	}

	public boolean isActive() {
		return getTransaction().isActive();
	}

	public final void defragment() {
		// do nothing
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.MEMORY;
	}

	public final MemoryDatabase create() {
		return this;
	}

	public final MemoryDatabase drop() {
		return this;
	}

	public final boolean exist() {
		return getSchema().countUsers() > 0;
	}

}