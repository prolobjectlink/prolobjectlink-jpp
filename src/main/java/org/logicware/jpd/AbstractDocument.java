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
package org.logicware.jpd;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.logicware.jpd.db.LockFile;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractDocument extends AbstractPersistentDocument implements Document {

	// modified flag
	private boolean dirty;

	// lock for
	// concurrent file access
	private final LockFile lock;

	// max capacity to clauses storage
	private final int maxCapacity;

	protected AbstractDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location) {
		this(provider, properties, converter, location, new LockFile(location + ".lock"));
	}

	protected AbstractDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, LockFile lock) {
		this(provider, properties, converter, location, lock, Integer.MAX_VALUE);
	}

	protected AbstractDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, int maxCapacity) {
		this(provider, properties, converter, location, new LockFile(location + ".lock"), maxCapacity);
	}

	protected AbstractDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, LockFile lock, int maxCapacity) {
		super(provider, properties, converter, location);
		this.maxCapacity = maxCapacity;
		this.lock = lock;
	}

	public final <O> void add(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				checkStorableObject(object);
				getEngine().assertz(getConverter().toTerm(object));
			}
			setDirty(true);
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
		setDirty(true);
	}

	public final <O> void remove(O... facts) {
		if (facts != null && facts.length > 0) {
			for (Object object : facts) {
				getEngine().retract(getConverter().toTerm(object));
			}
			setDirty(true);
		}
	}

	public final void remove(Class<?> clazz) {
		PrologTerm term = getConverter().toTerm(clazz);
		getEngine().abolish(term.getFunctor(), term.getArity());
		setDirty(true);
	}

	public final Object find(String string) throws NonSolutionError {
		PrologTerm[] prologTerms = getConverter().toTermsArray(string);
		List<Class<?>> classes = classesOf(prologTerms);
		return solutionOf(prologTerms, classes);
	}

	public final Object find(String functor, Object... args) throws NonSolutionError {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = reflector.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			reflector.writeValue(fields[i], instance, args[i]);
		}
		return find(instance);
	}

	public final <O> O find(O o) throws NonSolutionError {
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
		throw new NonSolutionError();
	}

	public final <O> O find(Class<O> clazz) throws NonSolutionError {
		PrologQuery query = prologQueryOf(clazz);
		if (query.hasSolution()) {
			Map<String, PrologTerm> solutionMap = query.oneVariablesSolution();
			return (O) getConverter().toObject(clazz, solutionMap);
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Predicate<O> query) throws NonSolutionError {
		List<O> all = findAll(query);
		if (!all.isEmpty())
			return all.get(0);
		throw new NonSolutionError();
	}

	public final List<Object> findAll(String string) {
		PrologTerm[] prologTerms = getConverter().toTermsArray(string);
		return solutionsOf(prologTerms, classesOf(prologTerms));
	}

	public final List<Object> findAll(String functor, Object... args) {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = reflector.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			reflector.writeValue(fields[i], instance, args[i]);
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

	public final void clear() {
		getEngine().dispose();
		setDirty(true);
	}

	public final void open() {
		getEngine().consult(getLocation());
		open = true;
	}

	public final <O> void insert(O... facts) {
		open();
		add(facts);
		flush();
	}

	public final <O> void update(O match, O update) {
		open();
		modify(match, update);
		flush();
	}

	public final <O> void delete(O... facts) {
		open();
		remove(facts);
		flush();
	}

	public final void delete(Class<?> clazz) {
		open();
		remove(clazz);
		flush();
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public final String locationOf(Class<?> clazz) {
		return getLocation();
	}

	public final List<Object> execute(String functor, Object... args) {
		open();
		List<Object> list = findAll(functor, args);
		close();
		return list;
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
		getEngine().dispose();
		open = false;
	}

}
