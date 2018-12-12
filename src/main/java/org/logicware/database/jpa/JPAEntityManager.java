/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.database.jpa;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.DatabaseEngine;

public class JPAEntityManager extends JPAAbstractContainer implements EntityManager {

	private boolean closed;

	// property key-value map
	private final Map properties;

	//
	private boolean joinedTransaction;

	//
	private final EntityManagerFactory managerFactory;

	//
	private FlushModeType flushMode = FlushModeType.AUTO;

	//
	private final SynchronizationType synchronizationType;

	// user defined names for entities
	private final Map<String, Class<?>> entityMap;

	// user defined named queries container
	private final Map<String, Query> namedQueries;

	//
	private final Map<String, EntityGraph<?>> namedEntityGraphs;

	// result set mappings for native queries result
	private final Map<String, JPAResultSetMapping> resultSetMappings;

	//
	private final EntityTransaction entityTransaction;

	public JPAEntityManager(DatabaseEngine database, JPAEntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JPAResultSetMapping> resultSetMappings) {
		super(database, managerFactory.getPersistenceUnitUtil());
		this.entityTransaction = new JPAEntityTransaction(database.getTransaction());
		this.synchronizationType = synchronizationType;
		this.namedEntityGraphs = namedEntityGraphs;
		this.resultSetMappings = resultSetMappings;
		this.managerFactory = managerFactory;
		this.namedQueries = namedQueries;
		this.properties = properties;
		this.entityMap = entityMap;
	}

	public void persist(Object entity) {
		if (entity != null) {
			database.insert(entity);
		}
	}

	public <T> T merge(T entity) {
		Class<T> clazz = (Class<T>) entity.getClass();
		Object id = persistenceUnitUtil.getIdentifier(entity);
		T old = find(clazz, id);
		if (old != null) {
			database.update(old, entity);
		} else {
			persist(entity);
		}
		return entity;
	}

