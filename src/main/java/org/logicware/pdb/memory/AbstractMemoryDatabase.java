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
package org.logicware.pdb.memory;

import static org.logicware.pdb.jpa.spi.JPAPersistenceXmlParser.XML;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.SynchronizationType;
import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.MemoryDatabase;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.VolatileContainer;
import org.logicware.pdb.databse.AbstractDatabaseEngine;
import org.logicware.pdb.jpa.JPAEntityManager;
import org.logicware.pdb.jpa.JPAEntityManagerFactory;
import org.logicware.pdb.jpa.JPAResultSetMapping;
import org.logicware.pdb.prolog.PrologContainerQuery;
import org.logicware.pdb.prolog.PrologTypedQuery;

public abstract class AbstractMemoryDatabase extends AbstractDatabaseEngine implements MemoryDatabase {

	private final URL url;
	private boolean closed;
	private final VolatileContainer storage;
	private final Map<String, PersistenceUnitInfo> units = new HashMap<String, PersistenceUnitInfo>();

	protected static final String URL_PREFIX = "jdbc:prolobjectlink:";
	protected static final URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	AbstractMemoryDatabase(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer container) {
		super(settings, url.getPath(), name, schema, owner);
		this.storage = container;
		this.closed = false;
		this.url = url;
	}

	public final URL getURL() {
		return url;
	}

	public final String getDatabaseLocation() {
		return getLocation() + "/database";
	}

	public final EntityManager getEntityManager() {

		// TODO FILL ALL MAPS

		// properties map
		Map map = getProperties().asMap();

		// user defined names for entities
		Map<String, Class<?>> entityMap = new HashMap<String, Class<?>>();

		//
		JPAEntityManagerFactory factory = new JPAEntityManagerFactory(this);

		//
		Map<String, EntityGraph<?>> namedEntityGraphs = new HashMap<String, EntityGraph<?>>();

		// result set mappings for native queries result
		Map<String, JPAResultSetMapping> resultSetMappings = new HashMap<String, JPAResultSetMapping>();

		// user defined named queries container
		Map<String, javax.persistence.Query> namedQueries = new HashMap<String, javax.persistence.Query>();

		return new JPAEntityManager(this, factory, SynchronizationType.SYNCHRONIZED, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

	public final String getRootLocation() {
		return url.getPath();
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
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
		// TODO Auto-generated method stub
		return null;
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

	public final void defragment() {
		// do nothing
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.MEMORY;
	}

	public final MemoryDatabase create() {
		return this;
	}

	public MemoryDatabase drop() {
		return this;
	}

}
