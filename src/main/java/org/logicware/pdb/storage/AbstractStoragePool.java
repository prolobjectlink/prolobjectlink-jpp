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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.NonSolutionError;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureArgumentError;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Storage;
import org.logicware.pdb.StoragePool;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.common.AbstractPersistentContainer;
import org.logicware.pdb.common.AbstractProcedureQuery;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

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
		this.transaction = new DefaultTransaction(this);
		this.storageCapacity = storageCapacity;
		this.rootDirectory.mkdir();
		this.open = false;
		this.name = name;
	}

	public final Object find(String string) throws NonSolutionError {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			Object object = storage.find(string);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final Object find(String functor, Object... args) throws NonSolutionError {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			Object object = storage.find(functor, args);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(O o) throws NonSolutionError {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(o);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Class<O> clazz) throws NonSolutionError {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(clazz);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Predicate<O> predicate) throws NonSolutionError {
		checkActiveTransaction(transaction);
		for (Storage storage : storages) {
			O object = storage.find(predicate);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
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
		// TODO
		// String c = File.separator;
		// backup(getLocation(), name);
		// String path = getLocation() + c + name;
		// try {
		// Files.delete(rootDirectory.toPath());
		// List<Class<?>> classList = classes();
		// for (Class<?> clazz : classList) {
		// insert(findAll(clazz).toArray(new Object[0]));
		// }
		// Files.delete(new File(path).toPath());
		// } catch (Exception e) {
		// String s = LoggerConstants.IO_ERROR;
		// LoggerUtils.error(getClass(), s, e);
		// restore(getLocation(), name);
		// }
	}

	public final void clear() {
		checkActiveTransaction(transaction);
		for (Storage s : storages) {
			s.clear();
		}
		storages.clear();
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

	// FIXME pool add method
	// public final <O> void insert(O... objects) {
	//
	// String root = getLocation();
	//
	// int length = objects.length;
	// if (lastStorage != null) {
	// int size = lastStorage.getSize();
	// int newLength = size + length;
	// if (newLength < storageCapacity) {
	// lastStorage.insert(objects);
	// } else if (lastStorage.hasCapacity()) {
	// int free = storageCapacity - lastStorage.getSize();
	// O[] array2 = Arrays.copyOfRange(objects, free, length);
	// O[] array1 = Arrays.copyOf(objects, free);
	// lastStorage.insert(array1);
	// insert(array2);
	// } else {
	// int index = getPoolSize();
	// String path = root + SEPARATOR + name + "." + (index + 1);
	// lastStorage = createStorage(path, storageCapacity);
	// storages.add(lastStorage);
	// insert(objects);
	// }
	// } else {
	// int index = getPoolSize();
	// String path = root + SEPARATOR + name + "." + index;
	// lastStorage = createStorage(path, storageCapacity);
	// storages.add(lastStorage);
	// insert(objects);
	// }
	//
	// }

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
						lastStorage.open();
						storages.add(lastStorage);
					}
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
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
				storage.flush();
			}
		}
	}

	protected class StoragePoolProcedureQuery extends AbstractProcedureQuery<Object> implements ProcedureQuery {

		private List<Object> list = new ArrayList<Object>();
		private final List<ProcedureQuery> queries = new ArrayList<ProcedureQuery>();

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

		public Object getArgumentValue(String name) {
			for (int i = 0; i < getArguments().length; i++) {
				String argumentName = getArguments()[i];
				if (argumentName.equals(name)) {
					return getArgumentValue(i);
				}
			}
			throw new ProcedureArgumentError(getFunctor(), name);
		}

		public ProcedureQuery setArgumentValue(int position, Object value) {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.setArgumentValue(position, value);
			}
			return this;
		}

		public ProcedureQuery setArgumentValue(String name, Object value) {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.setArgumentValue(name, value);
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

		public Object getSolution() throws NonSolutionError {
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

		public void remove() {
			// skip the current solution
			next();
		}

	}

}
