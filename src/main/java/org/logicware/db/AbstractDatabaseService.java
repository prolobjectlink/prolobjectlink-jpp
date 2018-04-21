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

import org.logicware.ConstraintQuery;
import org.logicware.ContainerFactory;
import org.logicware.DatabaseEngine;
import org.logicware.DatabaseSchema;
import org.logicware.DatabaseUser;
import org.logicware.EmbeddedDatabase;
import org.logicware.ObjectConverter;
import org.logicware.PersistentContainer;
import org.logicware.Predicate;
import org.logicware.ProcedureQuery;
import org.logicware.Settings;
import org.logicware.Query;
import org.logicware.TypedQuery;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public abstract class AbstractDatabaseService extends AbstractDatabaseEngine implements EmbeddedDatabase {

	private final DatabaseEngine engine;

	public AbstractDatabaseService(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, ContainerFactory containerFactory, String location, String name,
			DatabaseSchema schema, DatabaseUser owner, DatabaseEngine engine) {
		super(provider, properties, converter, containerFactory, location, name, schema, owner);
		this.engine = engine;
	}

	public String getDatabaseLocation() {
		return engine.getDatabaseLocation();
	}

	public String getStorageLocation() {
		return engine.getStorageLocation();
	}

	public String getBaseLocation() {
		return engine.getBaseLocation();
	}

	public void create() {
		engine.create();
	}

	public void drop() {
		engine.drop();
	}

	public void open() {
		engine.open();
	}

	public <O> void insert(O... facts) {
		engine.insert(facts);
	}

	public <O> void update(O match, O update) {
		engine.update(match, update);
	}

	public void delete(Class<?> clazz) {
		engine.delete(clazz);
	}

	public <O> void delete(O... facts) {
		engine.delete(facts);
	}

	public Query createQuery(String string) {
		return engine.createQuery(string);
	}

	public <O> TypedQuery<O> createQuery(O o) {
		return engine.createQuery(o);
	}

	public <O> TypedQuery<O> createQuery(Class<O> clazz) {
		return engine.createQuery(clazz);
	}

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		return engine.createQuery(predicate);
	}

	public <O> ConstraintQuery<O> createConstraintQuery(Class<O> clazz) {
		return engine.createConstraintQuery(clazz);
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return engine.createProcedureQuery(functor, args);
	}

	public PersistentContainer containerOf(Class<?> clazz) {
		return engine.containerOf(clazz);
	}

	public String locationOf(Class<?> clazz) {
		return engine.locationOf(clazz);
	}

	public void include(String path) {
		engine.include(path);
	}

	public boolean isOpen() {
		return engine.isOpen();
	}

	public void flush() {
		engine.flush();
	}

	public void clear() {
		engine.clear();
	}

	public void close() {
		engine.close();
	}

	public boolean contains(String string) {
		return engine.contains(string);
	}

	public <O> boolean contains(O o) {
		return engine.contains(o);
	}

	public <O> boolean contains(Class<O> clazz) {
		return engine.contains(clazz);
	}

	public <O> boolean contains(Predicate<O> predicate) {
		return engine.contains(predicate);
	}

	public boolean contains(String functor, int arity) {
		return engine.contains(functor, arity);
	}

	public void begin() {
		engine.begin();
	}

	public void commit() {
		engine.commit();
	}

	public void rollback() {
		engine.rollback();
	}

	public void defragment() {
		engine.defragment();
	}

}
