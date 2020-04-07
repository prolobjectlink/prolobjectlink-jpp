/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.prolog;

import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.Predicate;
import io.github.prolobjectlink.db.ProcedureQuery;
import io.github.prolobjectlink.db.Query;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.StoragePool;
import io.github.prolobjectlink.db.TypedQuery;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.storage.AbstractStoragePool;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

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
