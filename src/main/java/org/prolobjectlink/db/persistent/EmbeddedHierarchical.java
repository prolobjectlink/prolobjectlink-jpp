/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.persistent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseProperties;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.EmbeddedDatabase;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.memory.MemoryHierarchical;
import org.prolobjectlink.db.spi.PersistenceXmlParser;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class EmbeddedHierarchical extends EmbeddedDatabaseClient implements EmbeddedDatabase {

	private static EmbeddedDatabase embeddedHierarchicalDatabase;

	EmbeddedHierarchical(String name, URL url, Schema schema, DatabaseUser owner, PersistentContainer storage) {
		super(storage.getProperties(), url, name, schema, owner, storage);
	}

	public static final EmbeddedDatabase newInstance(String name) {
		return newInstance(name, new HashMap<Object, Object>());
	}

	public static final EmbeddedDatabase newInstance(String name, Map<?, ?> map) {
		if (embeddedHierarchicalDatabase == null) {
			StorageMode mode = StorageMode.STORAGE_POOL;
			PersistenceXmlParser p = new PersistenceXmlParser();
			Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (PersistenceUnitInfo unit : m.values()) {
				String unitName = unit.getPersistenceUnitName();
				if (unitName.equals(name)) {
					Settings settings = new Settings(unit.getProperties().getProperty(DatabaseProperties.DRIVER));
					URL url = null;
					try {
						url = new URL(unit.getProperties().getProperty(DatabaseProperties.URL).replace(URL_PREFIX, ""));
						if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
							throw new MalformedURLException("The URL path don't have database name at the end");
						}
					} catch (MalformedURLException e) {
						LoggerUtils.error(EmbeddedHierarchical.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
					String user = unit.getProperties().getProperty(DatabaseProperties.USER);
					DatabaseUser owner = new DatabaseUser(user, password);
					StorageManager storage = settings.createStorageManager(url.getFile() + "/database", mode);
					Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
					for (String managedClass : unit.getManagedClassNames()) {
						schema.addClass(JavaReflect.classForName(managedClass), "");
					}
					embeddedHierarchicalDatabase = new EmbeddedHierarchical(name, url, schema, owner, storage).create();
				} else {
					LoggerUtils.error(MemoryHierarchical.class,
							"The given name don't match with persistence unit name");
				}
			}
		}
		return embeddedHierarchicalDatabase;
	}

	public static final EmbeddedDatabase newInstance(PersistenceUnitInfo unit, Map<?, ?> map) {
		StorageMode mode = StorageMode.STORAGE_POOL;
		if (embeddedHierarchicalDatabase == null) {
			String name = unit.getPersistenceUnitName();
			Settings settings = new Settings(unit.getProperties().getProperty(DatabaseProperties.DRIVER));
			URL url = null;
			try {
				url = new URL(unit.getProperties().getProperty(DatabaseProperties.URL).replace(URL_PREFIX, ""));
				if (!url.getPath().substring(url.getPath().lastIndexOf('/') + 1).equals(name)) {
					throw new MalformedURLException("The URL path don't have database name at the end");
				}
			} catch (MalformedURLException e) {
				LoggerUtils.error(EmbeddedHierarchical.class, LoggerConstants.IO, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			StorageManager storage = settings.createStorageManager(url.getFile() + "/database", mode);
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}
			embeddedHierarchicalDatabase = new EmbeddedHierarchical(name, url, schema, owner, storage).create();
		}
		return embeddedHierarchicalDatabase;
	}

	public DatabaseMode getMode() {
		return DatabaseMode.EMBEDDED;
	}

	public DatabaseType getType() {
		return DatabaseType.HIERARCHICAL;
	}

}
