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
package org.prolobjectlink.db.tx;

import org.prolobjectlink.db.AbstractTransaction;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.PersistentTransaction;

public class PersistentContainerTransaction extends AbstractTransaction implements PersistentTransaction {

	private final PersistentContainer container;

	public PersistentContainerTransaction(PersistentContainer container) {
		super(System.currentTimeMillis());
		this.container = container;
	}

	public final PersistentContainer getContainer() {
		return container;
	}

	public final void begin() {
		checkActiveTransaction();
		getContainer().open();
		active = true;
	}

	public final void commit() {
		checkNonActiveTransaction();
		getContainer().flush();
	}

	public final void rollback() {
		checkNonActiveTransaction();
		// roll back open the file
		// losing all memory data changes
		getContainer().open();
	}

}
