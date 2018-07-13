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
package org.logicware.pdb.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.RuntimeError;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.StorageGraph;
import org.logicware.pdb.StorageManager;
import org.logicware.pdb.StorageMode;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.graph.RelationalGraph;
import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

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
		this.transaction = new DefaultTransaction(this);
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
		this.transaction = new DefaultTransaction(this);
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
			throw new RuntimeError(

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
