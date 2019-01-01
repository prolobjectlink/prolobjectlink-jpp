/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.database.jpa.spi;

import static org.logicware.database.jpa.spi.JPAPersistenceXmlParser.XML;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.AttributeNode;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.LockModeType;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.Parameter;
import javax.persistence.ParameterMode;
import javax.persistence.Query;
import javax.persistence.QueryHint;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import org.logicware.database.DatabaseEngine;
import org.logicware.database.DatabaseUser;
import org.logicware.database.ObjectConverter;
import org.logicware.database.etc.Settings;
import org.logicware.database.jpa.JpaAttributeNode;
import org.logicware.database.jpa.JpaColumnResult;
import org.logicware.database.jpa.JpaConstructorResult;
import org.logicware.database.jpa.JpaEntityGraph;
import org.logicware.database.jpa.JpaEntityManagerFactory;
import org.logicware.database.jpa.JpaEntityResult;
import org.logicware.database.jpa.JpaFieldResult;
import org.logicware.database.jpa.JpaNativeQuery;
import org.logicware.database.jpa.JpaParameter;
import org.logicware.database.jpa.JpaProperties;
import org.logicware.database.jpa.JpaProviderUtil;
import org.logicware.database.jpa.JpaQuery;
import org.logicware.database.jpa.JpaResultSetMapping;
import org.logicware.database.jpa.JpaStoredProcedureQuery;
import org.logicware.database.prolog.PrologObjectConverter;
import org.logicware.database.util.JavaReflect;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public abstract class JPAPersistenceProvider implements PersistenceProvider {

	public static final String URL_PREFIX = "jdbc:prolobjectlink:store:";
	public static final String JPI_PROVIDER_PROPERTY = "org.logicware.jpi.provider";
	public static final String JPD_DRIVER_PROPERTY = "org.logicware.jpd.driver";
	private static final ProviderUtil PROVIDER_UTIL = new JpaProviderUtil();

	private final Map<String, PersistenceUnitInfo> persistenceUnits;
	private DatabaseEngine database;

	public JPAPersistenceProvider() {
		URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);
		persistenceUnits = new JPAPersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		PersistenceUnitInfo info = persistenceUnits.get(emName);
		return createContainerEntityManagerFactory(info, info.getProperties());
	}

	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {

		assert info != null;

		JpaProperties properties = (JpaProperties) info.getProperties();

		if (map != null && !map.isEmpty()) {
			properties.putAll(map);
		}

		// retrieving persistence unit properties
		// String provider = properties.getPersistenceProviderClassName();
		String driver = String.valueOf(properties.get(JpaProperties.DRIVER));
		String url = String.valueOf(properties.get(JpaProperties.URL)).replace(URL_PREFIX, "");
		String username = String.valueOf(properties.get(JpaProperties.USER));
		String password = String.valueOf(properties.get(JpaProperties.PASSWORD));
		PrologProvider prologProvider = createJavaPrologIntefaceProvider();
		Class<?> objectDriverClass = JavaReflect.classForName(driver);
		DatabaseUser user = new DatabaseUser(username, password);

		database = createDatabase(info.getPersistenceProviderClassName(), info.getPersistenceUnitName(),
				new Settings(driver), url, user);

		EntityManagerFactory managerFactory = new JpaEntityManagerFactory(database);

		List<String> managedClasNames = info.getManagedClassNames();
		for (String className : managedClasNames) {
			Class<?> clazz = JavaReflect.classForName(className);
			registerEntities(managerFactory, prologProvider, url, clazz);
			registerNamedQueries(managerFactory, prologProvider, url, clazz);
			registerNamedNativedQueries(managerFactory, prologProvider, url, clazz);
			registerNamedEntitiesGraph(managerFactory, prologProvider, url, clazz);
			registerSqlResultSetMapping(managerFactory, prologProvider, url, clazz);
			registerNamedStoredProcedureQueries(managerFactory, objectDriverClass, prologProvider, url, clazz);
		}
		return managerFactory;

	}

	protected abstract DatabaseEngine createDatabase(String provider, String name, Settings settings, String url,
			DatabaseUser user);

	public abstract String getJavaPrologIntefaceProviderName();

	public abstract PrologProvider createJavaPrologIntefaceProvider();

	public void generateSchema(PersistenceUnitInfo info, Map map) {
		// do nothing
	}

	public boolean generateSchema(String persistenceUnitName, Map map) {
		return false;
	}

	public ProviderUtil getProviderUtil() {
		return PROVIDER_UTIL;
	}

	private void registerEntities(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(Entity.class)) {
			String name = clazz.getAnnotation(Entity.class).name();
			if (name != null && !name.isEmpty()) {
				managerFactory.unwrap(JpaEntityManagerFactory.class).addEntity(name, clazz);
			} else {
				managerFactory.unwrap(JpaEntityManagerFactory.class).addEntity(clazz.getSimpleName(), clazz);
			}
		}
	}

	private void registerNamedQueries(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedQuery.class)) {
			addNamedQuery(managerFactory, provider, url, clazz.getAnnotation(NamedQuery.class));
		} else if (clazz.isAnnotationPresent(NamedQueries.class)) {
			NamedQuery[] namedQueries = clazz.getAnnotation(NamedQueries.class).value();
			for (NamedQuery namedQuery : namedQueries) {
				addNamedQuery(managerFactory, provider, url, namedQuery);
			}
		}
	}

	private void registerNamedNativedQueries(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedNativeQuery.class)) {
			addNamedNativeQuery(managerFactory, provider, url, clazz.getAnnotation(NamedNativeQuery.class));
		} else if (clazz.isAnnotationPresent(NamedNativeQueries.class)) {
			NamedNativeQuery[] namedQueries = clazz.getAnnotation(NamedNativeQueries.class).value();
			for (NamedNativeQuery namedQuery : namedQueries) {
				addNamedNativeQuery(managerFactory, provider, url, namedQuery);
			}
		}
	}

	private void registerSqlResultSetMapping(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(SqlResultSetMapping.class)) {
			addSqlResultSetMapping(managerFactory, provider, url, clazz.getAnnotation(SqlResultSetMapping.class),
					clazz);
		} else if (clazz.isAnnotationPresent(SqlResultSetMappings.class)) {
			SqlResultSetMapping[] resultSetMappings = clazz.getAnnotation(SqlResultSetMappings.class).value();
			for (SqlResultSetMapping sqlResultSetMapping : resultSetMappings) {
				addSqlResultSetMapping(managerFactory, provider, url, sqlResultSetMapping, clazz);
			}
		}
	}

	private void registerNamedEntitiesGraph(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedEntityGraph.class)) {
			addNamedEntityGraph(managerFactory, provider, url, clazz.getAnnotation(NamedEntityGraph.class), clazz);
		} else if (clazz.isAnnotationPresent(NamedEntityGraphs.class)) {
			NamedEntityGraph[] namedEntityGraphs = clazz.getAnnotation(NamedEntityGraphs.class).value();
			for (NamedEntityGraph namedEntityGraph : namedEntityGraphs) {
				addNamedEntityGraph(managerFactory, provider, url, namedEntityGraph, clazz);
			}
		}
	}

	private void registerNamedStoredProcedureQueries(EntityManagerFactory managerFactory, Class<?> driver,
			PrologProvider provider, String url, Class<?> clazz) {
		if (clazz.isAnnotationPresent(NamedStoredProcedureQuery.class)) {
			addNamedStoredProcedureQuery(managerFactory, driver, provider, url,
					clazz.getAnnotation(NamedStoredProcedureQuery.class), clazz);
		} else if (clazz.isAnnotationPresent(NamedStoredProcedureQueries.class)) {
			NamedStoredProcedureQuery[] namedStoredProcedureQueries = clazz
					.getAnnotation(NamedStoredProcedureQueries.class).value();
			for (NamedStoredProcedureQuery namedStoredProcedureQuery : namedStoredProcedureQueries) {
				addNamedStoredProcedureQuery(managerFactory, driver, provider, url, namedStoredProcedureQuery, clazz);
			}
		}

	}

	private void addNamedQuery(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedQuery namedQuery) {
		String name = namedQuery.name();
		String qlString = namedQuery.query();
		LockModeType lockModeType = namedQuery.lockMode();
		Query query = new JpaQuery(database, qlString);
		QueryHint[] hints = namedQuery.hints();
		for (QueryHint queryHint : hints) {
			query.setHint(queryHint.name(), queryHint.value());
		}
		managerFactory.addNamedQuery(name, query);
	}

	private void addNamedNativeQuery(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedNativeQuery namedQuery) {
		String name = namedQuery.name();
		String query = namedQuery.query();
		Class<?> resultClass = namedQuery.resultClass();
		String resultSetMapping = namedQuery.resultSetMapping();
		Query typedQuery = new JpaNativeQuery(database, query, resultClass);
		QueryHint[] hints = namedQuery.hints();
		for (QueryHint queryHint : hints) {
			typedQuery.setHint(queryHint.name(), queryHint.value());
		}
		managerFactory.addNamedQuery(name, typedQuery);
	}

	private void addSqlResultSetMapping(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			SqlResultSetMapping resultSetMapping, Class<?> clazz) {
		String name = resultSetMapping.name();
		EntityResult[] entities = resultSetMapping.entities();
		ConstructorResult[] classes = resultSetMapping.classes();
		ColumnResult[] columns = resultSetMapping.columns();

		// entities
		JpaEntityResult[] entityResults = new JpaEntityResult[entities.length];
		for (int i = 0; i < entities.length; i++) {
			EntityResult entityResult = entities[i];
			Class<?> entityClass = entityResult.entityClass();
			FieldResult[] fields = entityResult.fields();
			JpaFieldResult[] fieldResults = new JpaFieldResult[fields.length];
			for (int j = 0; j < fields.length; j++) {
				FieldResult fieldResult = fields[j];
				String fname = fieldResult.name();
				String column = fieldResult.column();
				JpaFieldResult result = new JpaFieldResult(fname, column);
				fieldResults[j] = result;
			}
			JpaEntityResult result = new JpaEntityResult(entityClass, fieldResults, "");
			entityResults[i] = result;
		}

		// constructors
		JpaConstructorResult[] classesResults = new JpaConstructorResult[classes.length];
		for (int i = 0; i < classes.length; i++) {
			ConstructorResult classResult = classes[i];
			Class<?> targetClass = classResult.targetClass();
			ColumnResult[] columnResults = classResult.columns();
			JpaColumnResult[] jpaColumnResults = new JpaColumnResult[columnResults.length];
			for (int j = 0; j < columnResults.length; j++) {
				ColumnResult columnResult = columnResults[j];
				String columnName = columnResult.name();
				Class<?> type = columnResult.type();
				JpaColumnResult result = new JpaColumnResult(columnName, type);
				jpaColumnResults[j] = result;
			}
			JpaConstructorResult constructorResult = new JpaConstructorResult(targetClass, jpaColumnResults);
			classesResults[i] = constructorResult;
		}

		// columns
		JpaColumnResult[] jpaColumnResults = new JpaColumnResult[columns.length];
		for (int j = 0; j < columns.length; j++) {
			ColumnResult columnResult = columns[j];
			String columnName = columnResult.name();
			Class<?> type = columnResult.type();
			JpaColumnResult result = new JpaColumnResult(columnName, type);
			jpaColumnResults[j] = result;
		}

		JpaResultSetMapping mapping = new JpaResultSetMapping(name, entityResults, classesResults, jpaColumnResults);
		managerFactory.unwrap(JpaEntityManagerFactory.class).addResultSetMapping(mapping);
	}

	private void addNamedEntityGraph(EntityManagerFactory managerFactory, PrologProvider provider, String url,
			NamedEntityGraph namedEntityGraph, Class<?> clazz) {
		String name = namedEntityGraph.name();
		NamedAttributeNode[] nodes = namedEntityGraph.attributeNodes();
		boolean allAttributes = namedEntityGraph.includeAllAttributes();
		NamedSubgraph[] namedSubgraphs = namedEntityGraph.subgraphs();
		NamedSubgraph[] namedSubclassSubgraphs = namedEntityGraph.subclassSubgraphs();

		List<AttributeNode<?>> attributeNodes = new ArrayList<AttributeNode<?>>(nodes.length);
		for (NamedAttributeNode namedAttributeNode : nodes) {
			// TODO SUBGRAPH AND KEYSUBGRAPH
			AttributeNode<?> attributeNode = new JpaAttributeNode(namedAttributeNode.value());
			attributeNodes.add(attributeNode);
		}

		Map<Class, JpaEntityGraph> subgraphs = new HashMap<Class, JpaEntityGraph>();
		Map<Class, JpaEntityGraph> subclassSubgraphs = new HashMap<Class, JpaEntityGraph>();

		// TODO FILL THIS GRAPHS

		JpaEntityGraph<?> entityGraph = new JpaEntityGraph(name, subgraphs, subclassSubgraphs, allAttributes,
				attributeNodes, clazz);

		managerFactory.addNamedEntityGraph(name, entityGraph);
	}

	private void addNamedStoredProcedureQuery(EntityManagerFactory managerFactory, Class<?> driver,
			PrologProvider provider, String url, NamedStoredProcedureQuery namedStoredProcedureQuery, Class<?> clazz) {

		String name = namedStoredProcedureQuery.name();
		String procedureName = namedStoredProcedureQuery.procedureName();
		Class<?>[] resultClasses = namedStoredProcedureQuery.resultClasses();
		String[] resultSetMappings = namedStoredProcedureQuery.resultSetMappings();
		ObjectConverter<PrologTerm> factory = new PrologObjectConverter(provider);
		PrologEngine engine = provider.newEngine();
		StoredProcedureQuery storedProcedureQuery = new JpaStoredProcedureQuery(database, procedureName, resultClasses,
				resultSetMappings);

		QueryHint[] queryHints = namedStoredProcedureQuery.hints();
		for (QueryHint queryHint : queryHints) {
			storedProcedureQuery.setHint(queryHint.name(), queryHint.value());
		}

		StoredProcedureParameter[] storedProcedureParameters = namedStoredProcedureQuery.parameters();
		for (int i = 0; i < storedProcedureParameters.length; i++) {
			StoredProcedureParameter storedProcedureParameter = storedProcedureParameters[i];
			String parameterName = storedProcedureParameter.name();
			ParameterMode parameterMode = storedProcedureParameter.mode();
			Class<?> parameterType = storedProcedureParameter.type();
			Parameter<?> parameter = new JpaParameter(parameterName, i, parameterType);
			storedProcedureQuery.setParameter(parameter, null);
		}

		managerFactory.addNamedQuery(name, storedProcedureQuery);

	}
}
