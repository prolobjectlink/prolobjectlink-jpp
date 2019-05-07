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
package org.prolobjectlink.db.persistent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.prolobjectlink.db.DatabaseProperties;
import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.DatabaseUnitInfo;
import org.prolobjectlink.db.Protocol;
import org.prolobjectlink.db.RemoteDatabase;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.memory.MemoryHierarchical;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.db.xml.PersistenceXmlParser;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public final class RemoteHierarchical extends RemoteDatabaseClient implements RemoteDatabase {

	private static RemoteDatabase remoteHierarchicalDatabase;

	private RemoteHierarchical(Settings properties, URL url, String name, Schema schema, DatabaseUser owner) {
		super(properties, url, name, schema, owner);
	}

	public static final RemoteDatabase newInstance(String name) {
		return newInstance(name, new HashMap<Object, Object>());
	}

	public static final RemoteDatabase newInstance(String name, Map<?, ?> map) {
		if (remoteHierarchicalDatabase == null) {
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
						LoggerUtils.error(RemoteHierarchical.class, LoggerConstants.IO, e);
					}

					assert url != null;

					String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
					String user = unit.getProperties().getProperty(DatabaseProperties.USER);
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

	public static final RemoteDatabase newInstance(DatabaseUnitInfo unit, Map<?, ?> map) {
		if (remoteHierarchicalDatabase == null) {
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
				LoggerUtils.error(RemoteHierarchical.class, LoggerConstants.IO, e);
			}

			assert url != null;

			String password = unit.getProperties().getProperty(DatabaseProperties.PASSWORD);
			String user = unit.getProperties().getProperty(DatabaseProperties.USER);
			DatabaseUser owner = new DatabaseUser(user, password);
			Schema schema = new DatabaseSchema(url.getPath(), settings.getProvider(), settings, owner);
			for (String managedClass : unit.getManagedClassNames()) {
				schema.addClass(JavaReflect.classForName(managedClass), "");
			}
			remoteHierarchicalDatabase = new RemoteHierarchical(settings, url, name, schema, owner).connect(name)
					.create();
		}
		return remoteHierarchicalDatabase;
	}

	public DatabaseType getType() {
		return DatabaseType.HIERARCHICAL;
	}

}
