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
package org.logicware.db.jpa;

import javax.persistence.Cache;
import javax.persistence.PersistenceUnitUtil;

public final class JpaCache implements Cache {

	// TODO implement a cache map and release database use

	public JpaCache(PersistenceUnitUtil persistenceUnitUtil) {
		// TODO Auto-generated method stub
	}

	void add(Object object) {
		// TODO Auto-generated method stub
	}

	public boolean contains(Class cls, Object primaryKey) {
		// TODO Auto-generated method stub
		return true;
	}

	public void evict(Class cls, Object primaryKey) {
		// TODO Auto-generated method stub
	}

	public void evict(Class cls) {
		// TODO Auto-generated method stub
	}

	public void evictAll() {
		// TODO Auto-generated method stub
	}

	public <T> T unwrap(Class<T> cls) {

		return null;
	}

}
