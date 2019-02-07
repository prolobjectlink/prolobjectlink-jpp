/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.prolog;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.LockFile;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Query;
import org.prolobjectlink.db.Storage;
import org.prolobjectlink.db.Transaction;
import org.prolobjectlink.db.TypedQuery;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.storage.AbstractStorage;
import org.prolobjectlink.db.tx.PersistentContainerTransaction;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

public final class PrologStorage extends AbstractStorage implements Storage {

	private final Transaction transaction;

	public PrologStorage(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory);
		this.transaction = new PersistentContainerTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory, maxCapacity);
		this.transaction = new PersistentContainerTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, maxCapacity);
		this.transaction = new PersistentContainerTransaction(this);
	}

	public PrologStorage(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, LockFile lock, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, lock, maxCapacity);
		this.transaction = new PersistentContainerTransaction(this);
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
