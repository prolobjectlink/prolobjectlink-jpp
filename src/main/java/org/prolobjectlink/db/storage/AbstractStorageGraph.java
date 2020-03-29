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
package org.prolobjectlink.db.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.RelationalGraph;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.StorageGraph;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;

import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractStorageGraph extends RelationalGraph<Object, Object>
		implements PersistentContainer, StorageGraph {

	private final Schema schema;
	private final String location;
	private final PrologEngine engine;
	private final Settings properties;
	private final StorageManager storage;
	private final PrologProvider provider;
	private final Transaction transaction;
	private final ContainerFactory containerFactory;
	private final ObjectConverter<PrologTerm> converter;

	public AbstractStorageGraph(String location, Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter, StorageMode storageMode) {
		this.storage = containerFactory.createStorageManager(location, storageMode);
		this.transaction = new PersistentContainerTransaction(this);
		this.containerFactory = containerFactory;
		this.engine = provider.newEngine();
		this.properties = properties;
		this.converter = converter;
		this.location = location;
		this.provider = provider;
		this.schema = schema;
	}

	public AbstractStorageGraph(String location, Schema schema, Settings properties, StorageMode storageMode,
			RelationalGraph<Object, Object> graph) {
		super(graph);
		this.containerFactory = properties.getContainerFactory();
		this.storage = containerFactory.createStorageManager(location, storageMode);
		this.converter = properties.getObjectConverter();
		this.transaction = new PersistentContainerTransaction(this);
		this.provider = properties.getProvider();
		this.engine = provider.newEngine();
		this.properties = properties;
		this.location = location;
		this.schema = schema;
	}

	public final <K> K unwrap(Class<K> cls) {
		return unwrap(this, cls);
	}

	public final <K> K unwrap(Object o, Class<K> cls) {
		try {
			if (cls.isAssignableFrom(o.getClass())) {
				return cls.cast(o);
			}
		} catch (Exception e) {
			throw new RuntimeException(

					"Impossible unwrap " + getClass()

							+ " to " + cls.getName()

					, e);
		}
		return null;
	}

	public final boolean isWrappedFor(Class<?> cls) {
		return cls.isInstance(this);
	}

	public final boolean isWrappedFor(Object o, Class<?> cls) {
		return cls.isInstance(o);
	}

	public final List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	public final Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return new Object[0];
	}

	public final boolean contains(String string) {
		// TODO Auto-generated method stub
		return false;
	}

	public final <O> boolean contains(O o) {
		// TODO Auto-generated method stub
		return false;
	}

	public final <O> boolean contains(Class<O> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return false;
	}

	public final boolean contains(String functor, int arity) {
		// TODO Auto-generated method stub
		return false;
	}

	public final Collection<Class<?>> classes() {
		return storage.classes();
	}

	public final ObjectConverter<PrologTerm> getConverter() {
		return converter;
	}

	public final Settings getProperties() {
		return properties;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	public final PrologEngine getEngine() {
		return engine;
	}

	public final void open() {
		storage.begin();
	}

	public final <O> void insert(O... facts) {
		// TODO Auto-generated method stub

	}

	public final <O> void update(O match, O update) {
		// TODO Auto-generated method stub

	}

	public final void delete(Class<?> clazz) {
		// TODO Auto-generated method stub

	}

	public final <O> void delete(O... facts) {
		// TODO Auto-generated method stub

	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final Query createQuery(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		// TODO Auto-generated method stub
		return null;
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return storage.containerOf(clazz);
	}

	public final String locationOf(Class<?> clazz) {
		return storage.locationOf(clazz);
	}

	public final void backup(String directory, String zipFileName) {
		storage.backup(directory, zipFileName);
	}

	public final void restore(String directory, String zipFileName) {
		storage.restore(directory, zipFileName);
	}

	public final void include(String path) {
		storage.include(path);
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final String getLocation() {
		return location;
	}

	public final void defragment() {
		storage.defragment();
	}

	public final boolean isOpen() {
		return storage.isOpen();
	}

	public final void flush() {
		storage.commit();
	}

	public final void close() {
		storage.close();
	}

	public final void begin() {
		open();
	}

	public final void commit() {
		flush();
	}

	public final void rollback() {
		open();
	}

	public boolean isActive() {
		return getTransaction().isActive();
	}

	@Override
	public String toString() {
		return "AbstractStorageGraph [location=" + location + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractStorageGraph other = (AbstractStorageGraph) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location)) {
			return false;
		}
		return true;
	}

}
