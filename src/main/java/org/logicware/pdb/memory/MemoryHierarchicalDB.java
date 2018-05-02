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
package org.logicware.pdb.memory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.jpa.JPAProperties;
import org.logicware.jpa.spi.JPAPersistenceXmlParser;
import org.logicware.pdb.DatabaseMode;
import org.logicware.pdb.DatabaseSchema;
import org.logicware.pdb.DatabaseService;
import org.logicware.pdb.DatabaseType;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.HierarchicalCache;
import org.logicware.pdb.MemoryDatabase;
import org.logicware.pdb.ReflectionUtils;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.common.AbstractMemoryDatabase;
import org.logicware.pdb.embedded.EmbeddedHierarchicalDB;
import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public final class MemoryHierarchicalDB extends AbstractMemoryDatabase implements MemoryDatabase {

	private static DatabaseService memoryHierarchicalDB;

	public MemoryHierarchicalDB(String name, URL url, Schema schema, DatabaseUser owner, HierarchicalCache cache) {
		super(cache.getProvider(), cache.getProperties(), cache.getConverter(), cache.getContainerFactory(), url, name,
				schema, owner, cache);
	}

	public static final DatabaseService newInstance(String name) {
		if (memoryHierarchicalDB == null) {
			JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
			Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (PersistenceUnitInfo unit : m.values()) {
				String unitName = unit.getPersistenceUnitName();
				if (unitName.equals(name)) {
					Settings settings = new Settings(unit.getProperties().getProperty(JPAProperties.DRIVER));
					URL url = null;
					try {
						url = new URL(unit.getProperties().getProperty(JPAProperties.URL).replace(URL_PREFIX, ""));
					} catch (MalformedURLException e) {
						LoggerUtils.error(EmbeddedHierarchicalDB.class, LoggerConstants.IO_ERROR, e);
					}
					String password = unit.getProperties().getProperty(JPAProperties.PASSWORD);
					String user = unit.getProperties().getProperty(JPAProperties.USER);
					DatabaseUser owner = new DatabaseUser(user, password);

					HierarchicalCache cache = settings.createHierarchicalCache();

					Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
					for (String managedClass : unit.getManagedClassNames()) {
						schema.addClass(ReflectionUtils.classForName(managedClass), "");
					}
					memoryHierarchicalDB = new MemoryHierarchicalDB(name, url, schema, owner, cache).create();
				}
			}
		}
		return memoryHierarchicalDB;
	}

	public DatabaseType getType() {
		return DatabaseType.HIERARCHY;
	}

}
