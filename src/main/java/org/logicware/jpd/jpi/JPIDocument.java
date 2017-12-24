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
import org.logicware.jpd.DefaultTransaction;
import org.logicware.jpd.Document;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.Predicate;
import org.logicware.jpd.ProcedureQuery;
import org.logicware.jpd.Properties;
import org.logicware.jpd.Query;
import org.logicware.jpd.Transaction;
import org.logicware.jpd.TypedQuery;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public final class JPIDocument extends AbstractDocument implements Document {

	private final Transaction transaction;

	public JPIDocument(PrologProvider provider, String location) {
		this(provider, new Properties(), location);
	}

	public JPIDocument(PrologProvider provider, Properties properties, String location) {
		this(provider, properties, new JPIObjectConverter(provider), location);
	}

	public JPIDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, int maxCapacity) {
		super(provider, properties, converter, location, maxCapacity);
		this.transaction = new DefaultTransaction(this);
	}

	public JPIDocument(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location) {
		super(provider, properties, converter, location);
		this.transaction = new DefaultTransaction(this);
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public Query createQuery(String string) {
		open();
		return new JPIQuery(findAll(string));
	}

	public <O> TypedQuery<O> createQuery(O o) {
		open();
		return new JPITypedQuery<O>(findAll(o));
	}

	public <O> TypedQuery<O> createQuery(Class<O> clazz) {
		open();
		return new JPITypedQuery<O>(findAll(clazz));
	}

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate) {
		open();
		return new JPITypedQuery<O>(findAll(predicate));
	}

	public <O> ConstraintQuery<O> createConstraintQuery(Class<O> clazz) {
		return new JPIConstraintQuery<O>(getLocation(), getEngine(), getProvider(), clazz);
	}

	public ProcedureQuery createProcedureQuery(String functor, String... args) {
		return new JPIProcedureQuery(getLocation(), getProvider(), functor, args);
	}

}
