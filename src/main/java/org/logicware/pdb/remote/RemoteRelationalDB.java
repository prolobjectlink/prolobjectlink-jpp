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
package org.logicware.pdb.remote;

import java.net.URL;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.RemoteDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.common.AbstractRemoteDatabase;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

public final class RemoteRelationalDB extends AbstractRemoteDatabase implements RemoteDatabase {

	private static RemoteRelationalDB remoteRelationalDatabase;

	private RemoteRelationalDB(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, URL url, String name, Schema schema, DatabaseUser owner, String server,
			int port) {
		super(provider, properties, converter, containerFactory, url, name, schema, owner, server, port);
	}

	public static final RemoteRelationalDB newInstance() {
		if (remoteRelationalDatabase == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return remoteRelationalDatabase;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}
