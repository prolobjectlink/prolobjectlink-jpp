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

import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.RelationalDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.StorageGraph;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;

/**
 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
 *             RemoteRelationalHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public abstract class AbstractRelationalDatabase extends AbstractDatabaseEngine implements RelationalDatabase {

	private final StorageGraph storage;
	private final Transaction transaction;
	protected static final String LOCATION = "dat" + File.separator + "relational";

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public AbstractRelationalDatabase(String name, Schema schema, DatabaseUser user, StorageGraph storage) {
		super(storage.getProperties(), LOCATION + File.separator + name + File.separator + "database", name, schema,
				user);
		this.transaction = new DefaultTransaction(this);
		this.storage = storage;
	}

	public final void open() {
		storage.begin();
	}

	public final <O> void insert(O... facts) {
		storage.insert(facts);
	}

	public final <O> void update(O match, O update) {
		storage.update(match, update);
	}

	public final void delete(Class<?> clazz) {
		storage.delete(clazz);
	}

	public final <O> void delete(O... facts) {
		storage.delete(facts);
	}

	public final boolean contains(String string) {
		return storage.contains(string);
	}

	public final <O> boolean contains(O o) {
		return storage.contains(o);
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
		return storage.createQuery(string);
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		return storage.createQuery(o);
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		return storage.createQuery(clazz);
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		return storage.createQuery(predicate);
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		return storage.createProcedureQuery(functor, args);
	}

	public final String getStorageLocation() {
		return storage.getLocation();
	}

	public final Transaction getTransaction() {
		return transaction;
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

	public final void defragment() {
		storage.defragment();
	}

	public final void include(String path) {
		storage.include(path);
	}

	public final void begin() {
		storage.begin();
	}

	public final void commit() {
		storage.commit();
	}

	public final void rollback() {
		storage.rollback();
	}

	public boolean isActive() {
		return storage.isActive();
	}

	public final boolean isOpen() {
		return storage.isOpen();
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

	public final boolean exist() {
		return getSchema().countUsers() > 0;
	}

	public final void flush() {
		getSchema().flush();
		storage.commit();
	}

	public final void close() {
		if (transaction.isActive()) {
			transaction.close();
		}
		getSchema().clear();
		storage.clear();
		storage.close();
	}

	public final void clear() {
		storage.clear();
		getSchema().clear();
		storage.clear();
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.EMBEDDED;
	}

	public final DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}
