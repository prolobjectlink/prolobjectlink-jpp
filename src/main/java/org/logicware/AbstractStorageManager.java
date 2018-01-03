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
package org.logicware;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractStorageManager extends AbstractPersistentContainer implements StorageManager {

	// container register mapping class name -> persistent container
	private final HashMap<String, PersistentContainer> master;

	protected AbstractStorageManager(PrologProvider provider, Properties properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory) {
		super(provider, properties, converter, location, containerFactory);
		this.master = new HashMap<String, PersistentContainer>();
	}

	public final void open() {
		open = true;
	}

	public final <O> void insert(O... objects) {
		if (objects != null && objects.length > 0) {
			Class<?> clazz = objects.getClass();
			Class<?> type = clazz.getComponentType();
			containerOf(type).insert(objects);
		}
	}

	public final <O> void update(O match, O update) {
		checkReplacementObject(match, update);
		Class<?> clazz = match.getClass();
		containerOf(clazz).update(match, update);
	}

	/**
	 * bulk deletion for non null objects array
	 * 
	 * @param objects
	 */
	public final <O> void delete(O... objects) {
		if (objects != null && objects.length > 0) {
			Class<?> clazz = objects.getClass();
			Class<?> type = clazz.getComponentType();
			containerOf(type).delete(objects);
		}
	}

	public final void delete(Class<?> clazz) {
		containerOf(clazz).delete(clazz);
		String path = locationOf(clazz);
		File fileLock = new File(path + ".lock");
		File file = new File(path);
		if (file.delete()) {
			fileLock.deleteOnExit();
		}
	}

	public final boolean contains(String string) {
		List<Class<?>> classes = classesOf(string);
		for (Class<?> clazz : classes) {
			getEngine().include(locationOf(clazz));
		}
		return getEngine().contains(string);
	}

	public final <O> boolean contains(O o) {
		PersistentContainer container = containerOf(classOf(o));
		container.getTransaction().begin();
		boolean contains = container.contains(o);
		container.getTransaction().commit();
		container.close();
		return contains;
	}

	public final <O> boolean contains(Class<O> clazz) {
		PersistentContainer container = containerOf(clazz);
		container.getTransaction().begin();
		boolean contains = container.contains(clazz);
		container.getTransaction().commit();
		container.close();
		return contains;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		PersistentContainer container = containerOf(classOf(predicate));
		container.getTransaction().begin();
		boolean contains = container.contains(predicate);
		container.getTransaction().commit();
		container.close();
		return contains;
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		return containerOf(classOf(o)).createQuery(o);
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		return containerOf(clazz).createQuery(clazz);
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		return containerOf(classOf(predicate)).createQuery(predicate);
	}

	public final <O> ConstraintQuery<O> createConstraintQuery(Class<O> clazz) {
		return containerOf(clazz).createConstraintQuery(clazz);
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		return containerOf(classOf(functor, args.length)).createProcedureQuery(functor, args);
	}

	public final Transaction getTransaction() {
		return new DefaultTransaction(this);
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		String key = clazz.getName();
		PersistentContainer container = master.get(key);
		if (container == null) {
			String path = locationOf(clazz);
			container = containerFactory.createStorage(path);
			master.put(key, container);
		}
		return container;
	}

	public final String locationOf(Class<?> clazz) {
		String name = clazz.getName();
		String path = name.replace('.', SEPARATOR);
		return getLocation() + SEPARATOR + path;
	}

	public final void flush() {
		for (PersistentContainer c : master.values()) {
			c.flush();
		}
	}

	public final void clear() {
		master.clear();
	}

	public final void close() {
		for (PersistentContainer c : master.values()) {
			c.clear();
			c.close();
		}
		master.clear();
		open = false;
	}

}
