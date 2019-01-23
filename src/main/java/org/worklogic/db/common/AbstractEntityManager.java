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
package org.worklogic.db.common;

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

import org.worklogic.db.DatabaseEngine;
import org.worklogic.db.jpa.JpaAbstractContainer;
import org.worklogic.db.jpa.JpaEntityGraph;
import org.worklogic.db.jpa.JpaEntityManager;
import org.worklogic.db.jpa.JpaEntityTransaction;
import org.worklogic.db.jpa.JpaNativeQuery;
import org.worklogic.db.jpa.JpaQuery;
import org.worklogic.db.jpa.JpaResultSetMapping;
import org.worklogic.db.jpa.JpaStoredProcedureQuery;
import org.worklogic.db.jpa.JpaTypedQuery;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractEntityManager extends JpaAbstractContainer implements EntityManager {

	protected volatile boolean closed;

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
	private final Map<String, JpaResultSetMapping> resultSetMappings;

	//
	private final EntityTransaction entityTransaction;

	public AbstractEntityManager(DatabaseEngine database, EntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JpaResultSetMapping> resultSetMappings) {
		super(database, managerFactory.getPersistenceUnitUtil());
		this.entityTransaction = new JpaEntityTransaction(database.getTransaction());
		this.synchronizationType = synchronizationType;
		this.namedEntityGraphs = namedEntityGraphs;
		this.resultSetMappings = resultSetMappings;
		this.managerFactory = managerFactory;
		this.namedQueries = namedQueries;
		this.properties = properties;
		this.entityMap = entityMap;
		database.begin();
	}

	public final void persist(Object entity) {
		if (entity != null) {
			database.insert(entity);
		}
	}

	public final <T> T merge(T entity) {
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

	public final void remove(Object entity) {
		if (entity != null) {
			database.delete(entity);
		}
	}

	public final <T> T find(Class<T> entityClass, Object primaryKey) {
		return (T) find(entityClass, primaryKey, LockModeType.NONE, properties);
	}

	public final <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return find(entityClass, primaryKey, LockModeType.NONE, properties);
	}

	public final <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return (T) find(entityClass, primaryKey, lockMode, properties);
	}

	public final <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode,
			Map<String, Object> properties) {

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

	public final <T> T getReference(Class<T> entityClass, Object primaryKey) {
		throw new UnsupportedOperationException("getReference(Class<T>, Object)");
	}

	public final void flush() {
		database.commit();
	}

	public final void setFlushMode(FlushModeType flushMode) {
		this.flushMode = flushMode;
	}

	public final FlushModeType getFlushMode() {
		return flushMode;
	}

	public final void lock(Object entity, LockModeType lockMode) {
		throw new UnsupportedOperationException("lock(Object, LockModeType)");
	}

	public final void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		throw new UnsupportedOperationException("lock(Object, LockModeType,Map<String, Object>)");
	}

	public final void refresh(Object entity) {
		refresh(entity, properties);
	}

	public final void refresh(Object entity, Map<String, Object> properties) {
		refresh(entity, LockModeType.NONE, properties);
	}

	public final void refresh(Object entity, LockModeType lockMode) {
		refresh(entity, lockMode, properties);
	}

	public final void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {

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

	public final void clear() {
		database.clear();
	}

	public final void detach(Object entity) {
		remove(entity);
	}

	public final boolean contains(Object entity) {
		return database.contains(entity);
	}

	public final LockModeType getLockMode(Object entity) {
		return LockModeType.NONE;
	}

	public final void setProperty(String propertyName, Object value) {
		properties.put(propertyName, value);
	}

	public final Map<String, Object> getProperties() {
		return properties;
	}

	public final Query createQuery(String qlString) {
		return new JpaQuery(database, qlString);
	}

	public final <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return new JpaTypedQuery<T>(database, "" + criteriaQuery + "", criteriaQuery.getResultType());
	}

	public final Query createQuery(CriteriaUpdate updateQuery) {
		return new JpaQuery(database, "" + updateQuery + "");
	}

	public final Query createQuery(CriteriaDelete deleteQuery) {
		return new JpaQuery(database, "" + deleteQuery + "");
	}

	public final <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return new JpaTypedQuery<T>(database, qlString, resultClass);
	}

	public final Query createNamedQuery(String name) {
		return namedQueries.get(name);
	}

	public final <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		JpaTypedQuery<T> query = namedQueries.get(name).unwrap(JpaTypedQuery.class);
		// query.resultClass[0] = resultClass;
		return query;
	}

	public final Query createNativeQuery(String sqlString) {
		return new JpaNativeQuery(database, sqlString);
	}

	public final Query createNativeQuery(String sqlString, Class resultClass) {
		return new JpaNativeQuery(database, sqlString, resultClass);
	}

	public final Query createNativeQuery(String sqlString, String resultSetMapping) {
		JpaResultSetMapping jpaResultSetMapping = resultSetMappings.get(resultSetMapping);
		return new JpaNativeQuery(database, sqlString, jpaResultSetMapping);
	}

	public final StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		Query query = namedQueries.get(name);
		if (!(query instanceof StoredProcedureQuery)) {
			throw new PersistenceException("The name '" + name + "' is not associate to StoredProcedureQuery");
		}
		return (StoredProcedureQuery) query;
	}

	public final StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return new JpaStoredProcedureQuery(database, procedureName);
	}

	public final StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
		return new JpaStoredProcedureQuery(database, procedureName, resultClasses);
	}

	public final StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
		return new JpaStoredProcedureQuery(database, procedureName, resultSetMappings);
	}

	public final void joinTransaction() {
		throw new UnsupportedOperationException("joinTransaction()");
	}

	public final boolean isJoinedToTransaction() {
		return joinedTransaction;
	}

	public final <T> T unwrap(Class<T> cls) {
		if (cls.equals(JpaEntityManager.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible unwrap to " + cls.getName());
	}

	public final Object getDelegate() {
		return this;
	}

	public final void close() {
		if (database != null) {
			database.close();
		}
		if (managerFactory != null) {
			managerFactory.close();
		}
		if (properties != null) {
			properties.clear();
		}
		closed = false;
	}

	public final boolean isOpen() {
		return !closed;
	}

	public final EntityTransaction getTransaction() {
		return entityTransaction;
	}

	public final EntityManagerFactory getEntityManagerFactory() {
		return managerFactory;
	}

	public final CriteriaBuilder getCriteriaBuilder() {
		return managerFactory.getCriteriaBuilder();
	}

	public final Metamodel getMetamodel() {
		return managerFactory.getMetamodel();
	}

	public final <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		return new JpaEntityGraph<T>(rootType);
	}

	public final EntityGraph<?> createEntityGraph(String graphName) {
		return new JpaEntityGraph<Object>(graphName);
	}

	public final EntityGraph<?> getEntityGraph(String graphName) {
		return namedEntityGraphs.get(graphName);
	}

	public final <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		List<EntityGraph<? super T>> graphs = new LinkedList<EntityGraph<? super T>>();
		for (EntityGraph<?> entityGraph : namedEntityGraphs.values()) {
			if (entityGraph instanceof JpaEntityGraph) {
				JpaEntityGraph<T> logicEntityGraph = (JpaEntityGraph<T>) entityGraph;
				if (logicEntityGraph.getClassType().equals(entityClass)) {
					graphs.add((EntityGraph<? super T>) logicEntityGraph);
				}
			}
		}
		return graphs;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (closed ? 1231 : 1237);
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntityManager other = (AbstractEntityManager) obj;
		if (closed != other.closed)
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties)) {
			return false;
		}
		return true;
	}

}
