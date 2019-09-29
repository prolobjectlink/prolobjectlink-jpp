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
	protected static final String LOCATION = "db/pdb/relational";

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
