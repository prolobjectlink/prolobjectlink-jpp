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
package org.prolobjectlink.db.memory;

import java.net.URL;

import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.MemoryDatabase;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.VolatileContainer;

public final class MemoryRelational extends AbstractMemoryDatabase implements MemoryDatabase {

	private static MemoryDatabase memoryRelationalDB;

	private MemoryRelational(String name, URL url, Schema schema, DatabaseUser owner, VolatileContainer storage) {
		super(storage.getProperties(), url, name, schema, owner, storage);
	}

	public static final MemoryDatabase newInstance(String name) {
		if (memoryRelationalDB == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return memoryRelationalDB;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}
