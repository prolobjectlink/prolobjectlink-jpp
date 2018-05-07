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
package org.logicware.pdb.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Storage;
import org.logicware.pdb.StorageManager;
import org.logicware.pdb.StorageMode;
import org.logicware.pdb.StoragePool;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.common.AbstractPersistentContainer;
import org.logicware.pdb.prolog.PrologContainerQuery;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

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
			PersistentContainer c = containerOf(type);
			if (c != null) {
				c.getTransaction().begin();
				c.insert(objects);
				c.getTransaction().commit();
				c.getTransaction().close();
			}
		}
	}

	public final <O> void update(O match, O update) {
		checkActiveTransaction(transaction);
		checkReplacementObject(match, update);
		Class<?> clazz = match.getClass();
		PersistentContainer c = containerOf(clazz);
		if (c != null) {
			c.getTransaction().begin();
			c.update(match, update);
			c.getTransaction().commit();
			c.getTransaction().close();
		}
	}

	public final <O> void delete(O... objects) {
		checkActiveTransaction(transaction);
		if (objects != null && objects.length > 0) {
			Class<?> clazz = objects.getClass();
			Class<?> type = clazz.getComponentType();
			PersistentContainer c = containerOf(type);
			if (c != null) {
				c.getTransaction().begin();
				c.delete(objects);
				c.getTransaction().commit();
				c.getTransaction().close();
			}
		}
	}

	public final void delete(Class<?> clazz) {
		checkActiveTransaction(transaction);
		PersistentContainer c = containerOf(clazz);
		if (c != null) {
			c.getTransaction().begin();
			c.delete(clazz);
			c.getTransaction().commit();
			c.getTransaction().close();
			String path = locationOf(clazz);
			File fileLock = new File(path + ".lock");
			File file = new File(path);
			if (file.delete()) {
				fileLock.deleteOnExit();
			}
		}
	}

	public final boolean contains(String string) {
		checkActiveTransaction(transaction);
		List<Class<?>> classes = classesOf(string);
		for (Class<?> clazz : classes) {
			PersistentContainer pc = containerOf(clazz);
			if (pc != null && pc instanceof StoragePool) {
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
		Class<O> type = classOf(o);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			boolean chk = c.contains(o);
			c.getTransaction().commit();
			c.getTransaction().close();
			return chk;
		}
		return false;
	}

	public final <O> boolean contains(Class<O> clazz) {
		checkActiveTransaction(transaction);
		PersistentContainer c = containerOf(clazz);
		if (c != null) {
			c.getTransaction().begin();
			boolean chk = c.contains(clazz);
			c.getTransaction().commit();
			c.getTransaction().close();
			return chk;
		}
		return false;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		Class<O> type = classOf(predicate);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			boolean chk = c.contains(predicate);
			c.getTransaction().commit();
			c.getTransaction().close();
			return chk;
		}
		return false;
	}

	public final boolean contains(String f, int a) {
		checkActiveTransaction(transaction);
		Class<?> type = classOf(f, a);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			boolean chk = c.contains(f, a);
			c.getTransaction().commit();
			c.getTransaction().close();
			return chk;
		}
		return false;
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
		Class<O> type = classOf(o);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			TypedQuery<O> q = c.createQuery(o);
			c.getTransaction().commit();
			c.getTransaction().close();
			return q;
		}

		// TODO create and use an empty query
		return null;
	}

	public final <O> TypedQuery<O> createQuery(Class<O> clazz) {
		checkActiveTransaction(transaction);
		PersistentContainer c = containerOf(clazz);
		if (c != null) {
			c.getTransaction().begin();
			TypedQuery<O> q = c.createQuery(clazz);
			c.getTransaction().commit();
			c.getTransaction().close();
			return q;
		}

		// TODO create and use an empty query
		return null;
	}

	public final <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		Class<O> type = classOf(predicate);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			TypedQuery<O> q = c.createQuery(predicate);
			c.getTransaction().commit();
			c.getTransaction().close();
			return q;
		}

		// TODO create and use an empty query
		return null;
	}

	public final ProcedureQuery createProcedureQuery(String functor, String... args) {
		checkActiveTransaction(transaction);
		Class<?> type = classOf(functor, args.length);
		PersistentContainer c = containerOf(type);
		if (c != null) {
			c.getTransaction().begin();
			ProcedureQuery q = c.createProcedureQuery(functor, args);
			c.getTransaction().commit();
			c.getTransaction().close();
			return q;
		}

		// TODO create and use an empty query
		return null;
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
			c.getTransaction().begin();
			l.addAll(c.classes());
			c.getTransaction().close();
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
			c.getTransaction().begin();
			c.clear();
			c.getTransaction().commit();
			c.getTransaction().close();
		}
		master.clear();
	}

	public final void close() {
		for (PersistentContainer c : master.values()) {
			c.getTransaction().begin();
			c.clear();
			c.close();
			c.getTransaction().commit();
			c.getTransaction().close();
		}
		master.clear();
		open = false;
	}

}