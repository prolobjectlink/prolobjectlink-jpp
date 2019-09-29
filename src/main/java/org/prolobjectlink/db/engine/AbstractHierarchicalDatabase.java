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
import org.prolobjectlink.db.HierarchicalDatabase;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;

/**
 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
 *             RemoteHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractHierarchicalDatabase extends AbstractDatabaseEngine implements HierarchicalDatabase {

	private final StorageManager storage;
	private final Transaction transaction;
	protected static final String LOCATION = "db/pdb/hierarchical";

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public AbstractHierarchicalDatabase(String name, Schema schema, DatabaseUser user, StorageManager storage) {
		super(storage.getProperties(), LOCATION + SEPARATOR + name + SEPARATOR + "database", name, schema, user);
		this.transaction = new PersistentContainerTransaction(this);
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

	public final String getStorageLocation() {
		return storage.getLocation();
	}

	public final void include(String path) {
		storage.include(path);
	}

	public final Transaction getTransaction() {
		return transaction;
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

	public boolean isActive() {
		return getTransaction().isActive();
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

	public final boolean exist() {
		return getSchema().countUsers() > 0;
	}

	public final DatabaseMode getMode() {
		return DatabaseMode.EMBEDDED;
	}

	public final DatabaseType getType() {
		return DatabaseType.HIERARCHICAL;
	}

}
