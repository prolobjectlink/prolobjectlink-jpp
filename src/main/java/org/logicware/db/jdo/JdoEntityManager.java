/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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
package org.logicware.db.jdo;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.SynchronizationType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.logicware.db.DatabaseEngine;
import org.logicware.db.jpa.JpaEntityTransaction;
import org.logicware.db.jpa.JpaResultSetMapping;

public class JdoEntityManager extends JdoPersistenceManager implements EntityManager {

	private boolean closed;

	// property key-value map
	private final Properties properties;

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

	public JdoEntityManager(DatabaseEngine database, JdoEntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Properties properties, Map<String, Class<?>> entityMap,
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
	}

	@Override
	public void persist(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T merge(T entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public FlushModeType getFlushMode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		// TODO Auto-generated method stub

	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void detach(Object entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean contains(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LockModeType getLockMode(Object entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Object> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createQuery(String qlString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createQuery(CriteriaUpdate updateQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createQuery(CriteriaDelete deleteQuery) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNamedQuery(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void joinTransaction() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isJoinedToTransaction() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDelegate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isOpen() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EntityTransaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Metamodel getMetamodel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityGraph<?> createEntityGraph(String graphName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntityGraph<?> getEntityGraph(String graphName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

}
