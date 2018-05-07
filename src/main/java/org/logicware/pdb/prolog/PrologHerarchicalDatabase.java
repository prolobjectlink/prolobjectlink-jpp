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
package org.logicware.pdb.prolog;

import java.io.File;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DatabaseSchema;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.HierarchicalDatabase;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Settings;
import org.logicware.pdb.StorageMode;
import org.logicware.pdb.common.AbstractHerarchicalDatabase;

public class PrologHerarchicalDatabase extends AbstractHerarchicalDatabase implements HierarchicalDatabase {

	public PrologHerarchicalDatabase(PrologProvider provider, Settings properties, ContainerFactory containerFactory,
			StorageMode storageMode, String name, DatabaseUser user) {
		super(provider, containerFactory, name,
				new DatabaseSchema(LOCATION + File.separator + name, provider, containerFactory, user), user,
				new PrologStorageManager(provider, properties, new PrologObjectConverter(provider),
						LOCATION + File.separator + name + File.separator + "database", containerFactory, storageMode));
	}

	public PrologHerarchicalDatabase(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, ContainerFactory containerFactory, StorageMode storageMode,
			String name, DatabaseUser user) {
		super(provider, containerFactory, name,
				new DatabaseSchema(LOCATION + File.separator + name, provider, containerFactory, user), user,
				new PrologStorageManager(provider, properties, converter,
						LOCATION + File.separator + name + File.separator + "database", containerFactory, storageMode));
	}

}