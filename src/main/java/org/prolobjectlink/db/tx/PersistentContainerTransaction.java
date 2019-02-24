/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
