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
package org.logicware.db;

import java.io.File;
import java.util.List;

import org.logicware.ContainerFactory;
import org.logicware.DatabaseUser;
import org.logicware.HierarchicalDatabase;
import org.logicware.PersistentContainer;
import org.logicware.Predicate;
import org.logicware.ProcedureQuery;
import org.logicware.Query;
import org.logicware.Schema;
import org.logicware.StorageManager;
import org.logicware.TypedQuery;
import org.logicware.prolog.PrologProvider;

public abstract class AbstractHerarchicalDatabase extends AbstractDatabaseEngine implements HierarchicalDatabase {

	private final StorageManager storage;
	protected static final String LOCATION = "dat" + File.separator + "hierarchical";

	public AbstractHerarchicalDatabase(PrologProvider provider, ContainerFactory containerFactory, String name,
			Schema schema, DatabaseUser user, StorageManager storage) {
		super(provider, storage.getProperties(), storage.getConverter(), containerFactory,
				LOCATION + File.separator + name + File.separator + "database", name, schema, user);
		this.storage = storage;
	}

	public final void open() {
		storage.open();
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

	public final PersistentContainer containerOf(Class<?> clazz) {
		return storage.containerOf(clazz);
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

	public final String getBaseLocation() {
		return LOCATION;
	}

	public final void include(String path) {
		storage.include(path);
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
		storage.clear();
	}

	public final void close() {
		storage.close();
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

	public final void defragment() {
		storage.defragment();
	}

	public final void create() {
		getSchema().flush();
	}

	public final void drop() {
		// TODO Auto-generated method stub

	}

}