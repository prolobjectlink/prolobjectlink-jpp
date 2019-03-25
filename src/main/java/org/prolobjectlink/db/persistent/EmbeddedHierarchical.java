/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db.persistent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseProperties;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.EmbeddedDatabase;
import org.prolobjectlink.db.DatabaseUnitInfo;
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
			Map<String, DatabaseUnitInfo> m = p.parsePersistenceXml(persistenceXml);
			for (DatabaseUnitInfo unit : m.values()) {
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

	public static final EmbeddedDatabase newInstance(DatabaseUnitInfo unit, Map<?, ?> map) {
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
