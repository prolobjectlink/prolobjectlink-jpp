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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.VolatileContainer;
import org.prolobjectlink.db.container.AbstractVolatileContainer;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologQuery;
import org.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractHierarchicalCache extends AbstractVolatileContainer implements VolatileContainer {

	public AbstractHierarchicalCache(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter) {
		super(provider, properties, converter);
	}

	public final <O> void add(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				checkStorableObject(object);
				getEngine().assertz(getConverter().toTerm(object));
			}
		}
	}

	public final <O> void modify(O match, O merge) {
		checkStorableObject(match);
		checkStorableObject(merge);
		checkReplacementObject(match, merge);
		PrologTerm prologMatch = getConverter().toTerm(match);
		PrologTerm prologMerge = getConverter().toTerm(merge);
		getEngine().retract(prologMatch);
		getEngine().assertz(prologMerge);
	}

	public final <O> void remove(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				getEngine().retract(getConverter().toTerm(object));
			}
		}
	}

	public final void remove(Class<?> clazz) {
		PrologTerm term = getConverter().toTerm(clazz);
		getEngine().abolish(term.getFunctor(), term.getArity());
	}

	public final Object find(String string) {
		PrologTerm[] prologTerms = getConverter().toTermsArray(string);
		List<Class<?>> classes = classesOf(prologTerms);
		return solutionOf(prologTerms, classes);
	}

	public final Object find(String functor, Object... args) {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = JavaReflect.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			JavaReflect.writeValue(fields[i], instance, args[i]);
		}
		return find(instance);
	}

	public final <O> O find(O o) {
		Map<String, PrologTerm> inspectionMap = new HashMap<String, PrologTerm>();
		PrologQuery query = prologQueryOf(o, inspectionMap);
		if (query.hasSolution()) {
			Map<String, PrologTerm> solutionMap = query.oneVariablesSolution();
			if (!solutionMap.isEmpty()) {

				// put all resolved by inspection
				solutionMap.putAll(inspectionMap);
				return (O) getConverter().toObject(classOf(o), solutionMap);
			} else {
				// the term exist and is equivalent to the query term
				return o;
			}
		}
		return null;
	}

	public final <O> O find(Class<O> clazz) {
		PrologQuery query = prologQueryOf(clazz);
		if (query.hasSolution()) {
			Map<String, PrologTerm> solutionMap = query.oneVariablesSolution();
			return (O) getConverter().toObject(clazz, solutionMap);
		}
		return null;
	}

	public final <O> O find(Predicate<O> query) {
		List<O> all = findAll(query);
		if (!all.isEmpty())
			return all.get(0);
		return null;
	}

	public final List<Object> findAll(String string) {
		PrologTerm[] prologTerms = getConverter().toTermsArray(string);
		return solutionsOf(prologTerms, classesOf(prologTerms));
	}

	public final List<Object> findAll(String functor, Object... args) {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = JavaReflect.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			JavaReflect.writeValue(fields[i], instance, args[i]);
		}
		return findAll(instance);
	}

	public final <O> List<O> findAll(O o) {
		Map<String, PrologTerm> inspectionMap = new HashMap<String, PrologTerm>();
		PrologTerm goal = getConverter().toTerm(o, inspectionMap);
		PrologQuery query = getEngine().query(goal);
		List<O> solutionSet = new ArrayList<O>();
		if (query.hasSolution()) {
			Map<String, PrologTerm>[] solutionsMap = query.allVariablesSolutions();
			if (solutionsMap.length > 0) {
				for (int i = 0; i < solutionsMap.length; i++) {
					Map<String, PrologTerm> solutionMap = solutionsMap[i];
					solutionMap.putAll(inspectionMap);
					O solution = (O) getConverter().toObject(classOf(o), solutionMap);
					solutionSet.add(solution);
				}
			} else {
				// a new term is built from the goal because
				// the term exist but is equivalent to the query term
				O solution = (O) getConverter().toObject(goal);
				// add to the solution as unique solution
				solutionSet.add(solution);
			}
		}
		return solutionSet;
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		PrologQuery query = prologQueryOf(clazz);
		List<O> solutionSet = new ArrayList<O>();
		if (query.hasSolution()) {
			Map<String, PrologTerm>[] solutionsMap = query.allVariablesSolutions();
			for (int i = 0; i < solutionsMap.length; i++) {
				O solution = (O) getConverter().toObject(clazz, solutionsMap[i]);
				solutionSet.add(solution);
			}
		}
		return solutionSet;
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		List<O> selection = new ArrayList<O>();
		Class<O> toBeFound = classOf(predicate);
		List<O> allObjects = findAll(toBeFound);
		for (O o : allObjects) {
			if (predicate.evaluate(o)) {
				selection.add(o);
			}
		}
		return selection;
	}

	public final List<Class<?>> classes() {
		// TODO Auto-generated method stub
		return new ArrayList<Class<?>>();
	}

	public final void evict(Class<?> cls) {
		remove(cls);
	}

	public final void evictAll() {
		clear();
	}

	public final void clear() {
		getEngine().dispose();
	}

}
