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
package org.logicware.prolog;

import org.logicware.ContainerFactory;
import org.logicware.ObjectConverter;
import org.logicware.Predicate;
import org.logicware.ProcedureQuery;
import org.logicware.Query;
import org.logicware.Settings;
import org.logicware.Storage;
import org.logicware.StoragePool;
import org.logicware.TypedQuery;
import org.logicware.storage.AbstractStoragePool;

public class PrologStoragePool extends AbstractStoragePool implements StoragePool {

	// private final Transaction transaction;

	public PrologStoragePool(PrologProvider provider, Settings properties, String location, String name,
			ContainerFactory containerFactory) {
		this(provider, properties, new PrologObjectConverter(provider), location, name, containerFactory, 1000);
	}

	public PrologStoragePool(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, String name, ContainerFactory containerFactory) {
		this(provider, properties, converter, location, name, containerFactory, 1000);
	}

	public PrologStoragePool(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, String name, ContainerFactory containerFactory, int documentCapacity) {
		super(provider, properties, converter, location, name, containerFactory, documentCapacity);
		// this.transaction = new DefaultTransaction(this);
	}

	// public Transaction getTransaction() {
	// return transaction;
	// }

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

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return new PrologStoragePoolProcedureQuery(functor, args);
	}

	public Storage createStorage(String location, int maxCapacity) {
		return new PrologStorage(getProvider(), getProperties(), getConverter(), location, getContainerFactory(),
				maxCapacity);
	}

	private final class PrologStoragePoolProcedureQuery extends StoragePoolProcedureQuery {

		protected PrologStoragePoolProcedureQuery(String functor, String[] arguments) {
			super(functor, arguments);
		}

	}

}