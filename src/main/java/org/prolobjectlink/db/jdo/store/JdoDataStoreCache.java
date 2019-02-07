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
package org.prolobjectlink.db.jdo.store;

import java.util.Collection;

import javax.jdo.datastore.DataStoreCache;
import javax.persistence.PersistenceUnitUtil;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.jpa.JpaAbstractContainer;

public class JdoDataStoreCache extends JpaAbstractContainer implements DataStoreCache {

	// TODO implement a cache map and release database use

	JdoDataStoreCache(DatabaseEngine database, PersistenceUnitUtil persistenceUnitUtil) {
		super(database, persistenceUnitUtil);
	}

	@Override
	public void evict(Object oid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll(Object... oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll(Collection oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll(Class pcClass, boolean subclasses) {
		// TODO Auto-generated method stub

	}

	@Override
	public void evictAll(boolean subclasses, Class pcClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pin(Object oid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pinAll(Collection oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pinAll(Object... oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pinAll(Class pcClass, boolean subclasses) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pinAll(boolean subclasses, Class pcClass) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpin(Object oid) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpinAll(Collection oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpinAll(Object... oids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpinAll(Class pcClass, boolean subclasses) {
		// TODO Auto-generated method stub

	}

	@Override
	public void unpinAll(boolean subclasses, Class pcClass) {
		// TODO Auto-generated method stub

	}

}
