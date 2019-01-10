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
package org.worklogic.db.memory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.worklogic.db.DatabaseEngine;
import org.worklogic.db.DatabaseSchema;
import org.worklogic.db.DatabaseType;
import org.worklogic.db.DatabaseUser;
import org.worklogic.db.HierarchicalCache;
import org.worklogic.db.MemoryDatabase;
import org.worklogic.db.Protocol;
import org.worklogic.db.Schema;
import org.worklogic.db.VolatileContainer;
import org.worklogic.db.etc.Settings;
import org.worklogic.db.jpa.JpaProperties;
import org.worklogic.db.jpa.spi.JPAPersistenceXmlParser;
import org.worklogic.db.util.Assertions;
import org.worklogic.db.util.JavaReflect;
import org.worklogic.logging.LoggerConstants;
import org.worklogic.logging.LoggerUtils;

public final class MemoryHierarchical extends AbstractMemoryDatabase implements MemoryDatabase {

	private static MemoryDatabase memoryHierarchicalDatabase;
	private final Map<String, PersistenceUnitInfo> units;

	private MemoryHierarchical(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer container, Map<String, PersistenceUnitInfo> units) {
		super(settings, url, name, schema, owner, container);
		this.units = units;
	}

	private MemoryHierarchical(String name, URL url, Schema schema, DatabaseUser owner, HierarchicalCache cache) {
		super(cache.getProperties(), url, name, schema, owner, cache);
		units = new HashMap<String, PersistenceUnitInfo>();
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

	/**
	 * Get or create an instance for the first persistence unit contained in the
	 * given persistence unit map.
	 * 
	 * @param m persistence unit map.
	 * @return an instance of the database for the first persistence unit contained
	 *         in the given persistence unit map.
	 * @since 1.0
	 */
	public static final DatabaseEngine newInstance(Map<String, PersistenceUnitInfo> m) {
		if (memoryHierarchicalDatabase == null) {
			m = Assertions.notNull(m);
			m = Assertions.nonEmpty(m);
			Collection<PersistenceUnitInfo> c = m.values();
			Iterator<PersistenceUnitInfo> i = c.iterator();
			PersistenceUnitInfo unit = i.next();
			String name = unit.getPersistenceUnitName();
			Settings settings = new Settings(unit.getProperties().getProperty(JpaProperties.DRIVER));
			URL url = null;
			try {
				System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
				url = new URL(unit.getProperties().getProperty(JpaProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database named " + name);
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.URL, e);
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
