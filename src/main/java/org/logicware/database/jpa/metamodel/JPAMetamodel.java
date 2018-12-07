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
package org.logicware.database.jpa.metamodel;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.Type;

public final class JPAMetamodel implements Metamodel {

	private final Map<Class<?>, Type<?>> types;
	private final Map<Class<?>, EntityType<?>> entities;
	private final Map<Class<?>, ManagedType<?>> managedTypes;
	private final Map<Class<?>, EmbeddableType<?>> embeddables;
	private final Set<MappedSuperclassType<?>> mappedSuperclasses;

	public JPAMetamodel(Map<Class<?>, EntityType<?>> entities, Map<Class<?>, EmbeddableType<?>> embeddables,
			Map<Class<?>, ManagedType<?>> managedTypes, Map<Class<?>, Type<?>> types,
			Set<MappedSuperclassType<?>> mappedSuperclasses) {
		this.mappedSuperclasses = mappedSuperclasses;
		this.managedTypes = managedTypes;
		this.embeddables = embeddables;
		this.entities = entities;
		this.types = types;
	}

	public <X> EntityType<X> entity(Class<X> cls) {
		EntityType<?> entityType = entities.get(cls);
		if (entityType != null && entityType.getJavaType() == cls) {
			return (EntityType<X>) entityType;
		}
		throw new IllegalArgumentException("No entity type associated to " + cls);
	}

	public <X> ManagedType<X> managedType(Class<X> cls) {
		ManagedType<?> managedType = entities.get(cls);
		if (managedType != null && managedType.getJavaType() == cls) {
			return (ManagedType<X>) managedType;
		}
		throw new IllegalArgumentException("No manage type associated to " + cls);
	}

	public <X> EmbeddableType<X> embeddable(Class<X> cls) {
		EmbeddableType<?> embeddableType = embeddables.get(cls);
		if (embeddableType != null && embeddableType.getJavaType() == cls) {
			return (EmbeddableType<X>) embeddableType;
		}
		throw new IllegalArgumentException("No embeddable type associated to " + cls);
	}

	public Set<ManagedType<?>> getManagedTypes() {
		Set<Class<?>> keySet = managedTypes.keySet();
		Set<ManagedType<?>> set = new LinkedHashSet<ManagedType<?>>(keySet.size());
		for (Class<?> clazz : keySet) {
			set.add(managedTypes.get(clazz));
		}
		return set;
	}

	public Set<EntityType<?>> getEntities() {
		Set<Class<?>> keySet = entities.keySet();
		Set<EntityType<?>> set = new LinkedHashSet<EntityType<?>>(keySet.size());
		for (Class<?> clazz : keySet) {
			set.add(entities.get(clazz));
		}
		return set;
	}

	public Set<EmbeddableType<?>> getEmbeddables() {
		Set<Class<?>> keySet = embeddables.keySet();
		Set<EmbeddableType<?>> set = new LinkedHashSet<EmbeddableType<?>>(keySet.size());
		for (Class<?> clazz : keySet) {
			set.add(embeddables.get(clazz));
		}
		return set;
	}

}
