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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;
import org.logicware.util.ReadWriteCollections;

public abstract class AbstractStoragePool extends AbstractPersistentContainer implements StoragePool {

	// pool name
	private final String name;

	// disposed document
	private Storage lastDocument;

	// capacity per document to store clauses
	private final int documentCapacity;

	//
	private final File rootDirectory;

	// list of documents in the pool
	private List<Storage> storages = new ArrayList<Storage>();

	// file filter for pool files
	private final FileFilter filter = new StoragePoolFileFilter();

	protected AbstractStoragePool(PrologProvider provider, Properties properties,
			ObjectConverter<PrologTerm> converter, String location, String name, ContainerFactory containerFactory,
			int documentCapacity) {
		super(provider, properties, converter, location, containerFactory);
		this.documentCapacity = documentCapacity;
		rootDirectory = new File(location);
		rootDirectory.mkdir();
		this.name = name;
		open = false;
	}

	public final <O> void add(O... facts) {
		int index = getPoolSize();
		String root = getLocation();
		String path = root + SEPARATOR + root + "." + index;
		if (lastDocument == null) {
			lastDocument = createStorage(path, documentCapacity);
			storages.add(lastDocument);
		}
		if (!lastDocument.hasCapacity()) {
			lastDocument = createStorage(path, documentCapacity);
			storages.add(lastDocument);
		} else {
			lastDocument.add(facts);
		}
	}

	public final <O> void modify(O match, O merge) {
		for (Storage document : storages) {
			document.modify(match, merge);
		}
	}

	public final void remove(Class<?> clazz) {
		for (Storage document : storages) {
			document.remove(clazz);
		}
	}

	public final <O> void remove(O... facts) {
		for (Storage document : storages) {
			document.remove(facts);
		}
	}

