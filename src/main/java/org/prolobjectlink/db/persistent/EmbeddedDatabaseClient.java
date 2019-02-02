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
package org.prolobjectlink.db.persistent;

import static org.prolobjectlink.db.XmlParser.XML;

import java.io.File;
import java.net.URL;

import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.EmbeddedDatabase;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.engine.AbstractDatabaseEngine;
import org.prolobjectlink.db.etc.Settings;

/** @author Jose Zalacain @since 1.0 */
public abstract class EmbeddedDatabaseClient extends AbstractDatabaseEngine
		implements PersistentContainer, EmbeddedDatabase {

	private final PersistentContainer storage;

	protected static final String URL_PREFIX = "jdbc:prolobjectlink:";
	protected static final URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	EmbeddedDatabaseClient(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			PersistentContainer storage) {
		super(settings, url, name, schema, owner);
		this.storage = storage;
	}

	public final PersistentContainer getContainer() {
		return storage;
	}

	public final String getStorageLocation() {
		return storage.getLocation();
	}

	public final void open() {
		storage.begin();
	}

	public final <O> void insert(O... facts) {
		storage.getTransaction().begin();
		storage.insert(facts);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final <O> void update(O match, O update) {
		storage.getTransaction().begin();
		storage.update(match, update);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final void delete(Class<?> clazz) {
		storage.getTransaction().begin();
		storage.delete(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final <O> void delete(O... facts) {
		storage.getTransaction().begin();
		storage.delete(facts);
		storage.getTransaction().commit();
		storage.getTransaction().close();
	}

	public final boolean contains(String string) {
		storage.getTransaction().begin();
		boolean b = storage.contains(string);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(O object) {
		storage.getTransaction().begin();
		boolean b = storage.contains(object);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(Class<O> clazz) {
		storage.getTransaction().begin();
		boolean b = storage.contains(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		storage.getTransaction().begin();
		boolean b = storage.contains(predicate);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final boolean contains(String functor, int arity) {
		storage.getTransaction().begin();
		boolean b = storage.contains(functor, arity);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return b;
	}

	public final Query createQuery(String string) {
		storage.getTransaction().begin();
		Query q = storage.createQuery(string);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(o);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(clazz);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		storage.getTransaction().begin();
		TypedQuery<O> q = storage.createQuery(predicate);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return q;
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		storage.getTransaction().begin();
		ProcedureQuery pq = storage.createProcedureQuery(functor, args);
		storage.getTransaction().commit();
		storage.getTransaction().close();
		return pq;
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return storage.containerOf(clazz);
	}

	public final String locationOf(Class<?> clazz) {
		return storage.locationOf(clazz);
	}

	public final void include(String path) {
		storage.include(path);
	}

	public final Transaction getTransaction() {
		return storage.getTransaction();
	}

	public final boolean isOpen() {
		return storage.isOpen();
	}

	public final void flush() {
		storage.commit();
	}

	public final void clear() {
		storage.getTransaction().begin();
		storage.clear();
		storage.getTransaction().commit();
		storage.getTransaction().close();
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

	public final boolean isActive() {
		return storage.isActive();
	}

	public final void defragment() {
		storage.defragment();
	}

	public final EmbeddedDatabase create() {
		new File(getRootLocation() + "/functions.pl");
		new File(getRootLocation() + "/triggers.pl");
		new File(getRootLocation() + "/views.pl");
		getSchema().flush();
		return this;
	}

	public final EmbeddedDatabase drop() {
		getSchema().clear();
		getSchema().flush();
		clear();
		flush();
		return this;
	}

	public final boolean exist() {
		return getSchema().countUsers() > 0;
	}

}
