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

public class DefaultTransaction extends AbstractTransaction implements Transaction {

	private void checkNonActiveTransaction() {
		if (!isActive()) {
			throw new IllegalStateException("Entity Transaction is not active");
		}
	}

	private void checkActiveTransaction() {
		if (isActive()) {
			throw new IllegalStateException("Entity Transaction is active");
		}
	}

	public DefaultTransaction(Transactional transactional) {
		super(transactional, System.currentTimeMillis());
	}

	public final void begin() {
		checkActiveTransaction();
		getTransactional().begin();
		active = true;
	}

	public final void commit() {
		checkNonActiveTransaction();
		getTransactional().commit();
	}

	public final void rollback() {
		checkNonActiveTransaction();
		getTransactional().rollback();
	}

	public final boolean isActive() {
		return active;
	}

	public final void close() {
		active = false;
	}

}
