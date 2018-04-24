/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.db;

import org.logicware.ContainerFactory;
import org.logicware.DatabaseEngine;
import org.logicware.DatabaseMode;
import org.logicware.DatabaseType;
import org.logicware.DatabaseUser;
import org.logicware.MemoryDatabase;
import org.logicware.ObjectConverter;
import org.logicware.RelationalCache;
import org.logicware.Schema;
import org.logicware.Settings;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public final class MemoryRelationalDB extends AbstractDatabaseService implements MemoryDatabase {

	private static MemoryRelationalDB memoryRelationalDB;
	private final RelationalCache cache;

	private MemoryRelationalDB(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, String location, String name, Schema schema, DatabaseUser owner,
			DatabaseEngine engine) {
		super(provider, properties, converter, containerFactory, location, name, schema, owner, engine);
		this.cache = properties.createRelationalCache(schema);
	}

	public static final MemoryDatabase newInstance() {
		if (memoryRelationalDB == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return memoryRelationalDB;
	}

	public DatabaseMode getMode() {
		return DatabaseMode.MEMORY;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}
