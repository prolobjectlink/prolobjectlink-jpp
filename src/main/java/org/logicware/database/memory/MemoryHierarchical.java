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
package org.logicware.database.memory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.database.DatabaseSchema;
import org.logicware.database.DatabaseType;
import org.logicware.database.DatabaseUser;
import org.logicware.database.HierarchicalCache;
import org.logicware.database.MemoryDatabase;
import org.logicware.database.Protocol;
import org.logicware.database.Schema;
import org.logicware.database.Settings;
import org.logicware.database.jpa.JpaProperties;
import org.logicware.database.jpa.spi.JPAPersistenceXmlParser;
import org.logicware.database.util.JavaReflect;
import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;

public final class MemoryHierarchical extends AbstractMemoryDatabase implements MemoryDatabase {

	private static MemoryDatabase memoryHierarchicalDatabase;

	private MemoryHierarchical(String name, URL url, Schema schema, DatabaseUser owner, HierarchicalCache cache) {
		super(cache.getProperties(), url, name, schema, owner, cache);
	}

	public static final MemoryDatabase newInstance(String name) {
		return newInstance(name, new HashMap<Object, Object>());
	}

	public static final MemoryDatabase newInstance(String name, Map<?, ?> map) {
		if (memoryHierarchicalDatabase == null) {
			JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
			Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (PersistenceUnitInfo unit : m.values()) {
				String unitName = unit.getPersistenceUnitName();
				if (unitName.equals(name)) {
					Settings settings = new Settings(unit.getProperties().getProperty(JpaProperties.DRIVER));
					URL url = null;
					try {
						System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
						url = new URL(unit.getProperties().getProperty(JpaProperties.URL).replace(URL_PREFIX, ""));
						if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
							throw new MalformedURLException("The URL path don't have database name at the end");
						}
					} catch (MalformedURLException e) {
						LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(JpaProperties.PASSWORD);
					String user = unit.getProperties().getProperty(JpaProperties.USER);
					DatabaseUser owner = new DatabaseUser(user, password);
					HierarchicalCache cache = settings.createHierarchicalCache();
					Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
					for (String managedClass : unit.getManagedClassNames()) {
						schema.addClass(JavaReflect.classForName(managedClass), "");
					}
					memoryHierarchicalDatabase = new MemoryHierarchical(name, url, schema, owner, cache).create();
				} else {
					LoggerUtils.error(MemoryHierarchical.class,
							"The given name don't match with persistence unit name");
				}
			}
		}
		return memoryHierarchicalDatabase;
	}

	public static final MemoryDatabase newInstance(PersistenceUnitInfo unit, Map<?, ?> map) {
		if (memoryHierarchicalDatabase == null) {
			String name = unit.getPersistenceUnitName();
			Settings settings = new Settings(unit.getProperties().getProperty(JpaProperties.DRIVER));
			URL url = null;
			try {
				System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
				url = new URL(unit.getProperties().getProperty(JpaProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database name at the end");
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.IO, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(JpaProperties.PASSWORD);
			String user = unit.getProperties().getProperty(JpaProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			HierarchicalCache cache = settings.createHierarchicalCache();
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}
			memoryHierarchicalDatabase = new MemoryHierarchical(name, url, schema, owner, cache).create();

		}
		return memoryHierarchicalDatabase;
	}

	public DatabaseType getType() {
		return DatabaseType.HIERARCHICAL;
	}

}
