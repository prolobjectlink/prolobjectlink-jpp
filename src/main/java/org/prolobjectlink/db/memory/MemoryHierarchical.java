/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.memory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.DatabaseProperties;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.HierarchicalCache;
import org.prolobjectlink.db.MemoryDatabase;
import org.prolobjectlink.db.DatabaseUnitInfo;
import org.prolobjectlink.db.Protocol;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.VolatileContainer;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.spi.PersistenceXmlParser;
import org.prolobjectlink.db.util.Assertions;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class MemoryHierarchical extends AbstractMemoryDatabase implements MemoryDatabase {

	private static MemoryDatabase memoryHierarchicalDatabase;
	private final Map<String, DatabaseUnitInfo> units;

	private MemoryHierarchical(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer container, Map<String, DatabaseUnitInfo> units) {
		super(settings, url, name, schema, owner, container);
		this.units = units;
	}

	private MemoryHierarchical(String name, URL url, Schema schema, DatabaseUser owner, HierarchicalCache cache) {
		super(cache.getProperties(), url, name, schema, owner, cache);
		units = new HashMap<String, DatabaseUnitInfo>();
	}

	public static final MemoryDatabase newInstance(String name) {
		return newInstance(name, new HashMap<Object, Object>());
	}

	public static final MemoryDatabase newInstance(String name, Map<?, ?> map) {
		if (memoryHierarchicalDatabase == null) {
			PersistenceXmlParser p = new PersistenceXmlParser();
			Map<String, DatabaseUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (DatabaseUnitInfo unit : m.values()) {
				String unitName = unit.getPersistenceUnitName();
				if (unitName.equals(name)) {
					Settings settings = new Settings(unit.getProperties().getProperty(DatabaseProperties.DRIVER));
					URL url = null;
					try {
						System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
						url = new URL(unit.getProperties().getProperty(DatabaseProperties.URL).replace(URL_PREFIX, ""));
						if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
							throw new MalformedURLException("The URL path don't have database name at the end");
						}
					} catch (MalformedURLException e) {
						LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
					String user = unit.getProperties().getProperty(DatabaseProperties.USER);
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

	public static final MemoryDatabase newInstance(DatabaseUnitInfo unit, Map<?, ?> map) {
		if (memoryHierarchicalDatabase == null) {
			String name = unit.getPersistenceUnitName();
			Settings settings = new Settings(unit.getProperties().getProperty(DatabaseProperties.DRIVER));
			URL url = null;
			try {
				System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
				url = new URL(unit.getProperties().getProperty(DatabaseProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database name at the end");
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.IO, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
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
	public static final DatabaseEngine newInstance(Map<String, DatabaseUnitInfo> m) {
		if (memoryHierarchicalDatabase == null) {
			m = Assertions.notNull(m);
			m = Assertions.nonEmpty(m);
			Collection<DatabaseUnitInfo> c = m.values();
			Iterator<DatabaseUnitInfo> i = c.iterator();
			DatabaseUnitInfo unit = i.next();
			String name = unit.getPersistenceUnitName();
			Settings settings = new Settings(unit.getProperties().getProperty(DatabaseProperties.DRIVER));
			URL url = null;
			try {
				System.setProperty("java.protocol.handler.pkgs", Protocol.class.getPackage().getName());
				url = new URL(unit.getProperties().getProperty(DatabaseProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database named " + name);
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(MemoryHierarchical.class, LoggerConstants.URL, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
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
