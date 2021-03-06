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
package io.github.prolobjectlink.db.storage;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.LockFile;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.PersistentContainer;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.container.AbstractPersistentContainer;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologQuery;
import io.github.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractStorage extends AbstractPersistentContainer implements Storage {

	// modified flag
	private boolean dirty;

	// lock for
	// concurrent file access
	private final LockFile lock;

	// max capacity to clauses storage
	private final int maxCapacity;

	protected AbstractStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory) {
		this(provider, properties, converter, location, containerFactory, Integer.MAX_VALUE);
	}

	protected AbstractStorage(PrologProvider provider, Settings settings, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, int maxCapacity) {
		this(provider, settings, converter, location, containerFactory, new LockFile(location + ".lock", settings),
				maxCapacity);
	}

	protected AbstractStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, LockFile lock, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory);
		this.maxCapacity = maxCapacity;
		this.lock = lock;
	}

	public final boolean contains(String string) {
		return getEngine().contains(string);
	}

	public final <O> boolean contains(O object) {
		return getEngine().contains(getConverter().toTerm(object));
	}

	public final <O> boolean contains(Class<O> clazz) {
		return getEngine().contains(getConverter().toTerm(clazz));
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		Class<O> clazz = classOf(predicate);
		PrologQuery query = prologQueryOf(clazz);
		if (query.hasSolution()) {
			Map<String, PrologTerm>[] solutionsMap = query.allVariablesSolutions();
			for (int i = 0; i < solutionsMap.length; i++) {
				O solution = (O) getConverter().toObject(clazz, solutionsMap[i]);
				if (predicate.evaluate(solution)) {
					return true;
				}
			}
		}
		return false;
	}

	public final boolean contains(String functor, int arity) {
		return getEngine().currentPredicate(functor, arity);
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

	public final Collection<Class<?>> classes() {
		Map<String, Class<?>> m = new HashMap<String, Class<?>>();
		for (PrologClause clause : getEngine()) {
			String functor = removeQuoted(clause.getFunctor());
			m.put(functor, JavaReflect.classForName(functor));
		}
		return m.values();
	}

	public final void clear() {
		getEngine().dispose();
		setDirty(true);
	}

	public final void open() {
		getEngine().consult(getLocation());
		open = true;
	}

	public final <O> void insert(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				checkStorableObject(object);
				getEngine().assertz(getConverter().toTerm(object));
			}
			setDirty(true);
		}
	}

	public final <O> void update(O match, O merge) {
		checkStorableObject(match);
		checkStorableObject(merge);
		checkReplacementObject(match, merge);
		PrologTerm pMatch = getConverter().toTerm(match);
		PrologTerm pMerge = getConverter().toTerm(merge);
		getEngine().retract(pMatch);
		getEngine().assertz(pMerge);
		setDirty(true);
	}

	public final <O> void delete(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				getEngine().retract(getConverter().toTerm(object));
			}
			setDirty(true);
		}
	}

	public final void delete(Class<?> clazz) {
		PrologTerm term = getConverter().toTerm(clazz);
		getEngine().abolish(term.getFunctor(), term.getArity());
		setDirty(true);
	}

	public final void include(String path) {
		getEngine().include(path);
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public final String locationOf(Class<?> clazz) {
		return getLocation();
	}

	public final boolean hasCapacity() {
		return getSize() < getCapacity();
	}

	public final int getCapacity() {
		return maxCapacity;
	}

	public final long getLength() {
		return getFile().length();
	}

	public final int getSize() {
		return getEngine().getProgramSize();
	}

	public final File getFile() {
		return new File(getLocation());
	}

	public final void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	public final boolean isDirty() {
		return dirty;
	}

	public final boolean locked() {
		return lock.islock();
	}

	public final void defragment() {
		// do nothing
	}

	public final void flush() {
		lock.lock();
		try {
			getEngine().persist(getLocation());
			setDirty(false);
		} finally {
			lock.unlock();
		}
	}

	public final void close() {
		getTransaction().close();
		getEngine().dispose();
		open = false;
	}

}
