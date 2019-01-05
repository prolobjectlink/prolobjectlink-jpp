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
package org.logicware.db.common;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.AttributeConverter;
import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.logicware.db.DatabaseEngine;
import org.logicware.db.Schema;
import org.logicware.db.jpa.JpaAttributeConverter;
import org.logicware.db.jpa.JpaCache;
import org.logicware.db.jpa.JpaEntityManagerFactory;
import org.logicware.db.jpa.JpaPersistenceUnitUtil;
import org.logicware.db.jpa.JpaResultSetMapping;
import org.logicware.db.jpa.criteria.JpaCriteriaBuilder;
import org.logicware.db.jpa.metamodel.JpaMetamodel;
import org.logicware.prolog.PrologTerm;

public abstract class AbstractEntityManagerFactory {

	// second level cache
	protected Cache cache;

	//
	protected final DatabaseEngine database;

	//
	protected final PersistenceUnitUtil persistenceUnitUtil;

	// user defined names for entities
	protected final Map<String, Class<?>> entityMap = new LinkedHashMap<String, Class<?>>();

	// user defined named queries container
	protected final Map<String, Query> namedQueries = new LinkedHashMap<String, Query>();

	// user defined named entity graphs container
	protected final Map<String, EntityGraph<?>> namedEntityGraphs = new LinkedHashMap<String, EntityGraph<?>>();

	// result set mappings for native queries result
	protected final Map<String, JpaResultSetMapping> resultSetMappings = new LinkedHashMap<String, JpaResultSetMapping>();

	public AbstractEntityManagerFactory(DatabaseEngine database) {
		this.persistenceUnitUtil = new JpaPersistenceUnitUtil();
		this.cache = new JpaCache(database, persistenceUnitUtil);
		this.database = database;
//		this.database.begin();
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return new JpaCriteriaBuilder(getMetamodel());
	}

	public Metamodel getMetamodel() {
		Schema s = database.getSchema();
		return new JpaMetamodel(s);
	}

	public boolean isOpen() {
		return database.isOpen();
	}

	public void close() {
		namedQueries.clear();
		if (database != null) {
			database.clear();
			database.close();
		}
		if (cache != null) {
			cache.evictAll();
		}
	}

	public Cache getCache() {
		return cache;
	}

	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return persistenceUnitUtil;
	}

	public void addNamedQuery(String name, Query query) {
		namedQueries.put(name, query);
	}

	public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
		namedEntityGraphs.put(graphName, entityGraph);
	}

	public <T> T unwrap(Class<T> cls) {
		if (cls.equals(JpaEntityManagerFactory.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible  unwrap " + cls.getName());
	}

	public void addEntity(String name, Class<?> clazz) {
		entityMap.put(name, clazz);
	}

	public void addResultSetMapping(JpaResultSetMapping mapping) {
		resultSetMappings.put(mapping.getName(), mapping);
	}

	public AttributeConverter<Object, PrologTerm> getAttributeConverter() {
		return new JpaAttributeConverter(database.getProvider());
	}

	public final Map<String, EntityGraph<?>> getNamedEntityGraphs() {
		return namedEntityGraphs;
	}

	public final Map<String, Query> getNamedQueries() {
		return namedQueries;
	}

}
