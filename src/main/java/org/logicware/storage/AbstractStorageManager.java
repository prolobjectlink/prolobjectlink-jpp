/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.storage;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.logicware.ContainerFactory;
import org.logicware.DefaultTransaction;
import org.logicware.ObjectConverter;
import org.logicware.PersistentContainer;
import org.logicware.Predicate;
import org.logicware.ProcedureQuery;
import org.logicware.Query;
import org.logicware.Settings;
import org.logicware.Storage;
import org.logicware.StorageManager;
import org.logicware.StorageMode;
import org.logicware.StoragePool;
import org.logicware.Transaction;
import org.logicware.TypedQuery;
import org.logicware.db.AbstractPersistentContainer;
import org.logicware.prolog.PrologContainerQuery;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.logicware.util.ArrayList;

public abstract class AbstractStorageManager extends AbstractPersistentContainer implements StorageManager {

	private final StorageMode storageMode;
	private final Transaction transaction;
	private final HashMap<String, PersistentContainer> master;

	protected AbstractStorageManager(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory,
			StorageMode storageMode) {
		super(provider, properties, converter, location, containerFactory);
		this.master = new HashMap<String, PersistentContainer>();
		this.transaction = new DefaultTransaction(this);
		this.storageMode = storageMode;
	}

	public final void open() {
		open = true;
	}

	public final <O> void insert(O... objects) {
		checkActiveTransaction(transaction);
		if (objects != null && objects.length > 0) {
			Class<?> clazz = objects.getClass();
			Class<?> type = clazz.getComponentType();
			containerOf(type).insert(objects);
		}
	}

	public final <O> void update(O match, O update) {
		checkActiveTransaction(transaction);
		checkReplacementObject(match, update);
		Class<?> clazz = match.getClass();
		containerOf(clazz).update(match, update);
	}

	public final <O> void delete(O... objects) {
		checkActiveTransaction(transaction);
		if (objects != null && objects.length > 0) {
			Class<?> clazz = objects.getClass();
			Class<?> type = clazz.getComponentType();
			containerOf(type).delete(objects);
		}
	}

	public final void delete(Class<?> clazz) {
		checkActiveTransaction(transaction);
		containerOf(clazz).delete(clazz);
		String path = locationOf(clazz);
		File fileLock = new File(path + ".lock");
		File file = new File(path);
		if (file.delete()) {
			fileLock.deleteOnExit();
		}
	}

	public final boolean contains(String string) {
		checkActiveTransaction(transaction);
		List<Class<?>> classes = classesOf(string);
		for (Class<?> clazz : classes) {
			PersistentContainer pc = containerOf(clazz);
			if (pc instanceof StoragePool) {
				StoragePool sp = (StoragePool) pc;
				sp.getTransaction().begin();
				List<Storage> storages = sp.getStorages();
				for (Storage storage : storages) {
					String path = storage.getLocation();
					getEngine().include(path);
				}
				sp.getTransaction().close();
			}
		}
		return getEngine().contains(string);
	}

	public final <O> boolean contains(O o) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(o)).contains(o);
	}

	public final <O> boolean contains(Class<O> clazz) {
		checkActiveTransaction(transaction);
		return containerOf(clazz).contains(clazz);
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(predicate)).contains(predicate);
	}

	public final boolean contains(String f, int a) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(f, a)).contains(removeQuoted(f), a);
	}

	public final Query createQuery(String string) {
		checkActiveTransaction(transaction);
		ObjectConverter<PrologTerm> c = getConverter();
		PrologTerm[] terms = c.toTermsArray(string);
		List<Class<?>> classes = classesOf(terms);
		for (Class<?> clazz : classes) {
			PersistentContainer pc = containerOf(clazz);
			if (pc instanceof StoragePool) {
				StoragePool sp = (StoragePool) pc;
				sp.getTransaction().begin();
				List<Storage> storages = sp.getStorages();
				for (Storage storage : storages) {
					String path = storage.getLocation();
					getEngine().include(path);
				}
				sp.getTransaction().close();
			}
		}
		return new PrologContainerQuery(solutionsOf(terms, classes));
	}

	public final <O> TypedQuery<O> createQuery(O o) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(o)).createQuery(o);
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		checkActiveTransaction(transaction);
		return containerOf(clazz).createQuery(clazz);
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(predicate)).createQuery(predicate);
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		checkActiveTransaction(transaction);
		return containerOf(classOf(functor, args.length)).createProcedureQuery(functor, args);
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		String key = clazz.getName();
		String name = clazz.getSimpleName();
		PersistentContainer container = master.get(key);
		if (container == null) {
			String path = locationOf(clazz);
			if (storageMode == StorageMode.STORAGE_POOL) {
				container = containerFactory.createStoragePool(path, name);
			} else if (storageMode == StorageMode.SINGLE_STORAGE) {
				container = containerFactory.createStorage(path);
			}
			master.put(key, container);
		}
		return container;
	}

	public final void include(String path) {
		for (PersistentContainer c : master.values()) {
			c.include(path);
		}
	}

	public final String locationOf(Class<?> clazz) {
		String name = clazz.getName();
		String path = name.replace('.', SEPARATOR);
		path = path.substring(0, path.lastIndexOf(SEPARATOR));
		return getLocation() + SEPARATOR + path;
	}

	public final void defragment() {
		for (PersistentContainer c : master.values()) {
			c.defragment();
		}
	}

	public final Collection<Class<?>> classes() {
		int s = master.size();
		List<Class<?>> l = new ArrayList<Class<?>>(s);
		for (PersistentContainer c : master.values()) {
			l.addAll(c.classes());
		}
		return l;
	}

	public final void flush() {
		for (PersistentContainer c : master.values()) {
			c.flush();
		}
	}

	public final void clear() {
		checkActiveTransaction(transaction);
		for (PersistentContainer c : master.values()) {
			c.clear();
		}
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
