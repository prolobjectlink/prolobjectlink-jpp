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
package org.logicware.db.prolog;

import org.logicware.db.DatabaseSchema;
import org.logicware.db.DatabaseUser;
import org.logicware.db.HierarchicalDatabase;
import org.logicware.db.StorageMode;
import org.logicware.db.engine.AbstractHierarchicalDatabase;
import org.logicware.db.etc.Settings;

/**
 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
 *             RemoteHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public class PrologHierarchicalDatabase extends AbstractHierarchicalDatabase implements HierarchicalDatabase {

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public PrologHierarchicalDatabase(Settings properties, StorageMode storageMode, String name, DatabaseUser user) {
		super(name, new DatabaseSchema(LOCATION + SEPARATOR + name, properties.getProvider(), properties, user), user,
				new PrologStorageManager(properties.getProvider(), properties,
						new PrologObjectConverter(properties.getProvider()),
						LOCATION + SEPARATOR + name + SEPARATOR + "database", properties, storageMode));
	}

}
