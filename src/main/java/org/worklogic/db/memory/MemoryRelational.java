/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.db.memory;

import java.net.URL;

import org.worklogic.db.DatabaseType;
import org.worklogic.db.DatabaseUser;
import org.worklogic.db.MemoryDatabase;
import org.worklogic.db.Schema;
import org.worklogic.db.VolatileContainer;

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
