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
package org.logicware.pdb.common;

import static org.logicware.pdb.jpa.spi.JPAPersistenceXmlParser.XML;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.pdb.DatabaseService;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.jpa.JPAEntityManager;
import org.logicware.pdb.jpa.JPAEntityManagerFactory;
import org.logicware.pdb.jpa.JPAResultSetMapping;

public abstract class AbstractDatabaseService extends AbstractDatabaseEngine implements DatabaseService {

	private final URL url;

	public static final String URL_PREFIX = "jdbc:prolobjectlink:";

	protected static URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	protected Map<String, PersistenceUnitInfo> units = new HashMap<String, PersistenceUnitInfo>();

	public AbstractDatabaseService(Settings settings, URL url, String name, Schema schema, DatabaseUser owner) {
		super(settings, url.getPath(), name, schema, owner);
		this.url = url;
	}

	public final URL getURL() {
		return url;
	}

	public final String getDatabaseLocation() {
		return getLocation() + "/database";
	}

	public final EntityManager getEntityManager() {

		// TODO FILL ALL MAPS

		// properties map
		Map map = getProperties().asMap();

		// user defined named queries container
		Map<String, Query> namedQueries = new HashMap<String, Query>();

		// user defined names for entities
		Map<String, Class<?>> entityMap = new HashMap<String, Class<?>>();

		//
		JPAEntityManagerFactory factory = new JPAEntityManagerFactory(this);

		//
		Map<String, EntityGraph<?>> namedEntityGraphs = new HashMap<String, EntityGraph<?>>();

		// result set mappings for native queries result
		Map<String, JPAResultSetMapping> resultSetMappings = new HashMap<String, JPAResultSetMapping>();

		return new JPAEntityManager(this, factory, SynchronizationType.SYNCHRONIZED, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

	public final String getRootLocation() {
		return url.getPath();
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
	}

}
