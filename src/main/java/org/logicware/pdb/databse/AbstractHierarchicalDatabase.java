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
package org.logicware.pdb.databse;

import java.io.File;
import java.util.List;

import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.HierarchicalDatabase;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.Schema;
import org.logicware.pdb.StorageManager;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;

public abstract class AbstractHierarchicalDatabase extends AbstractDatabaseEngine implements HierarchicalDatabase {

	private final StorageManager storage;
	private final Transaction transaction;
	protected static final String LOCATION = "dat" + SEPARATOR + "hierarchical";

	public AbstractHierarchicalDatabase(String name, Schema schema, DatabaseUser user, StorageManager storage) {
		super(storage.getProperties(), LOCATION + SEPARATOR + name + SEPARATOR + "database", name, schema, user);
		this.transaction = new DefaultTransaction(this);
		this.storage = storage;
	}

	public final void open() {
		storage.open();
	}

	public final <O> void insert(O... facts) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		storage.insert(facts);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final <O> void update(O match, O update) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		storage.update(match, update);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final void delete(Class<?> clazz) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		storage.delete(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final <O> void delete(O... facts) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		storage.delete(facts);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final boolean contains(String string) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		boolean b = storage.contains(string);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(O object) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		boolean b = storage.contains(object);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(Class<O> clazz) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		boolean b = storage.contains(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		boolean b = storage.contains(predicate);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final boolean contains(String functor, int arity) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		boolean b = storage.contains(functor, arity);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final Query createQuery(String string) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		Query q = storage.createQuery(string);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(o);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(predicate);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		checkActiveTransaction(transaction);
		storage.getTransaction().begin();
		ProcedureQuery pq = storage.createProcedureQuery(functor, args);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return pq;
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return storage.containerOf(clazz);
	}

	public final PersistentContainer getContainer() {
		return storage;
	}

	public final String locationOf(Class<?> clazz) {
		return storage.locationOf(clazz);
	}

	public final String getDatabaseLocation() {
		return getLocation() + File.separator + "database";
	}

	public final String getStorageLocation() {
		return storage.getLocation();
	}

	public final String getRootLocation() {
		return LOCATION;
	}

	public final void include(String path) {
		storage.include(path);
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
	}

	public final boolean isOpen() {
		return storage.isOpen();
	}

	public final void flush() {
		storage.flush();
	}

	public final void clear() {
		storage.getTransaction().begin();
		storage.clear();
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final void close() {
		if (transaction.isActive()) {
			transaction.close();
		}
		storage.close();
	}

	public final void begin() {
		getTransaction().begin();
	}

	public final void commit() {
		getTransaction().commit();
	}

	public final void rollback() {
		getTransaction().rollback();
	}

	public final void defragment() {
		storage.defragment();
	}

	public final DatabaseEngine create() {
		new File(getRootLocation() + "/functions.pl");
		new File(getRootLocation() + "/triggers.pl");
		new File(getRootLocation() + "/views.pl");
		getSchema().flush();
		return this;
	}

	public final DatabaseEngine drop() {
		storage.clear();
		storage.commit();
		getSchema().clear();
		getSchema().flush();
		return this;
	}

}
