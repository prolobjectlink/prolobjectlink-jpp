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
package org.logicware.pdb.common;

import java.util.List;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.NonSolutionError;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.RelationalCache;
import org.logicware.pdb.RuntimeError;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.graph.RelationalGraph;
import org.logicware.pdb.prolog.PrologEngine;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

public class AbstractRelationalCache extends RelationalGraph<Object, Object> implements RelationalCache {

	private final PrologEngine engine;
	private final Schema schema;
	private final Settings properties;
	private final PrologProvider provider;
	// private final Transaction transaction;
	// private final VolatileContainer container;
	private final ContainerFactory containerFactory;
	private final ObjectConverter<PrologTerm> converter;

	public AbstractRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter) {
		// this.transaction = new DefaultTransaction(this);
		this.containerFactory = containerFactory;
		this.engine = provider.newEngine();
		this.properties = properties;
		this.converter = converter;
		this.provider = provider;
		this.schema = schema;
	}

	public AbstractRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter,
			RelationalGraph<Object, Object> graph) {
		super(graph);
		// this.transaction = new DefaultTransaction(this);
		this.containerFactory = containerFactory;
		this.engine = provider.newEngine();
		this.properties = properties;
		this.converter = converter;
		this.provider = provider;
		this.schema = schema;
	}

	public final <O> void add(O... facts) {
		// TODO Auto-generated method stub

	}

	public final <O> void modify(O match, O merge) {
		// TODO Auto-generated method stub

	}

	public final void remove(Class<?> clazz) {
		// TODO Auto-generated method stub

	}

	public final <O> void remove(O... facts) {
		// TODO Auto-generated method stub

	}

	public final Object find(String string) throws NonSolutionError {
		// TODO Auto-generated method stub
		return null;
	}

	public final Object find(String functor, Object... args) throws NonSolutionError {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(O o) throws NonSolutionError {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(Class<O> clazz) throws NonSolutionError {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(Predicate<O> predicate) throws NonSolutionError {
		// TODO Auto-generated method stub
		return null;
	}

	public final List<Object> findAll(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public final List<Object> findAll(String functor, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> List<O> findAll(O o) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	public final void evict(Class<?> cls) {
		// TODO Auto-generated method stub
	}

	public final void evictAll() {
		// TODO Auto-generated method stub
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

	public final List<Class<?>> classesOf(PrologTerm[] prologTerms) {
		// TODO Auto-generated method stub
		return null;
	}

	public final List<Class<?>> classesOf(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> Class<O> classOf(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	public final Class<?> classOf(String functor, int arity) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> Class<O> classOf(O o) {
		// TODO Auto-generated method stub
		return null;
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
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

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final Schema getSchema() {
		return schema;
	}

	public final List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return null;
	}

	public final Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return null;
	}

}
