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
import org.logicware.Schema;
import org.logicware.DatabaseService;
import org.logicware.DatabaseUser;
import org.logicware.ObjectConverter;
import org.logicware.RemoteDatabase;
import org.logicware.Settings;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public final class RemoteHierarchicalDB extends AbstractDatabaseService implements RemoteDatabase {

	private static RemoteHierarchicalDB embeddedHierarchicalDB;

	private RemoteHierarchicalDB(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, String location, String name, Schema schema, DatabaseUser owner,
			DatabaseEngine engine) {
		super(provider, properties, converter, containerFactory, location, name, schema, owner, engine);
	}

	public static final RemoteDatabase instance() {
		if (embeddedHierarchicalDB == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return embeddedHierarchicalDB;
	}

	public DatabaseMode getMode() {
		return DatabaseMode.REMOTE;
	}

}
