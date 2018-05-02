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

import static org.logicware.jpa.spi.JPAPersistenceXmlParser.XML;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.SynchronizationType;
import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.jpa.JPAEntityManager;
import org.logicware.jpa.JPAEntityManagerFactory;
import org.logicware.jpa.JPAResultSetMapping;
import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DatabaseService;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.PersistentContainer;
import org.logicware.pdb.Predicate;
import org.logicware.pdb.ProcedureQuery;
import org.logicware.pdb.Query;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.TypedQuery;
import org.logicware.pdb.VolatileContainer;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

public abstract class AbstractDatabaseService extends AbstractDatabaseEngine implements DatabaseService {

	private final URL url;

	public static final String URL_PREFIX = "jdbc:prolobjectlink:";

	protected static URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	protected Map<String, PersistenceUnitInfo> units = new HashMap<String, PersistenceUnitInfo>();

	public AbstractDatabaseService(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, URL url, String name, Schema schema, DatabaseUser owner) {
		super(provider, properties, converter, containerFactory, url.getPath(), name, schema, owner);
		this.url = url;
	}

	public AbstractDatabaseService(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, URL url, String name, Schema schema, DatabaseUser owner,
			VolatileContainer cache) {
		super(provider, properties, converter, containerFactory, url.getPath(), name, schema, owner);
		// this.storage = storage;
		this.url = url;
	}

	public final URL getURL() {
		return url;
	}

	public final String getDatabaseLocation() {
		return getLocation() + "/database";
	}

	public final String getBaseLocation() {
		return url.getPath();
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
	}

	public final DatabaseService create() {
		// TODO Create functions and views file
		getSchema().flush();
		return this;
	}

	public final void drop() {
		getSchema().clear();
		getSchema().flush();
	}

	public final EntityManager getEntityManager() {

		// TODO FILL ALL MAPS

		// user defined named queries container
		Map<String, javax.persistence.Query> namedQueries = new HashMap<String, javax.persistence.Query>();

		// result set mappings for native queries result
		Map<String, JPAResultSetMapping> resultSetMappings = new HashMap<String, JPAResultSetMapping>();

		//
		Map<String, EntityGraph<?>> namedEntityGraphs = new HashMap<String, EntityGraph<?>>();

		// user defined names for entities
		Map<String, Class<?>> entityMap = new HashMap<String, Class<?>>();

		//
		JPAEntityManagerFactory factory = new JPAEntityManagerFactory(this);

		//
		Settings settings = getProperties();

		//
		Map map = settings.toMap();

		return new JPAEntityManager(this, factory, SynchronizationType.SYNCHRONIZED, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

}
