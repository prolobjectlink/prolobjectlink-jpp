/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.db.prolog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.ObjectConverter;
import org.worklogic.db.Predicate;
import org.worklogic.db.RelationalGraph;
import org.worklogic.db.Schema;
import org.worklogic.db.StorageGraph;
import org.worklogic.db.StorageMode;
import org.worklogic.db.etc.Settings;
import org.worklogic.db.storage.AbstractStorageGraph;
import org.worklogic.logging.LoggerConstants;
import org.worklogic.logging.LoggerUtils;

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
