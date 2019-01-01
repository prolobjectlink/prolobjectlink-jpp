/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.database.prolog;

import org.logicware.database.ContainerFactory;
import org.logicware.database.DefaultTransaction;
import org.logicware.database.LockFile;
import org.logicware.database.ObjectConverter;
import org.logicware.database.Predicate;
import org.logicware.database.ProcedureQuery;
import org.logicware.database.Query;
import org.logicware.database.Storage;
import org.logicware.database.Transaction;
import org.logicware.database.TypedQuery;
import org.logicware.database.etc.Settings;
import org.logicware.database.storage.AbstractStorage;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public final class PrologStorage extends AbstractStorage implements Storage {

	private final Transaction transaction;

	public PrologStorage(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, LockFile lock, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, lock, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public Query createQuery(String string) {
		return new PrologContainerQuery(findAll(string));
	}

	public <O> TypedQuery<O> createQuery(O o) {
		return new PrologTypedQuery<O>(findAll(o));
	}

	public <O> TypedQuery<O> createQuery(Class<O> clazz) {
		return new PrologTypedQuery<O>(findAll(clazz));
	}

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		return new PrologTypedQuery<O>(findAll(predicate));
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return new PrologProcedureQuery(getLocation(), getProvider(), functor, args);
	}

}
