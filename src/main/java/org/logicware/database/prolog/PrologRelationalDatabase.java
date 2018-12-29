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
package org.logicware.database.prolog;

import java.io.File;

import org.logicware.database.DatabaseSchema;
import org.logicware.database.DatabaseUser;
import org.logicware.database.RelationalDatabase;
import org.logicware.database.Settings;
import org.logicware.database.StorageMode;
import org.logicware.database.engine.AbstractRelationalDatabase;

/**
 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
 *             RemoteRelationalHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public final class PrologRelationalDatabase extends AbstractRelationalDatabase implements RelationalDatabase {

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public PrologRelationalDatabase(Settings properties, StorageMode storageMode, String name, DatabaseUser user) {
		super(name, new DatabaseSchema(LOCATION + File.separator + name, properties.getProvider(), properties, user),
				user,
				new PrologStorageGraph(LOCATION + File.separator + name + File.separator + "database",
						new DatabaseSchema(LOCATION + File.separator + name, properties.getProvider(), properties,
								user),
						properties, properties.getProvider(), properties,
						new PrologObjectConverter(properties.getProvider()), storageMode));
	}

}
