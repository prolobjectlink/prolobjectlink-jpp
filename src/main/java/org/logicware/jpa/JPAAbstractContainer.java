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
package org.logicware.jpa;

import java.util.List;

import javax.persistence.PersistenceUnitUtil;

import org.logicware.pdb.DatabaseService;

public abstract class JPAAbstractContainer {

	//
	protected final DatabaseService database;

	//
	protected final PersistenceUnitUtil persistenceUnitUtil;

	protected JPAAbstractContainer(DatabaseService database, PersistenceUnitUtil persistenceUnitUtil) {
		this.persistenceUnitUtil = persistenceUnitUtil;
		this.database = database;
	}

	protected final <O> List<O> findAll(Class<O> clazz) {
		return database.createQuery(clazz).getSolutions();
	}

}
