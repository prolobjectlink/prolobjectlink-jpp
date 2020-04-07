/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.memory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import io.github.prolobjectlink.db.DatabaseEngine;
import io.github.prolobjectlink.db.DatabaseProperties;
import io.github.prolobjectlink.db.DatabaseSchema;
import io.github.prolobjectlink.db.DatabaseType;
import io.github.prolobjectlink.db.DatabaseUnitInfo;
import io.github.prolobjectlink.db.DatabaseUser;
import io.github.prolobjectlink.db.MemoryDatabase;
import io.github.prolobjectlink.db.Protocol;
import io.github.prolobjectlink.db.RelationalCache;
import io.github.prolobjectlink.db.Schema;
import io.github.prolobjectlink.db.VolatileContainer;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.util.Assertions;
import io.github.prolobjectlink.db.util.JavaReflect;
import io.github.prolobjectlink.db.xml.PersistenceXmlParser;
import io.github.prolobjectlink.logging.LoggerConstants;
import io.github.prolobjectlink.logging.LoggerUtils;

public final class MemoryRelational extends AbstractMemoryDatabase implements MemoryDatabase {

	private static MemoryDatabase memoryRelationalDB;
	private final Map<String, DatabaseUnitInfo> units;

	private MemoryRelational(Settings settings, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer container, Map<String, DatabaseUnitInfo> units) {
		super(settings, url, name, schema, owner, container);
		this.units = units;
	}

	private MemoryRelational(Settings settings, String name, URL url, Schema schema, DatabaseUser owner,
			RelationalCache cache) {
		super(settings, url, name, schema, owner, cache);
		units = new HashMap<String, DatabaseUnitInfo>();
	}

	public static final MemoryDatabase newInstance(String name) {
		return newInstance(name, new HashMap<Object, Object>());
	}

	public static final MemoryDatabase newInstance(String name, Map<?, ?> map) {
		if (memoryRelationalDB == null) {
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
						LoggerUtils.error(MemoryRelational.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
					String user = unit.getProperties().getProperty(DatabaseProperties.USER);
					DatabaseUser owner = new DatabaseUser(user, password);
					Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
					RelationalCache cache = settings.createRelationalCache(schema);
					for (String managedClass : unit.getManagedClassNames()) {
						schema.addClass(JavaReflect.classForName(managedClass), "");
					}
					memoryRelationalDB = new MemoryRelational(settings, name, url, schema, owner, cache).create();
				} else {
					LoggerUtils.error(MemoryRelational.class, "The given name don't match with persistence unit name");
				}
			}
		}
		return memoryRelationalDB;
	}

	public static final MemoryDatabase newInstance(DatabaseUnitInfo unit, Map<?, ?> map) {
		if (memoryRelationalDB == null) {
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
				LoggerUtils.error(MemoryRelational.class, LoggerConstants.IO, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			RelationalCache cache = settings.createRelationalCache(schema);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}
			memoryRelationalDB = new MemoryRelational(settings, name, url, schema, owner, cache).create();

		}
		return memoryRelationalDB;
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
		if (memoryRelationalDB == null) {
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
				LoggerUtils.error(MemoryRelational.class, LoggerConstants.URL, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			RelationalCache cache = settings.createRelationalCache(schema);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}
			memoryRelationalDB = new MemoryRelational(settings, name, url, schema, owner, cache).create();

		}
		return memoryRelationalDB;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}