	public void remove(Object entity) {
		if (entity != null) {
			database.delete(entity);
		}
	}

	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return (T) find(entityClass, primaryKey, LockModeType.NONE, properties);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return find(entityClass, primaryKey, LockModeType.NONE, properties);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return (T) find(entityClass, primaryKey, lockMode, properties);
	}

	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {

		if (properties != null) {
			this.properties.putAll(properties);
		}

		if (entityClass != null && primaryKey != null) {
			List<T> all = findAll(entityClass);
			for (T t : all) {
				Object id = persistenceUnitUtil.getIdentifier(t);
				if (id != null && primaryKey.equals(id)) {
					return t;
				}
			}
		}
		return (T) null;

	}

	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		throw new UnsupportedOperationException("getReference(Class<T>, Object)");
	}

	public void flush() {
		database.commit();
	}

	public void setFlushMode(FlushModeType flushMode) {
		this.flushMode = flushMode;
	}

	public FlushModeType getFlushMode() {
		return flushMode;
	}

	public void lock(Object entity, LockModeType lockMode) {
		throw new UnsupportedOperationException("lock(Object, LockModeType)");
	}

	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		throw new UnsupportedOperationException("lock(Object, LockModeType,Map<String, Object>)");
	}

	public void refresh(Object entity) {
		refresh(entity, properties);
	}

	public void refresh(Object entity, Map<String, Object> properties) {
		refresh(entity, LockModeType.NONE, properties);
	}

	public void refresh(Object entity, LockModeType lockMode) {
		refresh(entity, lockMode, properties);
	}

	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {

		// Map<String, Object> thisProperties = this.properties;
		//
		// if (properties != null) {
		// thisProperties.putAll(properties);
		// }
		//
		// if (entity != null) {
		//
		// Class<?> clazz = entity.getClass();
		// Object id = persistenceUnitUtil.getIdentifier(entity);
		//
		// ObjectStore thisObjectStore = objectStore;
		//
		// String driver =
		// String.valueOf(thisProperties.get(JPAProperties.DRIVER));
		// String path = String.valueOf(thisProperties.get(JPAProperties.URL));
		// ObjectContainerFactory containerFactory =
		// Prolobjectlink.create(driver);
		// ObjectStore otherObjectStore = containerFactory.createStore(path);
		// otherObjectStore.open();
		// List<?> allObjects = otherObjectStore.findAll(clazz);
		// for (Object object : allObjects) {
		// Object objectId = persistenceUnitUtil.getIdentifier(entity);
		// if (objectId.equals(id)) {
		// thisObjectStore.add(object);
		// thisObjectStore.remove(entity);
		// }
		// }
		// otherObjectStore.clear();
		// otherObjectStore.close();
		//
		// }

	}

	public void clear() {
		database.clear();
	}

	public void detach(Object entity) {
		remove(entity);
	}

	public boolean contains(Object entity) {
		return database.contains(entity);
	}

	public LockModeType getLockMode(Object entity) {
		return LockModeType.NONE;
	}

	public void setProperty(String propertyName, Object value) {
		properties.put(propertyName, value);
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public Query createQuery(String qlString) {
		return new JPAQuery(database, qlString);
	}

	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return new JPATypedQuery<T>(database, "" + criteriaQuery + "", criteriaQuery.getResultType());
	}

	public Query createQuery(CriteriaUpdate updateQuery) {
		return new JPAQuery(database, "" + updateQuery + "");
	}

	public Query createQuery(CriteriaDelete deleteQuery) {
		return new JPAQuery(database, "" + deleteQuery + "");
	}

	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return new JPATypedQuery<T>(database, qlString, resultClass);
	}

	public Query createNamedQuery(String name) {
		return namedQueries.get(name);
	}

	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		JPATypedQuery<T> query = namedQueries.get(name).unwrap(JPATypedQuery.class);
		// query.resultClass[0] = resultClass;
		return query;
	}

	public Query createNativeQuery(String sqlString) {
		return new JPANativeQuery(database, sqlString);
	}

	public Query createNativeQuery(String sqlString, Class resultClass) {
		return new JPANativeQuery(database, sqlString, resultClass);
	}

	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		JPAResultSetMapping jpaResultSetMapping = resultSetMappings.get(resultSetMapping);
		return new JPANativeQuery(database, sqlString, jpaResultSetMapping);
	}

	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		Query query = namedQueries.get(name);
		if (!(query instanceof StoredProcedureQuery)) {
			throw new PersistenceException("The name '" + name + "' is not associate to StoredProcedureQuery");
		}
		return (StoredProcedureQuery) query;
	}

	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return new JPAStoredProcedureQuery(database, procedureName);
	}

	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
		return new JPAStoredProcedureQuery(database, procedureName, resultClasses);
	}

	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
		return new JPAStoredProcedureQuery(database, procedureName, resultSetMappings);
	}

	public void joinTransaction() {
		throw new UnsupportedOperationException("joinTransaction()");
	}

	public boolean isJoinedToTransaction() {
		return joinedTransaction;
	}

	public <T> T unwrap(Class<T> cls) {
		if (cls.equals(JPAEntityManager.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible unwrap to " + cls.getName());
	}

	public Object getDelegate() {
		return this;
	}

	public void close() {
		if (database != null) {
			database.close();
		}
		if (managerFactory != null) {
			managerFactory.close();
		}
		if (properties != null) {
			properties.clear();
		}
	}

	public boolean isOpen() {
		return closed;
	}

	public EntityTransaction getTransaction() {
		return entityTransaction;
	}

	public EntityManagerFactory getEntityManagerFactory() {
		return managerFactory;
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return managerFactory.getCriteriaBuilder();
	}

	public Metamodel getMetamodel() {
		return managerFactory.getMetamodel();
	}

	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		return new JPAEntityGraph<T>(rootType);
	}

	public EntityGraph<?> createEntityGraph(String graphName) {
		return new JPAEntityGraph<Object>(graphName);
	}

	public EntityGraph<?> getEntityGraph(String graphName) {
		return namedEntityGraphs.get(graphName);
	}

	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		List<EntityGraph<? super T>> graphs = new LinkedList<EntityGraph<? super T>>();
		for (EntityGraph<?> entityGraph : namedEntityGraphs.values()) {
			if (entityGraph instanceof JPAEntityGraph) {
				JPAEntityGraph<T> logicEntityGraph = (JPAEntityGraph<T>) entityGraph;
				if (logicEntityGraph.getClassType().equals(entityClass)) {
					graphs.add((EntityGraph<? super T>) logicEntityGraph);
				}
			}
		}
		return graphs;
	}
}
