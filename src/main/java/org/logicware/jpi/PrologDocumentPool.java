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
package org.logicware.jpi;

import java.util.List;

import org.logicware.AbstractDocumentPool;
import org.logicware.ConstraintQuery;
import org.logicware.ContainerFactory;
import org.logicware.DefaultTransaction;
import org.logicware.Document;
import org.logicware.DocumentPool;
import org.logicware.ObjectConverter;
import org.logicware.Predicate;
import org.logicware.ProcedureQuery;
import org.logicware.Properties;
import org.logicware.Query;
import org.logicware.Transaction;
import org.logicware.TypedQuery;
import org.logicware.util.ReadWriteCollections;

public class PrologDocumentPool extends AbstractDocumentPool implements DocumentPool {

	private final Transaction transaction;

	public PrologDocumentPool(PrologProvider provider, String location, ContainerFactory containerFactory) {
		this(provider, new Properties(), new PrologObjectConverter(provider), location, containerFactory, 10000);
	}

	public PrologDocumentPool(PrologProvider provider, Properties properties, String location,
			ContainerFactory containerFactory) {
		this(provider, properties, new PrologObjectConverter(provider), location, containerFactory, 10000);
	}

	public PrologDocumentPool(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory) {
		this(provider, properties, converter, location, containerFactory, 1000);
	}

	public PrologDocumentPool(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, int documentCapacity) {
		super(provider, properties, converter, location, containerFactory, documentCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public Query createQuery(String string) {
		open();
		return new PrologContainerQuery(findAll(string));
	}

	public <O> TypedQuery<O> createQuery(O o) {
		open();
		return new PrologTypedQuery<O>(findAll(o));
	}

	public <O> TypedQuery<O> createQuery(Class<O> clazz) {
		open();
		return new PrologTypedQuery<O>(findAll(clazz));
	}

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		open();
		return new PrologTypedQuery<O>(findAll(predicate));
	}

	public <O> ConstraintQuery<O> createConstraintQuery(Class<O> clazz) {
		return new JPIDocumetPoolConstraintQuery<O>(clazz);
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return new JPIDocumentPoolProcedureQuery(functor, args);
	}

	public Document createDocument(String location, int maxCapacity) {
		return new PrologDocument(getProvider(), getProperties(), getConverter(), location, getContainerFactory(),
				maxCapacity);
	}

	private final class JPIDocumetPoolConstraintQuery<O> extends DocumetPoolConstraintQuery<O>
			implements ConstraintQuery<O> {

		protected JPIDocumetPoolConstraintQuery(Class<O> clazz) {
			super(clazz);
		}

		public TypedQuery<O> createQuery() {
			List<O> list = ReadWriteCollections.newReadWriteArrayList();
			for (ConstraintQuery<O> constraintQuery : getConstraints()) {
				List<O> objects = constraintQuery.getSolutions();
				list.addAll(objects);
			}
			TypedQuery<O> query = new PrologTypedQuery<O>(list);
			query.setFirstSolution(getFirstSolution());
			query.setMaxSolution(getMaxSolution());
			return query;
		}

	}

	private final class JPIDocumentPoolProcedureQuery extends DocumentPoolProcedureQuery {

		protected JPIDocumentPoolProcedureQuery(String functor, String[] arguments) {
			super(functor, arguments);
		}

	}

}
