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
package org.logicware.db.jpa;

import static org.logicware.db.XmlParser.XML;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import org.logicware.AbstractWrapper;
import org.logicware.db.DatabaseEngine;
import org.logicware.db.Protocol;
import org.logicware.db.jpa.spi.JPAPersistenceXmlParser;
import org.logicware.db.memory.MemoryHierarchical;
import org.logicware.db.persistent.EmbeddedHierarchical;
import org.logicware.db.persistent.RemoteHierarchical;
import org.logicware.logging.LoggerUtils;

/**
 * Implementation of {@link PersistenceProvider}. Derived classes just extend
 * from this abstract class.
 * 
 * @author Jose Zalacain
 * @version 1.0
 */
public abstract class JpaAbstractProvider extends AbstractWrapper implements PersistenceProvider {

	protected final ProviderUtil providerUtil = new JpaProviderUtil();
	protected final JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();

	protected static final String SECRET = "javax.persistence.jdbc.password";
	protected static final String DRIVER = "javax.persistence.jdbc.driver";
	protected static final String USER = "javax.persistence.jdbc.user";
	protected static final String URL = "javax.persistence.jdbc.url";
	protected static final String URL_PREFIX = "jdbc:prolobjectlink:";

	protected Map<String, EntityManagerFactory> entityManagerFactories = new LinkedHashMap<String, EntityManagerFactory>();

	protected final void assertPersistenceUnitExistenceInMapLoadedFromXml(String emName,
			Map<String, PersistenceUnitInfo> m) {
		if (m.get(emName) == null) {
			throw new PersistenceException("There are not persistence unit named " + emName);
		}
	}

	private DatabaseEngine createDatabaseEngine(PersistenceUnitInfo info, Map<?, ?> map) {
		try {
			DatabaseEngine database = null;
			String urlString = "" + info.getProperties().get(URL) + "";
			urlString = urlString.replace(URL_PREFIX, "");
			String url = new URL(urlString).getProtocol().toUpperCase();
			if (url.equals("" + Protocol.MEMPDB + "")) {
				database = MemoryHierarchical.newInstance(info, map);
			} else if (url.equals("" + Protocol.REMPDB + "")) {
				database = RemoteHierarchical.newInstance(info, map);
			} else if (url.equals("" + Protocol.FILE + "")) {
				database = EmbeddedHierarchical.newInstance(info, map);
			}
			return database;
		} catch (MalformedURLException e) {
			LoggerUtils.error(getClass(), e);
		}
		return null;
	}

	private DatabaseEngine generateSchema0(PersistenceUnitInfo info, Map map) {
		DatabaseEngine database = createDatabaseEngine(info, map);
		assert database != null;
		database.getSchema().flush();
		return database;
	}

	public final EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		EntityManagerFactory emf = entityManagerFactories.get(emName);
		if (emf == null) {
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
			assertPersistenceUnitExistenceInMapLoadedFromXml(emName, m);
			emf = createContainerEntityManagerFactory(m.get(emName), map);
		}
		return emf;
	}

	public final EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		DatabaseEngine database = createDatabaseEngine(info, map);
		assert database != null;
		return new JpaEntityManagerFactory(database, info.getProperties());
	}

	public final void generateSchema(PersistenceUnitInfo info, Map map) {
		generateSchema0(info, map);
	}

	public final boolean generateSchema(String persistenceUnitName, Map map) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
		assertPersistenceUnitExistenceInMapLoadedFromXml(persistenceUnitName, m);
		PersistenceUnitInfo unit = m.get(persistenceUnitName);
		DatabaseEngine database = generateSchema0(unit, map);
		return database.exist();
	}

	public final ProviderUtil getProviderUtil() {
		return providerUtil;
	}

}
