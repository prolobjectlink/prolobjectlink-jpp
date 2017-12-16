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
package org.logicware.jpd;

public class DefaultTransaction extends AbstractTransaction implements Transaction {

    private boolean active;

    public DefaultTransaction(PersistentContainer persistentContainer) {
	super(System.currentTimeMillis(), persistentContainer);
    }

    public final void begin() {
	checkActiveTransaction();
	if (getPersistentContainer().isWrappedBy(PersistentDocument.class)) {
	    getPersistentContainer().unwrap(PersistentDocument.class).open();
	}
	active = true;
    }

    public final void commit() {
	checkNonActiveTransaction();
	if (getPersistentContainer().isWrappedBy(PersistentDocument.class)) {
	    getPersistentContainer().unwrap(PersistentDocument.class).flush();
	}
	active = false;
    }

    public final void rollback() {
	checkNonActiveTransaction();
	if (getPersistentContainer().isWrappedBy(PersistentDocument.class)) {
	    // roll back open from the file losing
	    // all memory data changes
	    getPersistentContainer().unwrap(PersistentDocument.class).open();
	}
	active = false;
    }

    public final boolean isActive() {
	return active;
    }

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

}
