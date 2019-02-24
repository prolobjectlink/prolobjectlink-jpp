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
package org.prolobjectlink.db.engine;

import java.io.File;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.RelationalDatabase;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.StorageGraph;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;

/**
 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
 *             RemoteRelationalHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
/** @author Jose Zalacain @since 1.0 */
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
		this.transaction = new PersistentContainerTransaction(this);
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
