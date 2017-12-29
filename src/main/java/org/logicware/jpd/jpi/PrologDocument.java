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
package org.logicware.jpd.jpi;

import org.logicware.jpd.AbstractDocument;
import org.logicware.jpd.ConstraintQuery;
import org.logicware.jpd.ContainerFactory;
import org.logicware.jpd.DefaultTransaction;
import org.logicware.jpd.Document;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.Predicate;
import org.logicware.jpd.ProcedureQuery;
import org.logicware.jpd.Properties;
import org.logicware.jpd.Query;
import org.logicware.jpd.Transaction;
import org.logicware.jpd.TypedQuery;
import org.logicware.jpd.db.LockFile;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public final class PrologDocument extends AbstractDocument implements Document {

	private final Transaction transaction;

	public PrologDocument(PrologProvider provider, Properties properties, String location,
			ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologDocument(PrologProvider provider, Properties properties, String location,
			ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public PrologDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, LockFile lock, int maxCapacity) {
		super(provider, properties, converter, location, containerFactory, lock, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public Query createQuery(String string) {
		open();
		return new PrologQuery(findAll(string));
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
		return new PrologConstraintQuery<O>(getLocation(), getEngine(), getProvider(), clazz);
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return new PrologProcedureQuery(getLocation(), getProvider(), functor, args);
	}

}
