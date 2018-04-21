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
package org.logicware.prolog;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.logicware.ClassNotFoundError;
import org.logicware.ContainerFactory;
import org.logicware.DatabaseSchema;
import org.logicware.ObjectConverter;
import org.logicware.Predicate;
import org.logicware.Settings;
import org.logicware.StorageGraph;
import org.logicware.graph.RelationalGraph;
import org.logicware.storage.AbstractStorageGraph;

public class PrologStorageGraph extends AbstractStorageGraph implements StorageGraph {

	private final ObjectConverter<PrologTerm> converter;

	public PrologStorageGraph(PrologProvider provider, Settings properties, String location, DatabaseSchema schema,
			ContainerFactory containerFactory) {
		this(location, schema, properties, provider, containerFactory, new PrologObjectConverter(provider));
	}

	public PrologStorageGraph(String location, DatabaseSchema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter) {
		super(location, schema, properties, provider, containerFactory, converter);
		this.converter = converter;
	}

	public PrologStorageGraph(String location, DatabaseSchema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter,
			RelationalGraph<Object, Object> graph) {
		super(location, schema, properties, provider, containerFactory, converter, graph);
		this.converter = converter;
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
				throw new ClassNotFoundError("" + type + "");
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
	 * @param o
	 *            object to known your class
	 * @return class of object {@code o}
	 */
	public final <O> Class<O> classOf(O o) {
		return (Class<O>) o.getClass();
	}

}
