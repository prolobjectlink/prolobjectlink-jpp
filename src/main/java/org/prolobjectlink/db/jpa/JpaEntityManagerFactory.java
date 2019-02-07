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
package org.prolobjectlink.db.jpa;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.SynchronizationType;

import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.common.AbstractEntityManagerFactory;

/**
 * EntityManagerFactory implementation
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public final class JpaEntityManagerFactory extends AbstractEntityManagerFactory implements EntityManagerFactory {

	// property key-value map
	protected Map<String, Object> properties = new HashMap<String, Object>();

	public JpaEntityManagerFactory(DatabaseEngine database, Map<Object, Object> map) {
		super(database);
		for (Entry<Object, Object> e : map.entrySet()) {
			this.properties.put("" + e.getKey() + "", e.getValue());
		}
	}

	@Override
	public Map<String, Object> getProperties() {
		return properties;
	}

	public EntityManager createEntityManager() {
		return createEntityManager(properties);
	}

	public EntityManager createEntityManager(Map map) {
		return createEntityManager(SynchronizationType.SYNCHRONIZED, properties);
	}

	public EntityManager createEntityManager(SynchronizationType synchronizationType) {
		return createEntityManager(synchronizationType, properties);
	}

	public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {

		// TODO create database here

		return new JpaEntityManager(database, this, synchronizationType, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

}
