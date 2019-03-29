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
package org.prolobjectlink.db.cache;

import java.util.ArrayList;
import java.util.List;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.RelationalCache;
import org.prolobjectlink.db.RelationalGraph;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

public class AbstractRelationalCache extends RelationalGraph<Object, Object> implements RelationalCache {

	private final Schema schema;
	private final PrologEngine engine;
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

	public final Object find(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public final Object find(String functor, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(O o) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(Class<O> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	public final <O> O find(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return null;
	}

	public final List<Object> findAll(String string) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	public final List<Object> findAll(String functor, Object... args) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	public final <O> List<O> findAll(O o) {
		// TODO Auto-generated method stub
		return new ArrayList<O>();
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		// TODO Auto-generated method stub
		return new ArrayList<O>();
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		// TODO Auto-generated method stub
		return new ArrayList<O>();
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

	public final List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return new ArrayList<Object>();
	}

	public final Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		// TODO Auto-generated method stub
		return new Object[0];
	}

	public final List<Class<?>> classesOf(PrologTerm[] prologTerms) {
		// TODO Auto-generated method stub
		return new ArrayList<Class<?>>();
	}

	public final List<Class<?>> classesOf(String string) {
		// TODO Auto-generated method stub
		return new ArrayList<Class<?>>();
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

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final Schema getSchema() {
		return schema;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
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
		AbstractRelationalCache other = (AbstractRelationalCache) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema))
			return false;
		return true;
	}

}
