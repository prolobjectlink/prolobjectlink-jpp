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
package org.logicware.pdb.prolog;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.LockFile;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Storage;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.storage.AbstractStorage;

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
