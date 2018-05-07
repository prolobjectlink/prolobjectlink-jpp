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
package org.logicware.pdb.embedded;

import java.net.URL;

import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.EmbeddedDatabase;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Schema;
import org.logicware.pdb.common.AbstractPersistentDatabase;

public final class EmbeddedRelationalDB extends AbstractPersistentDatabase implements EmbeddedDatabase {

	protected static EmbeddedRelationalDB embeddedRelationalDB;

	private EmbeddedRelationalDB(String name, URL url, Schema schema, DatabaseUser owner, PersistentContainer storage) {
		super(storage.getProvider(), storage.getProperties(), storage.getConverter(), storage.getContainerFactory(),
				url, name, schema, owner, storage);
	}

	public static final EmbeddedRelationalDB newInstance() {
		if (embeddedRelationalDB == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return embeddedRelationalDB;
	}

	public DatabaseMode getMode() {
		return DatabaseMode.EMBEDDED;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}