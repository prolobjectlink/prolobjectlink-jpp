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
package org.logicware.prolog;

import java.io.File;

import org.logicware.ContainerFactory;
import org.logicware.DatabaseUser;
import org.logicware.ObjectConverter;
import org.logicware.Settings;
import org.logicware.RelationalDatabase;
import org.logicware.db.AbstractRelationalDatabase;
import org.logicware.db.MetadataSchema;

public final class PrologRelationalDatabase extends AbstractRelationalDatabase implements RelationalDatabase {

	public PrologRelationalDatabase(PrologProvider provider, Settings properties, ContainerFactory containerFactory,
			String name, DatabaseUser user) {
		super(provider, containerFactory, name,
				new MetadataSchema(LOCATION + File.separator + name, provider, containerFactory, user), user,
				new PrologStorageGraph(LOCATION + File.separator + name + File.separator + "database",
						new MetadataSchema(LOCATION + File.separator + name, provider, containerFactory, user),
						properties, provider, containerFactory, new PrologObjectConverter(provider)));
	}

	public PrologRelationalDatabase(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, ContainerFactory containerFactory, String name, DatabaseUser user) {
		super(provider, containerFactory, name,
				new MetadataSchema(LOCATION + File.separator + name, provider, containerFactory, user), user,
				new PrologStorageGraph(LOCATION + File.separator + name + File.separator + "database",
						new MetadataSchema(LOCATION + File.separator + name, provider, containerFactory, user),
						properties, provider, containerFactory, converter));
	}

}
