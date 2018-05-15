/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.jpa;

import java.util.List;

import javax.persistence.Cache;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;

import org.logicware.pdb.DatabaseService;

public final class JPACache extends JPAAbstractContainer implements Cache {

	// TODO implement a cache map and release database use

	JPACache(DatabaseService database, PersistenceUnitUtil persistenceUnitUtil) {
		super(database, persistenceUnitUtil);
	}

	void add(Object object) {
		if (object != null) {
			database.insert(object);
		}
	}

	public boolean contains(Class cls, Object primaryKey) {
		List<Object> list = findAll(cls);
		for (Object t : list) {
			Object id = persistenceUnitUtil.getIdentifier(t);
			if (primaryKey.equals(id)) {
				return true;
			}
		}
		return false;
	}

	public void evict(Class cls, Object primaryKey) {
		List<Object> list = findAll(cls);
		for (Object t : list) {
			Object id = persistenceUnitUtil.getIdentifier(t);
			if (primaryKey.equals(id)) {
				database.delete(t);
			}
		}
	}

	public void evict(Class cls) {
		database.delete(cls);
	}

	public void evictAll() {
		database.clear();
	}

	public <T> T unwrap(Class<T> cls) {
		if (cls.equals(JPACache.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible unwrap to " + cls.getName());
	}

}
