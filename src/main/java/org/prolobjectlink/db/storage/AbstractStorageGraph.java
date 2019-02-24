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
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

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
