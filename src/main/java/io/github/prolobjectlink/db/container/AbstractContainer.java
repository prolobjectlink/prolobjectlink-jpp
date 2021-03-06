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
package io.github.prolobjectlink.db.container;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.prolobjectlink.AbstractWrapper;
import io.github.prolobjectlink.db.Container;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.ProcedureInvokationError;
import io.github.prolobjectlink.db.Transaction;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologQuery;
import io.github.prolobjectlink.prolog.PrologTerm;

/**
 * Implementation of interface {@code Container}. Hold an immutable reference to
 * {@code IPrologFactory} and to the {@code IPrologEngine} provided by this
 * factory. The {@code ObjectSerializer} immutable reference is contained too
 * for convert from object oriented context to prolog context and vice versa.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
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

	protected AbstractContainer(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter) {
		this.engine = provider.newEngine();
		this.properties = properties;
		this.converter = converter;
		this.provider = provider;
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
		Class<?> clazz = JavaReflect.classForName(className);
		if (clazz.getDeclaredFields().length != arity) {
			try {
				throw new IllegalArgumentException("Non exist " + className + " with " + arity + " fields");
			} catch (IllegalArgumentException e) {
				LoggerUtils.error(getClass(), LoggerConstants.ILLEGAL_ARGUMENT, e);
			}
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

	public final List<Class<?>> classesOf(String string) {
		return classesOf(converter.toTermsArray(string));
	}

	public final List<Class<?>> classesOf(PrologTerm[] prologTerms) {
		return classesOf(prologTerms, new ArrayList<Class<?>>());
	}

	public final Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
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
	 * @param prologTerms prolog terms array to query.
	 * 
	 * @param classes     classes extracted from prolog terms array.
	 * @return all objects solutions of prologTerms prolog terms array.
	 */
	public final List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes) {
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
		return engine.query(converter.toTerm(clazz));
	}

	protected final PrologQuery prologQueryOf(Object object, Map<String, PrologTerm> map) {
		return engine.query(converter.toTerm(object, map));
	}

	protected final List<Class<?>> classesOf(PrologTerm[] prologTerms, List<Class<?>> classList) {
		for (int i = 0; i < prologTerms.length; i++) {
			PrologTerm prologTerm = prologTerms[i];
			if (prologTerm.isStructure()) {
				String functor = prologTerms[i].getFunctor();
				PrologTerm[] args = prologTerm.getArguments();
				if (!prologTerm.isEvaluable()) {
					String className = removeQuoted(functor);
					Class<?> clazz = JavaReflect.classForName(className);
					if (!classList.contains(clazz)) {
						classList.add(clazz);
					}
				}
				classesOf(args, classList);
			}
		}
		return classList;
	}

	protected final String removeQuoted(String functor) {
		if (functor != null && functor.startsWith("\'") && functor.endsWith("\'")) {
			return functor.substring(1, functor.length() - 1);
		}
		return functor;
	}

	protected final void checkStorableObject(Object object) {
		if (classes.contains(object.getClass()) || object.getClass().isArray()) {
			throw new RuntimeException("The object do not belong to persistent class: " + object);
		}
	}

	protected final void checkReplacementObject(Object match, Object update) {
		if (match.getClass() != update.getClass()) {
			throw new RuntimeException(
					"The update object [ " + match + "]class is different to match object [" + update + "]");
		}
	}

	protected final void checkProcedureInvokation(String name, Field[] fields, Object[] values) {
		int fieldsNumber = fields.length;
		int valuesNumber = values.length;
		if (fieldsNumber != valuesNumber) {
			throw new ProcedureInvokationError(name, fieldsNumber, valuesNumber);
		}
	}

	protected final void checkActiveTransaction(Transaction transaction) {
		if (!transaction.isActive()) {
			throw new IllegalStateException("Entity Transaction is not active");
		}
	}

}
