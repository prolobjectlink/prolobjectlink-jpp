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
package org.worklogic.db.jdo;

import java.util.Collection;
import java.util.Date;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import javax.jdo.Extent;
import javax.jdo.FetchGroup;
import javax.jdo.FetchPlan;
import javax.jdo.JDOException;
import javax.jdo.ObjectState;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import javax.jdo.datastore.JDOConnection;
import javax.jdo.datastore.Sequence;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SynchronizationType;

import org.worklogic.db.DatabaseEngine;
import org.worklogic.db.common.AbstractEntityManager;
import org.worklogic.db.jpa.JpaResultSetMapping;

public class JdoPersistenceManager extends AbstractEntityManager implements PersistenceManager {

	private final Transaction transaction;

	public JdoPersistenceManager(DatabaseEngine database, EntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, javax.persistence.Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JpaResultSetMapping> resultSetMappings) {
		super(database, managerFactory, synchronizationType, properties, entityMap, namedQueries, namedEntityGraphs,
				resultSetMappings);
		this.transaction = new JdoTransaction(database.getTransaction());

	}

	@Override
	public boolean isClosed() {
		return closed;
	}

	@Override
	public Transaction currentTransaction() {
		return transaction;
	}

	@Override
	public void evict(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll(Object... pcs) {
		for (Object object : pcs) {
			evict(object);
		}
	}

	@Override
	public void evictAll(Collection pcs) {
		for (Object object : pcs) {
			evict(object);
		}
	}

	@Override
	public void evictAll(boolean subclasses, Class pcClass) {
		// TODO Auto-generated method stub
	}

	@Override
	public void evictAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void refreshAll(JDOException jdoe) {
		// TODO Auto-generated method stub

	}

	@Override
	public Query newQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Object compiled) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(String query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(String language, Object query) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Class cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Extent cln) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Class cls, Collection cln) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Class cls, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Class cls, Collection cln, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newQuery(Extent cln, String filter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query newNamedQuery(Class cls, String queryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Extent<T> getExtent(Class<T> persistenceCapableClass, boolean subclasses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Extent<T> getExtent(Class<T> persistenceCapableClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectById(Object oid, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getObjectById(Class<T> cls, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectById(Object oid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getObjectId(Object pc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getTransactionalObjectId(Object pc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object newObjectIdInstance(Class pcClass, Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getObjectsById(Collection oids, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection getObjectsById(Collection oids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getObjectsById(Object[] oids, boolean validate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getObjectsById(boolean validate, Object... oids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getObjectsById(Object... oids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T makePersistent(T pc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] makePersistentAll(T... pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Collection<T> makePersistentAll(Collection<T> pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePersistent(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePersistentAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePersistentAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransient(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransientAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransientAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransient(Object pc, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransientAll(Object[] pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransientAll(boolean useFetchPlan, Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransientAll(Collection pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransactional(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransactionalAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeTransactionalAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeNontransactional(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeNontransactionalAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeNontransactionalAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieve(Object pc) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieve(Object pc, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAll(Collection pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAll(Collection pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAll(Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAll(Object[] pcs, boolean useFetchPlan) {
		// TODO Auto-generated method stub

	}

	@Override
	public void retrieveAll(boolean useFetchPlan, Object... pcs) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUserObject(Object o) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getUserObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManagerFactory getPersistenceManagerFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Class getObjectIdClass(Class cls) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMultithreaded(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getMultithreaded() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIgnoreCache(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getIgnoreCache() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getDetachAllOnCommit() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDetachAllOnCommit(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getCopyOnAttach() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCopyOnAttach(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public <T> T detachCopy(T pc) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Collection<T> detachCopyAll(Collection<T> pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] detachCopyAll(T... pcs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object putUserObject(Object key, Object val) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getUserObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object removeUserObject(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkConsistency() {
		// TODO Auto-generated method stub

	}

	@Override
	public FetchPlan getFetchPlan() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T newInstance(Class<T> pcClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sequence getSequence(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JDOConnection getDataStoreConnection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class... classes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getServerDate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getManagedObjects() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getManagedObjects(EnumSet<ObjectState> states) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getManagedObjects(Class... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getManagedObjects(EnumSet<ObjectState> states, Class... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FetchGroup getFetchGroup(Class cls, String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
