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
package org.worklogic.db.jpa;

import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;

import org.worklogic.db.DatabaseEngine;
import org.worklogic.db.common.AbstractEntityManager;

public final class JpaEntityManager extends AbstractEntityManager implements EntityManager {

	public JpaEntityManager(DatabaseEngine database, EntityManagerFactory managerFactory,
			SynchronizationType synchronizationType, Map properties, Map<String, Class<?>> entityMap,
			Map<String, Query> namedQueries, Map<String, EntityGraph<?>> namedEntityGraphs,
			Map<String, JpaResultSetMapping> resultSetMappings) {
		super(database, managerFactory, synchronizationType, properties, entityMap, namedQueries, namedEntityGraphs,
				resultSetMappings);
	}

}
