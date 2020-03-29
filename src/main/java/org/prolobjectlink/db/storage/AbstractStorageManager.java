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
package org.prolobjectlink.db.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.Storage;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.StoragePool;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.container.AbstractPersistentContainer;
import org.prolobjectlink.db.container.DummyProcedureQuery;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.prolog.PrologContainerQuery;
import org.prolobjectlink.db.prolog.PrologTypedQuery;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;
import org.prolobjectlink.db.util.JavaLists;

import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractStorageManager extends AbstractPersistentContainer implements StorageManager {

	private final StorageMode storageMode;
	private final Transaction transaction;
	private final IdentityHashMap<Class<?>, PersistentContainer> master;
	private final IdentityHashMap<Class<?>, PersistentContainer> logger;

	protected AbstractStorageManager(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory,
			StorageMode storageMode) {
		super(provider, properties, converter, location, containerFactory);
		this.master = new IdentityHashMap<Class<?>, PersistentContainer>();
		this.logger = new IdentityHashMap<Class<?>, PersistentContainer>();
		this.transaction = new PersistentContainerTransaction(this);
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
		ArrayList<O> s = new ArrayList<O>();
		return new PrologTypedQuery<O>(s);
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
		ArrayList<O> s = new ArrayList<O>();
		return new PrologTypedQuery<O>(s);
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
		ArrayList<O> s = new ArrayList<O>();
		return new PrologTypedQuery<O>(s);
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
		return new DummyProcedureQuery(functor, args);
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	/**
	 * @deprecated Use containerOf(Class, Map), masterOf(Class) instead
	 */
	@Deprecated
	public final PersistentContainer containerOf(Class<?> clazz) {
		return masterOf(clazz);
	}

	public final PersistentContainer masterOf(Class<?> clazz) {
		PersistentContainer container = master.get(clazz);
		if (container == null) {
			String path = locationOf(clazz);
			if (storageMode == StorageMode.STORAGE_POOL) {
				String name = clazz.getSimpleName();
				container = containerFactory.createStoragePool(path, name);
			} else if (storageMode == StorageMode.SINGLE_STORAGE) {
				container = containerFactory.createStorage(path);
			}
			master.put(clazz, container);
		}
		return container;
	}

	public final PersistentContainer loggerOf(Class<?> clazz) {
		PersistentContainer container = logger.get(clazz);
		if (container == null) {
			String path = locationOf(clazz);
			if (storageMode == StorageMode.STORAGE_POOL) {
				String name = clazz.getSimpleName();
				container = containerFactory.createStoragePool(path, name + "-log");
			} else if (storageMode == StorageMode.SINGLE_STORAGE) {
				container = containerFactory.createStorage(path + "-log");
			}
			logger.put(clazz, container);
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
		List<Class<?>> l = JavaLists.arrayList(master.size());
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
			c.begin();
			c.clear();
			c.commit();
			c.close();
		}
		master.clear();
	}

	public final void close() {
		for (PersistentContainer c : master.values()) {
			c.begin();
			c.clear();
			c.commit();
			c.close();
		}
		master.clear();
		open = false;
	}

}
