/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.db;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.logicware.AbstractWrapper;
import org.logicware.ClassNotFoundError;
import org.logicware.Container;
import org.logicware.ObjectConverter;
import org.logicware.ObjectReflector;
import org.logicware.PersistenceError;
import org.logicware.Predicate;
import org.logicware.ProcedureInvokationError;
import org.logicware.Settings;
import org.logicware.UpdateError;
import org.logicware.prolog.PrologClause;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologQuery;
import org.logicware.prolog.PrologTerm;

/**
 * Implementation of interface {@code Container}. Hold an immutable reference to
 * {@code IPrologFactory} and to the {@code IPrologEngine} provided by this
 * factory. The {@code ObjectSerializer} immutable reference is contained too
 * for convert from object oriented context to prolog context and vice versa.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractContainer extends AbstractWrapper implements Container {

	// Prolog engine reference
	private final PrologEngine engine;

	// prolog driver
	private final PrologProvider provider;

	//
	private final Settings properties;

	// factory for term creation
	private final ObjectConverter<PrologTerm> converter;

	// non store classes map
	private static final Set<Class<?>> classes;

	static {
		classes = new HashSet<Class<?>>();
		classes.add(String.class);
		classes.add(Boolean.class);
		classes.add(Integer.class);
		classes.add(Float.class);
		classes.add(Long.class);
		classes.add(Double.class);
		classes.add(Object[].class);
	}

	protected final ObjectReflector reflector = new ObjectReflector();

	protected AbstractContainer(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter) {
		this.engine = provider.newEngine();
		this.properties = properties;
		this.converter = converter;
		this.provider = provider;
	}

	public final List<Object> findAll() {
		List<Object> list = new ArrayList<Object>();
		for (PrologClause clause : engine) {
			if (clause.isFact()) {
				PrologTerm t = clause.getHead();
				list.add(converter.toObject(t));
			}
		}
		return list;
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

	public final <O> Class<O> classOf(O o) {
		return (Class<O>) o.getClass();
	}

	public final Class<?> classOf(String functor, int arity) {
		String className = removeQuoted(functor);
		Class<?> clazz = reflector.classForName(className);
		if (clazz.getDeclaredFields().length != arity) {
			throw new ClassNotFoundError(className, arity);
		}
		return clazz;
	}

	public final <O> Class<O> classOf(Predicate<O> predicate) {
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

	public final List<Class<?>> classesOf(String string) {
		PrologTerm[] prologTerms = converter.toTermsArray(string);
		return classesOf(prologTerms);
	}

	public final List<Class<?>> classesOf(PrologTerm[] prologTerms) {
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (int i = 0; i < prologTerms.length; i++) {
			PrologTerm prologTerm = prologTerms[i];
			if (prologTerm.isStructure() && !prologTerm.isEvaluable()) {
				String functor = prologTerms[i].getFunctor();
				String className = removeQuoted(functor);
				try {
					Class<?> clazz = Class.forName(className);
					if (clazz != null) {
						classList.add(clazz);
					}
				} catch (ClassNotFoundException e) {
					throw new ClassNotFoundError(functor, e);
				}
			}
		}
		return classList;
	}

	protected final Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		PrologQuery query = engine.query(prologTerms);
		if (query.hasSolution()) {
			Map<String, PrologTerm> solutionsMap = query.oneVariablesSolution();
			Object[] objects = new Object[classes.size()];
			for (int i = 0; i < classes.size(); i++) {
				objects[i] = converter.toObject(classes.get(i), solutionsMap);
			}
			return objects;
		}
		return new Object[0];
	}

	/**
	 * Find all objects solutions of the prolog terms array. Use a given list of
	 * classes extracted from prolog terms array for convert the result obtained
	 * from prolog query result.
	 * 
	 * @param prologTerms
	 *            prolog terms array to query.
	 * 
	 * @param classes
	 *            classes extracted from prolog terms array.
	 * @return all objects solutions of prologTerms prolog terms array.
	 */
	protected final List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
		List<Object> solutionSet = new ArrayList<Object>();
		PrologQuery query = engine.query(prologTerms);
		if (query.hasSolution()) {
			Map<String, PrologTerm>[] solutionsMap = query.allVariablesSolutions();
			for (int i = 0; i < solutionsMap.length; i++) {
				Object[] objects = new Object[classes.size()];
				for (int j = 0; j < classes.size(); j++) {
					objects[j] = converter.toObject(classes.get(j), solutionsMap[i]);
				}
				solutionSet.add(objects);
			}
		}
		return solutionSet;
	}

	protected final PrologQuery prologQueryOf(Class<?> clazz) {
		PrologTerm goal = converter.toTerm(clazz);
		return engine.query(goal);
	}

	protected final PrologQuery prologQueryOf(Object object, Map<String, PrologTerm> map) {
		PrologTerm goal = converter.toTerm(object, map);
		return engine.query(goal);
	}

	protected final String removeQuoted(String functor) {
		if (functor != null && functor.startsWith("\'") && functor.endsWith("\'")) {
			return functor.substring(1, functor.length() - 1);
		}
		return functor;
	}

	protected final void checkStorableObject(Object object) {
		if (classes.contains(object.getClass()) || object.getClass().isArray()) {
			throw new PersistenceError(object);
		}
	}

	protected final void checkReplacementObject(Object match, Object update) {
		if (match.getClass() != update.getClass()) {
			throw new UpdateError(match, update);
		}
	}

	protected final void checkProcedureInvokation(String name, Field[] fields, Object[] values) {
		int fieldsNumber = fields.length;
		int valuesNumber = values.length;
		if (fieldsNumber != valuesNumber) {
			throw new ProcedureInvokationError(name, fieldsNumber, valuesNumber);
		}
	}

}
