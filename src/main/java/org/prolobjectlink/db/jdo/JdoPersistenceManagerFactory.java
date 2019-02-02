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
package org.prolobjectlink.db.jdo;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.datastore.DataStoreCache;
import javax.jdo.listener.InstanceLifecycleListener;
import javax.jdo.metadata.ComponentMetadata;
import javax.jdo.metadata.JDOMetadata;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManagerFactory;

public class JdoPersistenceManagerFactory extends AbstractEntityManagerFactory implements PersistenceManagerFactory {

	private static final long serialVersionUID = -8268233969545842471L;

	public JdoPersistenceManagerFactory(DatabaseEngine database, Map<Object, Object> properties) {
		super(database);
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PersistenceManager getPersistenceManager() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManagerProxy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PersistenceManager getPersistenceManager(String userid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionUserName(String userName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionUserName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionPassword(String password) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnectionURL(String url) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionDriverName(String driverName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionDriverName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionFactoryName(String connectionFactoryName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionFactoryName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionFactory(Object connectionFactory) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getConnectionFactory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionFactory2Name(String connectionFactoryName) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getConnectionFactory2Name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConnectionFactory2(Object connectionFactory) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getConnectionFactory2() {
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
	public void setMapping(String mapping) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getMapping() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOptimistic(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getOptimistic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRetainValues(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getRetainValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setRestoreValues(boolean restoreValues) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getRestoreValues() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNontransactionalRead(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getNontransactionalRead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setNontransactionalWrite(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getNontransactionalWrite() {
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
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPersistenceUnitName(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getPersistenceUnitName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setServerTimeZoneID(String timezoneid) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getServerTimeZoneID() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransactionType(String name) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTransactionType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setReadOnly(boolean flag) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getTransactionIsolationLevel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransactionIsolationLevel(String level) {
		// TODO Auto-generated method stub

	}

	@Override
	public Properties getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> supportedOptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataStoreCache getDataStoreCache() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addInstanceLifecycleListener(InstanceLifecycleListener listener, Class[] classes) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeInstanceLifecycleListener(InstanceLifecycleListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFetchGroups(FetchGroup... groups) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeFetchGroups(FetchGroup... groups) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeAllFetchGroups() {
		// TODO Auto-generated method stub

	}

	@Override
	public FetchGroup getFetchGroup(Class cls, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set getFetchGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComponentMetadata getMetadata(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getQueryTimeoutMillis() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public JDOMetadata newMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void registerMetadata(JDOMetadata arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setQueryTimeoutMillis(int arg0) {
		// TODO Auto-generated method stub

	}

}
