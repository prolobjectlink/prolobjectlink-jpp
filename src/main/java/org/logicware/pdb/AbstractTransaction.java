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
package org.logicware.pdb;

import java.util.Date;

public abstract class AbstractTransaction extends AbstractWrapper implements Transaction {

	protected boolean active;
	private final long timestamp;
	private final Transactional transactional;

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

	public AbstractTransaction(Transactional transactional, long timestamp) {
		this.transactional = transactional;
		this.timestamp = timestamp;
	}

	public final Transactional getTransactional() {
		return transactional;
	}

	public final boolean before(Transaction t) {
		return compareTo(t) < 0;
	}

	public final boolean after(Transaction t) {
		return compareTo(t) > 0;
	}

	public final int compareTo(Transaction o) {
		Date thisDate = new Date(timestamp);
		Date otherDate = new Date(o.getTimestamp());
		return thisDate.compareTo(otherDate);
	}

	public final long getTimestamp() {
		return timestamp;
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
