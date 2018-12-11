/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeConverter;
import javax.persistence.Cache;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PersistenceException;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.Version;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;

import org.logicware.database.DatabaseEngine;
import org.logicware.database.jpa.criteria.JpaCriteriaBuilder;
import org.logicware.database.jpa.metamodel.JPAAttribute;
import org.logicware.database.jpa.metamodel.JPABasicType;
import org.logicware.database.jpa.metamodel.JPACollectionAttribute;
import org.logicware.database.jpa.metamodel.JPAEmbeddableType;
import org.logicware.database.jpa.metamodel.JPAEntityType;
import org.logicware.database.jpa.metamodel.JPAIdentifiableType;
import org.logicware.database.jpa.metamodel.JPAListAttribute;
import org.logicware.database.jpa.metamodel.JPAManagedType;
import org.logicware.database.jpa.metamodel.JPAMapAttribute;
import org.logicware.database.jpa.metamodel.JPAMappedSuperclassType;
import org.logicware.database.jpa.metamodel.JPAMetamodel;
import org.logicware.database.jpa.metamodel.JPASetAttribute;
import org.logicware.database.jpa.metamodel.JPASingularAttribute;
import org.logicware.database.util.JavaReflect;
import org.logicware.prolog.PrologTerm;

/**
 * EntityManagerFactory implementation
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public final class JPAEntityManagerFactory implements EntityManagerFactory {

	// second level cache
	private Cache cache;

	//
	private final DatabaseEngine database;

	// property key-value map
	private Map<String, Object> properties;

	//
	private final PersistenceUnitUtil persistenceUnitUtil;

	// user defined names for entities
	private final Map<String, Class<?>> entityMap = new LinkedHashMap<String, Class<?>>();

	// user defined named queries container
	private final Map<String, Query> namedQueries = new LinkedHashMap<String, Query>();

	// user defined named entity graphs container
	private final Map<String, EntityGraph<?>> namedEntityGraphs = new LinkedHashMap<String, EntityGraph<?>>();

	// result set mappings for native queries result
	private final Map<String, JPAResultSetMapping> resultSetMappings = new LinkedHashMap<String, JPAResultSetMapping>();

	public JPAEntityManagerFactory(DatabaseEngine database) {
		this.persistenceUnitUtil = new JPAPersistenceUnitUtil();
		this.cache = new JPACache(database, persistenceUnitUtil);
		this.database = database;
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
		return new JPAEntityManager(database, this, synchronizationType, map, entityMap, namedQueries,
				namedEntityGraphs, resultSetMappings);
	}

	public CriteriaBuilder getCriteriaBuilder() {
		return new JpaCriteriaBuilder(getMetamodel());
	}

	public Metamodel getMetamodel() {
		Map<Class<?>, Type<?>> types = new LinkedHashMap<Class<?>, Type<?>>();
		Map<Class<?>, EntityType<?>> entities = new LinkedHashMap<Class<?>, EntityType<?>>();
		Map<Class<?>, ManagedType<?>> managedTypes = new LinkedHashMap<Class<?>, ManagedType<?>>();
		Map<Class<?>, EmbeddableType<?>> embeddables = new LinkedHashMap<Class<?>, EmbeddableType<?>>();
		Set<MappedSuperclassType<?>> mappedSuperclasses = new LinkedHashSet<MappedSuperclassType<?>>();
		for (Class<?> entityClass : database.getSchema().getJavaClasses()) {
			getType(entityClass, types, entities, managedTypes, embeddables, mappedSuperclasses);
		}
		return new JPAMetamodel(entities, embeddables, managedTypes, types, mappedSuperclasses);
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
		if (properties != null) {
			properties.clear();
		}
		if (cache != null) {
			cache.evictAll();
		}
	}

	public Map<String, Object> getProperties() {
		return properties;
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
		if (cls.equals(JPAEntityManagerFactory.class)) {
			return (T) this;
		}
		throw new PersistenceException("Impossible  unwrap " + cls.getName());
	}

	public void addEntity(String name, Class<?> clazz) {
		entityMap.put(name, clazz);
	}

	public void addResultSetMapping(JPAResultSetMapping mapping) {
		resultSetMappings.put(mapping.getName(), mapping);
	}

	public AttributeConverter<Object, PrologTerm> getAttributeConverter() {
		return new JPAAttributeConverter(database.getProvider());
	}

	private Type<?> getType(Class<?> type, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		if (type.isAnnotationPresent(Entity.class)) {
			return getEntityType(type, types, entities, managedTypes, embeddables, mappedSuperclasses);
		} else if (type.isAnnotationPresent(Embeddable.class)) {
			return getEmbeddableType(type, types, entities, managedTypes, embeddables, mappedSuperclasses);
		} else if (type.isAnnotationPresent(MappedSuperclass.class)) {
			return getMappedSupperClassType(type, types, entities, managedTypes, embeddables, mappedSuperclasses);
		}
		return getBasicType(type, types);
	}

	private Type<?> getMappedSupperClassType(Class<?> type, Map<Class<?>, Type<?>> types,
			Map<Class<?>, EntityType<?>> entities, Map<Class<?>, ManagedType<?>> managedTypes,
			Map<Class<?>, EmbeddableType<?>> embeddables, Set<MappedSuperclassType<?>> mappedSuperclasses) {

		JPAMappedSuperclassType<?> mappedSuperclassType = new JPAMappedSuperclassType(type,
				getIdType(type, types, entities, managedTypes, embeddables, mappedSuperclasses));
		addManagedTypeAttributes(mappedSuperclassType, type, types, entities, managedTypes, embeddables,
				mappedSuperclasses);
		managedTypes.put(type, mappedSuperclassType);
		mappedSuperclasses.add(mappedSuperclassType);
		types.put(type, mappedSuperclassType);
		return mappedSuperclassType;

	}

	private Type<?> getEmbeddableType(Class<?> type, Map<Class<?>, Type<?>> types,
			Map<Class<?>, EntityType<?>> entities, Map<Class<?>, ManagedType<?>> managedTypes,
			Map<Class<?>, EmbeddableType<?>> embeddables, Set<MappedSuperclassType<?>> mappedSuperclasses) {

		JPAEmbeddableType<?> embeddableType = new JPAEmbeddableType(type);
		addManagedTypeAttributes(embeddableType, type, types, entities, managedTypes, embeddables, mappedSuperclasses);
		managedTypes.put(type, embeddableType);
		embeddables.put(type, embeddableType);
		types.put(type, embeddableType);
		return embeddableType;

	}

	private Type<?> getEntityType(Class<?> type, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {

		String name = getEntityName(type);
		JPAEntityType<?> entityType = new JPAEntityType(type, name,
				getIdType(type, types, entities, managedTypes, embeddables, mappedSuperclasses));
		addManagedTypeAttributes(entityType, type, types, entities, managedTypes, embeddables, mappedSuperclasses);
		managedTypes.put(type, entityType);
		entities.put(type, entityType);
		types.put(type, entityType);
		return entityType;

	}

	private String getEntityName(Class<?> type) {
		String name = type.getAnnotation(Entity.class).name();
		return name.isEmpty() ? type.getSimpleName() : name;
	}

	private Type<?> getBasicType(Class<?> type, Map<Class<?>, Type<?>> types) {
		JPABasicType<?> basicType = new JPABasicType(type);
		types.put(type, basicType);
		return basicType;
	}

	private void addManagedTypeAttributes(JPAManagedType<?> managedType, Class<?> type, Map<Class<?>, Type<?>> types,
			Map<Class<?>, EntityType<?>> entities, Map<Class<?>, ManagedType<?>> managedTypes,
			Map<Class<?>, EmbeddableType<?>> embeddables, Set<MappedSuperclassType<?>> mappedSuperclasses) {

		Field[] managedTypeFields = type.getDeclaredFields();
		for (Field field : managedTypeFields) {
			Attribute attribute = getAttribute(field, managedType, types, entities, managedTypes, embeddables,
					mappedSuperclasses);
			managedType.addAttribute(field.getName(), attribute);
			if (managedType instanceof JPAIdentifiableType) {
				JPAIdentifiableType identifiableType = (JPAIdentifiableType) managedType;
				markIdentifiableAttributes(field, attribute, identifiableType);
			}
		}

	}

	private void markIdentifiableAttributes(Field field, Attribute attribute, JPAIdentifiableType identifiableType) {
		if (field.isAnnotationPresent(Id.class) || field.isAnnotationPresent(EmbeddedId.class)) {
			if (!attribute.isCollection()) {
				JPASingularAttribute singularAttribute = (JPASingularAttribute) attribute;
				identifiableType.addIdAttribute(singularAttribute);
				singularAttribute.markLikeId();
			}
		}

		if (field.isAnnotationPresent(Version.class)) {
			if (!attribute.isCollection()) {
				JPASingularAttribute singularAttribute = (JPASingularAttribute) attribute;
				identifiableType.addIdAttribute(singularAttribute);
				singularAttribute.markLikeVersion();
			}
		}
	}

	private JPAAttribute getAttribute(Field field, ManagedType<?> managedType, Map<Class<?>, Type<?>> types,
			Map<Class<?>, EntityType<?>> entities, Map<Class<?>, ManagedType<?>> managedTypes,
			Map<Class<?>, EmbeddableType<?>> embeddables, Set<MappedSuperclassType<?>> mappedSuperclasses) {

		String fieldName = field.getName();
		Class<?> fieldType = field.getType();

		PersistentAttributeType attributeType = getPersistentAttributeType(field);

		if (fieldType == Collection.class) { // collection attribute
			Type<?> elementType = getElementType(field, types, entities, managedTypes, embeddables, mappedSuperclasses);
			return new JPACollectionAttribute(fieldName,
					getType(fieldType, types, entities, managedTypes, embeddables, mappedSuperclasses), managedType,
					elementType, attributeType);
		} else if (fieldType == Set.class) { // set attribute
			Type<?> elementType = getElementType(field, types, entities, managedTypes, embeddables, mappedSuperclasses);
			return new JPASetAttribute(fieldName,
					getType(fieldType, types, entities, managedTypes, embeddables, mappedSuperclasses), managedType,
					elementType, attributeType);
		} else if (fieldType == List.class) { // list attribute
			Type<?> elementType = getElementType(field, types, entities, managedTypes, embeddables, mappedSuperclasses);
			return new JPAListAttribute(fieldName,
					getType(fieldType, types, entities, managedTypes, embeddables, mappedSuperclasses), managedType,
					elementType, attributeType);
		} else if (fieldType == Map.class) { // map attribute
			Type<?> keyType = getKeyType(field, types, entities, managedTypes, embeddables, mappedSuperclasses);
			Type<?> valueType = getValueType(field, types, entities, managedTypes, embeddables, mappedSuperclasses);
			return new JPAMapAttribute(fieldName,
					getType(fieldType, types, entities, managedTypes, embeddables, mappedSuperclasses), managedType,
					valueType, attributeType, keyType);
		}
		return new JPASingularAttribute(fieldName,
				getType(fieldType, types, entities, managedTypes, embeddables, mappedSuperclasses), managedType);
	}

	private PersistentAttributeType getPersistentAttributeType(Field field) {
		if (field.isAnnotationPresent(ElementCollection.class)) {
			return PersistentAttributeType.ELEMENT_COLLECTION;
		} else if (field.isAnnotationPresent(OneToOne.class)) {
			return PersistentAttributeType.ONE_TO_ONE;
		} else if (field.isAnnotationPresent(OneToMany.class)) {
			return PersistentAttributeType.ONE_TO_MANY;
		} else if (field.isAnnotationPresent(ManyToOne.class)) {
			return PersistentAttributeType.MANY_TO_ONE;
		} else if (field.isAnnotationPresent(ManyToMany.class)) {
			return PersistentAttributeType.MANY_TO_MANY;
		}
		return PersistentAttributeType.BASIC;
	}

	private Type<?> getElementType(Field field, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		java.lang.reflect.Type elementType = parameterizedType.getActualTypeArguments()[0];
		Class<?> genericType = JavaReflect.classForName(((Class<?>) elementType).getName());
		return getType(genericType, types, entities, managedTypes, embeddables, mappedSuperclasses);
	}

	private Type<?> getKeyType(Field field, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		java.lang.reflect.Type keyType = parameterizedType.getActualTypeArguments()[0];
		Class<?> genericType = JavaReflect.classForName(((Class<?>) keyType).getName());
		return getType(genericType, types, entities, managedTypes, embeddables, mappedSuperclasses);
	}

	private Type<?> getValueType(Field field, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
		java.lang.reflect.Type elementType = parameterizedType.getActualTypeArguments()[1];
		Class<?> genericType = JavaReflect.classForName(((Class<?>) elementType).getName());
		if (genericType.isAnnotationPresent(Entity.class)) {
			// System.out.println(getEntityName(genericType));
			// System.out.println(getEntityName(field.getDeclaringClass()));
			Class<?> clazz = field.getDeclaringClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field2 : fields) {
				if (field.getDeclaringClass() == field2.getType()) {
					System.out.println(field2);
				}
			}
		}

		return getType(genericType, types, entities, managedTypes, embeddables, mappedSuperclasses);
	}

	private Type<?> getIdType(Class<?> type, Map<Class<?>, Type<?>> types, Map<Class<?>, EntityType<?>> entities,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, EmbeddableType<?>> embeddables,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		Field[] fields = type.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].isAnnotationPresent(Id.class)) {
				return getType(fields[i].getType(), types, entities, managedTypes, embeddables, mappedSuperclasses);
			}
		}
		return null;
		// throw new
		// PersistenceException("No field was annotated with @Id annotation");
	}
}
