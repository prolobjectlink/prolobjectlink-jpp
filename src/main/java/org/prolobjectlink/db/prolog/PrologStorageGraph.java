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
package org.prolobjectlink.db.prolog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.RelationalGraph;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.StorageGraph;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.storage.AbstractStorageGraph;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

public class PrologStorageGraph extends AbstractStorageGraph implements StorageGraph {

	private final ObjectConverter<PrologTerm> converter;

	public PrologStorageGraph(PrologProvider provider, Settings properties, String location, Schema schema,
			ContainerFactory containerFactory, StorageMode storageMode) {
		this(location, schema, properties, provider, containerFactory, new PrologObjectConverter(provider),
				storageMode);
	}

	public PrologStorageGraph(String location, Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter, StorageMode storageMode) {
		super(location, schema, properties, provider, containerFactory, converter, storageMode);
		this.converter = converter;
	}

	public PrologStorageGraph(String location, Schema schema, Settings properties, StorageMode storageMode,
			RelationalGraph<Object, Object> graph) {
		super(location, schema, properties, storageMode, graph);
		this.converter = properties.getObjectConverter();
	}

	public List<Class<?>> classesOf(PrologTerm[] prologTerms) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Class<?>> classesOf(String string) {
		PrologTerm[] prologTerms = converter.toTermsArray(string);
		return classesOf(prologTerms);
	}

	public <O> Class<O> classOf(Predicate<O> predicate) {
		Class<O> templateClass = null;
		Class<?> clazz = predicate.getClass();
		Type[] generics = clazz.getGenericInterfaces();
		if (generics.length == 1 && generics[0] instanceof ParameterizedType) {
			ParameterizedType parameterized = (ParameterizedType) generics[0];
			Type type = parameterized.getActualTypeArguments()[0];
			if (!(type instanceof Class<?>)) {
				try {
					throw new ClassNotFoundException("" + type + "");
				} catch (ClassNotFoundException e) {
					LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
				}
			}
			templateClass = (Class<O>) type;
		}
		return templateClass;
	}

	public Class<?> classOf(String functor, int length) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Allow known the class of some given object
	 * 
	 * @param o object to known your class
	 * @return class of object {@code o}
	 */
	public final <O> Class<O> classOf(O o) {
		return (Class<O>) o.getClass();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((converter == null) ? 0 : converter.hashCode());
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
		PrologStorageGraph other = (PrologStorageGraph) obj;
		if (converter == null) {
			if (other.converter != null)
				return false;
		} else if (!converter.equals(other.converter))
			return false;
		return true;
	}

}
