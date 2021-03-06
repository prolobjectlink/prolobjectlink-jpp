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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.PersistentContainer;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.ProcedureQuery;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.StoragePool;
import io.github.prolobjectlink.db.Transaction;
import io.github.prolobjectlink.db.container.AbstractPersistentContainer;
import io.github.prolobjectlink.db.container.AbstractProcedureQuery;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.tx.PersistentContainerTransaction;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractStoragePool extends AbstractPersistentContainer implements StoragePool {

	// pool name
	private final String name;

	// disposed storage
	private Storage lastStorage;

	// capacity per storage to store clauses
	private final int storageCapacity;

	//
	private final File rootDirectory;

	// elimination counter for defrag
	private volatile int counter = 0;

	// transaction
	private final Transaction transaction;

	// list of storages in the pool
	private List<Storage> storages = new ArrayList<Storage>();

	// file filter for pool files
	private final FileFilter filter = new StoragePoolFileFilter();

	protected AbstractStoragePool(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, String name, ContainerFactory containerFactory, int storageCapacity) {
		super(provider, properties, converter, location + SEPARATOR + name, containerFactory);
		this.rootDirectory = new File(location + SEPARATOR + name);
		this.transaction = new PersistentContainerTransaction(this);
		this.storageCapacity = storageCapacity;
		this.rootDirectory.mkdir();
		this.open = false;
		this.name = name;
	}

	public final Object find(String string) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			Object object = storage.find(string);
			if (object != null) {
				return object;
			}
		}
		return null;
	}

	public final Object find(String functor, Object... args) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			Object object = storage.find(functor, args);
			if (object != null) {
				return object;
			}
		}
		return null;
	}

	public final <O> O find(O o) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(o);
			if (object != null) {
				return object;
			}
		}
		return null;
	}

	public final <O> O find(Class<O> clazz) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(clazz);
			if (object != null) {
				return object;
			}
		}
		return null;
	}

	public final <O> O find(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(predicate);
			if (object != null) {
				return object;
			}
		}
		return null;
	}

	public final List<Object> findAll(String string) {
		checkActiveTransaction(transaction);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < getStorages().size(); i++) {
			Storage storage = getStorages().get(i);
			list.addAll(storage.findAll(string));
		}
		return list;
	}

	public final List<Object> findAll(String functor, Object... args) {
		checkActiveTransaction(transaction);
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < getStorages().size(); i++) {
			Storage storage = getStorages().get(i);
			list.addAll(storage.findAll(functor, args));
		}
		return list;
	}

	public final <O> List<O> findAll(O o) {
		checkActiveTransaction(transaction);
		List<O> list = new ArrayList<O>();
		for (int i = 0; i < getStorages().size(); i++) {
			Storage storage = getStorages().get(i);
			list.addAll(storage.findAll(o));
		}
		return list;
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		checkActiveTransaction(transaction);
		List<O> list = new ArrayList<O>();
		for (int i = 0; i < getStorages().size(); i++) {
			Storage storage = getStorages().get(i);
			list.addAll(storage.findAll(clazz));
		}
		return list;
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		List<O> list = new ArrayList<O>();
		for (int i = 0; i < getStorages().size(); i++) {
			Storage storage = getStorages().get(i);
			list.addAll(storage.findAll(predicate));
		}
		return list;
	}

	public final boolean contains(String string) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			if (storage.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(O object) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			if (storage.contains(object)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Class<O> clazz) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			if (storage.contains(clazz)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			if (storage.contains(predicate)) {
				return true;
			}
		}
		return false;
	}

	public final boolean contains(String functor, int arity) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			if (storage.contains(functor, arity)) {
				return true;
			}
		}
		return false;
	}

	public final void defragment() {
		// TODO Auto-generated method stub
	}

	public final void clear() {
		checkActiveTransaction(transaction);
		for (Storage s : storages) {
			s.clear();
		}
	}

	public final <O> void insert(O... facts) {
		checkActiveTransaction(transaction);
		int index = getPoolSize();
		String root = getLocation();
		String path = root + SEPARATOR + name + "." + index;
		if (lastStorage == null) {
			lastStorage = createStorage(path, storageCapacity);
			storages.add(lastStorage);
		}
		if (!lastStorage.hasCapacity()) {
			lastStorage = createStorage(path, storageCapacity);
			storages.add(lastStorage);
		} else {
			lastStorage.insert(facts);
		}
	}

	public final <O> void update(O match, O merge) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			storage.update(match, merge);
		}
	}

	public final <O> void delete(O... facts) {
		for (Storage storage : storages) {
			storage.delete(facts);
		}
		counter += facts.length;
		if (counter >= getCapacity()) {
			defragment();
			counter = 0;
		}
	}

	public final void delete(Class<?> clazz) {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			storage.delete(clazz);
		}
		defragment();
		counter = 0;
	}

	public final void include(String path) {
		for (Storage storage : storages) {
			storage.include(path);
		}
	}

	public final String locationOf(Class<?> clazz) {
		return getLocation();
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public final Collection<Class<?>> classes() {
		checkActiveTransaction(transaction);
		List<Class<?>> c = new ArrayList<Class<?>>();
		for (Storage storage : storages) {
			c.addAll(storage.classes());
		}
		return c;
	}

	public final void close() {
		getTransaction().close();
		for (Storage s : storages) {
			s.clear();
			s.close();
		}
		storages.clear();
		open = false;
	}

	public final void open() {
		File[] files = rootDirectory.listFiles(filter);
		if (files != null) {
			for (File filex : files) {
				try {
					String canonical = filex.getCanonicalPath();
					int lastDotIndex = canonical.lastIndexOf('.');
					String index = canonical.substring(lastDotIndex + 1);
					storages = new ArrayList<Storage>(files.length);
					if (index.matches(StoragePoolFileFilter.NUMBER_REGEX)) {
						lastStorage = createStorage(canonical, storageCapacity);
						lastStorage.begin();
						storages.add(lastStorage);
					}
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO, e);
				}
			}
		}
		open = true;
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final List<Storage> getStorages() {
		return storages;
	}

	public final String getPoolName() {
		return name;
	}

	public final int getCapacity() {
		return storageCapacity;
	}

	public final int getPoolSize() {
		return storages.size();
	}

	public final boolean isEmpty() {
		return storages.isEmpty();
	}

	public final FileFilter getFilter() {
		return filter;
	}

	public final void flush() {
		for (Storage storage : storages) {
			if (storage.isDirty()) {
				if (!storage.isActive()) {
					storage.begin();
				}
				storage.commit();
			}
		}
	}

	protected class StoragePoolProcedureQuery extends AbstractProcedureQuery implements ProcedureQuery {

		private ArrayList<Object> list = new ArrayList<Object>();
		private static final long serialVersionUID = -6828890841047794331L;
		private final ArrayList<ProcedureQuery> queries = new ArrayList<ProcedureQuery>();

		protected StoragePoolProcedureQuery(String functor, String[] arguments) {
			super(functor, arguments);
			AbstractStoragePool.this.open();
			for (Storage storage : storages) {
				queries.add(storage.createProcedureQuery(functor, arguments));
			}
		}

		public ProcedureQuery setMaxSolution(int maxSolution) {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.setMaxSolution(maxSolution);
			}
			return this;
		}

		public ProcedureQuery setFirstSolution(int firstSolution) {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.setFirstSolution(maxSolution);
			}
			return this;
		}

		public Object getArgumentValue(int position) {
			checkSolutionAt(position, list.size());
			return list.get(position);
		}

		public ProcedureQuery setArgumentValue(int position, Object value) {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.setArgumentValue(position, value);
			}
			return this;
		}

		public ProcedureQuery execute() {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.execute();
			}
			return this;
		}

		public List<Object> getSolutions() {
			return list;
		}

		public Object getSolution() {
			return next();
		}

		public void dispose() {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.dispose();
			}
		}

		protected final List<ProcedureQuery> getQueries() {
			return queries;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Objects.hashCode(queries);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			StoragePoolProcedureQuery other = (StoragePoolProcedureQuery) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			return Objects.equals(queries, other.queries);
		}

		private AbstractStoragePool getOuterType() {
			return AbstractStoragePool.this;
		}

		public boolean hasNext() {
			for (ProcedureQuery procedureQuery : queries) {
				if (procedureQuery.hasNext()) {
					return true;
				}
			}
			return false;
		}

		public Object next() {
			for (ProcedureQuery procedureQuery : queries) {
				if (procedureQuery.hasNext()) {
					Object object = procedureQuery.next();
					if (object instanceof Object[]) {
						Object[] a = (Object[]) object;
						list = new ArrayList<Object>(a.length);
						for (int i = 0; i < a.length; i++) {
							list.add(a[i]);
						}
					}
				}
			}
			return list.toArray();
		}

		@Override
		public void remove() {
			// skip the current solution
			next();
		}

	}

}