	public final Object find(String string) throws NonSolutionError {
		for (Storage document : storages) {
			Object object = document.find(string);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final Object find(String functor, Object... args) throws NonSolutionError {
		for (Storage document : storages) {
			Object object = document.find(functor, args);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(O o) throws NonSolutionError {
		for (Storage document : storages) {
			O object = document.find(o);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Class<O> clazz) throws NonSolutionError {
		for (Storage document : storages) {
			O object = document.find(clazz);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Predicate<O> predicate) throws NonSolutionError {
		for (Storage document : storages) {
			O object = document.find(predicate);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final List<Object> findAll(String string) {
		List<Object> list = ReadWriteCollections.newReadWriteArrayList();
		for (Storage document : storages) {
			List<Object> objects = document.findAll(string);
			for (Object object : objects) {
				if (!list.contains(object)) {
					list.add(object);
				}
			}
		}
		return list;
	}

	public final List<Object> findAll(String functor, Object... args) {
		List<Object> list = ReadWriteCollections.newReadWriteArrayList();
		for (Storage document : storages) {
			List<Object> objects = document.findAll(functor, args);
			for (Object object : objects) {
				if (!list.contains(object)) {
					list.add(object);
				}
			}
		}
		return list;
	}

	public final <O> List<O> findAll(O o) {
		List<O> list = ReadWriteCollections.newReadWriteArrayList();
		for (Storage document : storages) {
			List<O> objects = document.findAll(o);
			for (O object : objects) {
				if (!list.contains(object)) {
					list.add(object);
				}
			}
		}
		return list;
	}

	public final <O> List<O> findAll(Class<O> clazz) {
		List<O> list = ReadWriteCollections.newReadWriteArrayList();
		for (Storage document : storages) {
			List<O> objects = document.findAll(clazz);
			for (O object : objects) {
				if (!list.contains(object)) {
					list.add(object);
				}
			}
		}
		return list;
	}

	public final <O> List<O> findAll(Predicate<O> predicate) {
		List<O> list = ReadWriteCollections.newReadWriteArrayList();
		for (Storage document : storages) {
			List<O> objects = document.findAll(predicate);
			for (O object : objects) {
				if (!list.contains(object)) {
					list.add(object);
				}
			}
		}
		return list;
	}

	public final boolean contains(String string) {
		for (Storage document : storages) {
			if (document.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(O object) {
		for (Storage document : storages) {
			if (document.contains(object)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Class<O> clazz) {
		for (Storage document : storages) {
			if (document.contains(clazz)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		for (Storage document : storages) {
			if (document.contains(predicate)) {
				return true;
			}
		}
		return false;
	}

	public final void clear() {
		for (Storage document : storages) {
			document.clear();
		}
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

	public final String locationOf(Class<?> clazz) {
		return getLocation();
	}

	public final PersistentContainer containerOf(Class<?> clazz) {
		return this;
	}

	public final void close() {
		for (Storage document : storages) {
			document.clear();
			document.close();
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
						lastDocument = createStorage(canonical, documentCapacity);
						storages.add(lastDocument);
						lastDocument.open();
					}
				} catch (IOException e) {
					LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR + filex, e);
				}
			}
		}
		open = true;
	}

	public final List<Storage> getStorages() {
		return storages;
	}

	public final String getPoolName() {
		return name;
	}

	public final int getCapacity() {
		return documentCapacity;
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
		for (Storage document : storages) {
			if (document.isDirty()) {
				document.flush();
			}
		}
	}

	/**
	 * ConstraintQuery implementation for document pool
	 * 
	 * @author Jose Zalacain
	 * @since 1.0
	 * @param <O>
	 *            parametric object
	 */
	protected abstract class StoragePoolConstraintQuery<O> extends AbstractQuery<O> implements ConstraintQuery<O> {

		//
		private final Class<O> rootClass;

		//
		private final List<ConstraintQuery<O>> constraints = new LinkedList<ConstraintQuery<O>>();

		protected StoragePoolConstraintQuery(Class<O> clazz) {
			this.rootClass = clazz;
			for (Storage document : storages) {
				constraints.add(document.createConstraintQuery(clazz));
			}
		}

		public ConstraintQuery<O> setMaxSolution(int maxSolution) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.setMaxSolution(maxSolution);
			}
			return this;
		}

		public ConstraintQuery<O> setFirstSolution(int firstSolution) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.setFirstSolution(firstSolution);
			}
			return this;
		}

		public ConstraintQuery<O> not(Class<?> clazz) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.not(clazz);
			}
			return this;
		}

		public ConstraintQuery<O> and(Class<?> clazz) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.and(clazz);
			}
			return this;
		}

		public ConstraintQuery<O> or(Class<?> clazz) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.or(clazz);
			}
			return this;
		}

		public ConstraintQuery<O> unify(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.unify(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> notUnify(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notUnify(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> equivalent(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.equivalent(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> notEquivalent(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notEquivalent(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> before(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.before(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> after(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.after(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> beforeEquals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.beforeEquals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> afterEquals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.afterEquals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> equals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.equals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> notEquals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notEquals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> greater(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.greater(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> less(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.less(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> greaterEquals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.greaterEquals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> lessEquals(String field, Object value) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.lessEquals(field, value);
			}
			return this;
		}

		public ConstraintQuery<O> unifyField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.unifyField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> notUnifyField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notUnifyField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> equivalentField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.equivalentField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> notEquivalentField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notEquivalentField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> beforeField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.beforeField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> afterField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.afterField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> beforeEqualsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.beforeEqualsField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> afterEqualsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.afterEqualsField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> equalsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.equalsField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> notEqualsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.notEqualsField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> lessField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.lessField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> greaterField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.greaterField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> greaterEqualsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.greaterEqualsField(left, right);
			}
			return this;
		}

		public ConstraintQuery<O> lessEqualsField(String left, String right) {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.lessEqualsField(left, right);
			}
			return this;
		}

		public O getSolution() throws NonSolutionError {
			return createQuery().getSolution();
		}

		public List<O> getSolutions() {
			return createQuery().getSolutions();
		}

		public ConstraintQuery<O> trace() {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.trace();
			}
			return this;
		}

		public void dispose() {
			for (ConstraintQuery<O> constraintQuery : constraints) {
				constraintQuery.dispose();
			}
			constraints.clear();
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + Objects.hashCode(constraints);
			result = prime * result + Objects.hashCode(rootClass);
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
			StoragePoolConstraintQuery<O> other = (StoragePoolConstraintQuery) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (!Objects.equals(constraints, other.constraints))
				return false;
			return Objects.equals(rootClass, other.rootClass);
		}

		protected final List<Storage> getDocuments() {
			return storages;
		}

		protected final List<ConstraintQuery<O>> getConstraints() {
			return constraints;
		}

		protected final Class<O> getRootClass() {
			return rootClass;
		}

		private AbstractStoragePool getOuterType() {
			return AbstractStoragePool.this;
		}

	}

	protected class StoragePoolProcedureQuery extends AbstractProcedureQuery<Object> implements ProcedureQuery {

		private List<Object> list = ReadWriteCollections.newReadWriteArrayList();
		private final List<ProcedureQuery> queries = new ArrayList<ProcedureQuery>();

		protected StoragePoolProcedureQuery(String functor, String[] arguments) {
			super(functor, arguments);
			AbstractStoragePool.this.open();
			for (Storage document : storages) {
				queries.add(document.createProcedureQuery(functor, arguments));
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
						Object[] array = (Object[]) object;
						list = ReadWriteCollections.newReadWriteArrayList(array);
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
