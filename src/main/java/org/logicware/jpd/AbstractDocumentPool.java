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
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.logicware.jpd.util.ReadWriteCollections;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractDocumentPool extends AbstractDocumentContainer implements DocumentPool {

	// disposed document
	private Document lastDocument;

	// capacity per document to store clauses
	private final int documentCapacity;

	//
	private final File rootDirectory;

	// list of documents in the pool
	private List<Document> documents = new ArrayList<Document>();

	// file filter for pool files
	private final FileFilter filter = new DocumentPoolFileFilter();

	protected AbstractDocumentPool(PrologProvider provider, Properties properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory,
			int documentCapacity) {
		super(provider, properties, converter, location, containerFactory);
		this.documentCapacity = documentCapacity;
		rootDirectory = new File(location);
		rootDirectory.mkdir();
		open = false;
	}

	public final <O> void add(O... facts) {
		int index = getPoolSize();
		String root = getLocation();
		String path = root + SEPARATOR + root + "." + index;
		if (lastDocument == null) {
			lastDocument = createDocument(path, documentCapacity);
			documents.add(lastDocument);
		}
		if (!lastDocument.hasCapacity()) {
			lastDocument = createDocument(path, documentCapacity);
			documents.add(lastDocument);
		} else {
			lastDocument.add(facts);
		}
	}

	public final <O> void modify(O match, O merge) {
		for (Document document : documents) {
			document.modify(match, merge);
		}
	}

	public final void remove(Class<?> clazz) {
		for (Document document : documents) {
			document.remove(clazz);
		}
	}

	public final <O> void remove(O... facts) {
		for (Document document : documents) {
			document.remove(facts);
		}
	}

	public final Object find(String string) throws NonSolutionError {
		for (Document document : documents) {
			Object object = document.find(string);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final Object find(String functor, Object... args) throws NonSolutionError {
		for (Document document : documents) {
			Object object = document.find(functor, args);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(O o) throws NonSolutionError {
		for (Document document : documents) {
			O object = document.find(o);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Class<O> clazz) throws NonSolutionError {
		for (Document document : documents) {
			O object = document.find(clazz);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final <O> O find(Predicate<O> predicate) throws NonSolutionError {
		for (Document document : documents) {
			O object = document.find(predicate);
			if (object != null) {
				return object;
			}
		}
		throw new NonSolutionError();
	}

	public final List<Object> findAll(String string) {
		List<Object> list = ReadWriteCollections.newReadWriteArrayList();
		for (Document document : documents) {
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
		for (Document document : documents) {
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
		for (Document document : documents) {
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
		for (Document document : documents) {
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
		for (Document document : documents) {
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
		for (Document document : documents) {
			if (document.contains(string)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(O object) {
		for (Document document : documents) {
			if (document.contains(object)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Class<O> clazz) {
		for (Document document : documents) {
			if (document.contains(clazz)) {
				return true;
			}
		}
		return false;
	}

	public final <O> boolean contains(Predicate<O> predicate) {
		for (Document document : documents) {
			if (document.contains(predicate)) {
				return true;
			}
		}
		return false;
	}

	public final void clear() {
		for (Document document : documents) {
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
		for (Document document : documents) {
			document.clear();
			document.close();
		}
		documents.clear();
		open = false;
	}

	public final void open() {
		File[] files = rootDirectory.listFiles(filter);
		if (files != null) {
			for (File filex : files) {
				try {
					String name = filex.getCanonicalPath();
					int lastDotIndex = name.lastIndexOf('.');
					String index = name.substring(lastDotIndex + 1);
					documents = new ArrayList<Document>(files.length);
					if (index.matches(DocumentPoolFileFilter.NUMBER_REGEX)) {
						lastDocument = createDocument(name, documentCapacity);
						// int i = Integer.parseInt(index);
						// documents.set(i, lastDocument);
						documents.add(lastDocument);
						lastDocument.open();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		open = true;
	}

	public final List<Document> getDocuments() {
		return documents;
	}

	public final int getCapacity() {
		return documentCapacity;
	}

	public final int getPoolSize() {
		return documents.size();
	}

	public final boolean isEmpty() {
		return documents.isEmpty();
	}

	public final FileFilter getFilter() {
		return filter;
	}

	public final void flush() {
		for (Document document : documents) {
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
	protected abstract class DocumetPoolConstraintQuery<O> extends AbstractQuery<O> implements ConstraintQuery<O> {

		//
		private final Class<O> rootClass;

		//
		private final List<ConstraintQuery<O>> constraints = new LinkedList<ConstraintQuery<O>>();

		protected DocumetPoolConstraintQuery(Class<O> clazz) {
			this.rootClass = clazz;
			for (Document document : documents) {
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
			result = prime * result + ((rootClass == null) ? 0 : rootClass.hashCode());
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
			DocumetPoolConstraintQuery other = (DocumetPoolConstraintQuery) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			else if (!constraints.equals(other.constraints))
				return false;
			if (rootClass == null) {
				if (other.rootClass != null)
					return false;
			} else if (!rootClass.equals(other.rootClass))
				return false;
			return true;
		}

		protected final List<Document> getDocuments() {
			return documents;
		}

		protected final List<ConstraintQuery<O>> getConstraints() {
			return constraints;
		}

		protected final Class<O> getRootClass() {
			return rootClass;
		}

		private AbstractDocumentPool getOuterType() {
			return AbstractDocumentPool.this;
		}

	}

	protected class DocumentPoolProcedureQuery extends AbstractProcedureQuery<Object> implements ProcedureQuery {

		private List<Object> list = ReadWriteCollections.newReadWriteArrayList();
		private final List<ProcedureQuery> queries = new ArrayList<ProcedureQuery>();

		protected DocumentPoolProcedureQuery(String functor, String[] arguments) {
			super(functor, arguments);
			AbstractDocumentPool.this.open();
			for (Document document : documents) {
				queries.add(document.createProcedureQuery(functor, arguments));
			}
		}

		public boolean hasMoreElements() {
			for (ProcedureQuery procedureQuery : queries) {
				if (procedureQuery.hasMoreElements()) {
					return true;
				}
			}
			return false;
		}

		public Object nextElement() {
			for (ProcedureQuery procedureQuery : queries) {
				if (procedureQuery.hasMoreElements()) {
					Object object = procedureQuery.nextElement();
					if (object instanceof Object[]) {
						Object[] array = (Object[]) object;
						list = ReadWriteCollections.newReadWriteArrayList(array);
					}
				}
			}
			return list.toArray();
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
			return nextElement();
		}

		public void dispose() {
			for (ProcedureQuery procedureQuery : queries) {
				procedureQuery.dispose();
			}
		}

		protected final List<ProcedureQuery> getQueries() {
			return queries;
		}

	}

}
