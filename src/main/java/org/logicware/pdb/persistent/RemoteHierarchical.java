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
package org.logicware.pdb.persistent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.pdb.DatabaseSchema;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.Protocol;
import org.logicware.pdb.RemoteDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.jpa.JPAProperties;
import org.logicware.pdb.jpa.spi.JPAPersistenceXmlParser;
import org.logicware.pdb.memory.MemoryHierarchical;
import org.logicware.pdb.util.JavaReflect;
import org.logicware.platform.logging.LoggerConstants;
import org.logicware.platform.logging.LoggerUtils;

public final class RemoteHierarchical extends RemoteDatabaseClient implements RemoteDatabase {

	private static RemoteDatabase remoteHierarchicalDatabase;

	private RemoteHierarchical(Settings properties, URL url, String name, Schema schema, DatabaseUser owner) {
		super(properties, url, name, schema, owner);
	}

	public static final RemoteDatabase newInstance(String name) {
		if (remoteHierarchicalDatabase == null) {
			JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
			Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (PersistenceUnitInfo unit : m.values()) {
				String unitName = unit.getPersistenceUnitName();
				if (unitName.equals(name)) {
					Settings settings = new Settings(unit.getProperties().getProperty(JPAProperties.DRIVER));
					URL url = null;
					try {
						System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
						url = new URL(unit.getProperties().getProperty(JPAProperties.URL).replace(URL_PREFIX, ""));
						if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
							throw new MalformedURLException("The URL path don't have database name at the end");
						}
					} catch (MalformedURLException e) {
						LoggerUtils.error(RemoteHierarchical.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(JPAProperties.PASSWORD);
					String user = unit.getProperties().getProperty(JPAProperties.USER);
					DatabaseUser owner = new DatabaseUser(user, password);
					Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
					for (String managedClass : unit.getManagedClassNames()) {
						schema.addClass(JavaReflect.classForName(managedClass), "");
					}
					remoteHierarchicalDatabase = new RemoteHierarchical(settings, url, name, schema, owner)
							.connect(name).create();
				} else {
					LoggerUtils.error(MemoryHierarchical.class,
							"The given name don't match with persistence unit name");
				}
			}
		}
		return remoteHierarchicalDatabase;
	}

	public DatabaseType getType() {
		return DatabaseType.HIERARCHICAL;
	}

}
