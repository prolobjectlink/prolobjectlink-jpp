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

import java.util.List;

import javax.persistence.PersistenceUnitUtil;

import org.logicware.db.DatabaseEngine;

public abstract class JpaAbstractContainer {

	//
	protected final DatabaseEngine database;

	//
	protected final PersistenceUnitUtil persistenceUnitUtil;

	protected JpaAbstractContainer(DatabaseEngine database, PersistenceUnitUtil persistenceUnitUtil) {
		this.persistenceUnitUtil = persistenceUnitUtil;
		this.database = database;
	}

	protected final <O> List<O> findAll(Class<O> clazz) {
		return database.createQuery(clazz).getSolutions();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((database == null) ? 0 : database.hashCode());
		result = prime * result + ((persistenceUnitUtil == null) ? 0 : persistenceUnitUtil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaAbstractContainer other = (JpaAbstractContainer) obj;
		if (database == null) {
			if (other.database != null)
				return false;
		} else if (!database.equals(other.database)) {
			return false;
		}
		if (persistenceUnitUtil == null) {
			if (other.persistenceUnitUtil != null)
				return false;
		} else if (!persistenceUnitUtil.equals(other.persistenceUnitUtil)) {
			return false;
		}
		return true;
	}

}
