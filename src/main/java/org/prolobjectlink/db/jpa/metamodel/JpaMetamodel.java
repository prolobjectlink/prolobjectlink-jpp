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
package org.prolobjectlink.db.jpa.metamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.EmbeddableType;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MappedSuperclassType;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.Schema;

public final class JpaMetamodel implements Metamodel {

	private final Schema schema;

	public JpaMetamodel(Schema schema) {
		this.schema = schema;
	}

	public <X> EntityType<X> entity(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertEntityAnnotatedClass(cls);
		return new JpaEntityType<X>(schema, c);
	}

	public <X> ManagedType<X> managedType(Class<X> cls) {
		ManagedType<X> managedType = null;
		if (cls.isAnnotationPresent(MappedSuperclass.class)) {
			managedType = mappedSuperclass(cls);
		} else if (cls.isAnnotationPresent(Embeddable.class)) {
			managedType = embeddable(cls);
		} else if (cls.isAnnotationPresent(Entity.class)) {
			managedType = entity(cls);
		}
		return managedType;
	}

	public <X> MappedSuperclassType<X> mappedSuperclass(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertMappedSuperclassAnnotatedClass(cls);
		return new JpaMappedSuperclassType<X>(schema, c);
	}

	public <X> EmbeddableType<X> embeddable(Class<X> cls) {
		DatabaseClass c = schema.getClass(cls);
		assertEmbeddableAnnotatedClass(cls);
		return new JpaEmbeddableType<X>(schema, c);
	}

	public Set<ManagedType<?>> getManagedTypes() {
		Set<ManagedType<?>> types = new HashSet<ManagedType<?>>();
		types.addAll(getEmbeddables());
		types.addAll(getEntities());
		return types;
	}

	public Set<EntityType<?>> getEntities() {
		Set<EntityType<?>> entities = new HashSet<EntityType<?>>();
		for (DatabaseClass cls : schema.getClasses()) {
			if (cls.getJavaClass().isAnnotationPresent(Entity.class)) {
				EntityType<?> e = new JpaEntityType(schema, cls);
				entities.add(e);
			}
		}
		return entities;
	}

	public Set<EmbeddableType<?>> getEmbeddables() {
		Set<EmbeddableType<?>> embeddables = new HashSet<EmbeddableType<?>>();
		for (DatabaseClass cls : schema.getClasses()) {
			if (cls.getJavaClass().isAnnotationPresent(Embeddable.class)) {
				EmbeddableType<?> e = new JpaEmbeddableType(schema, cls);
				embeddables.add(e);
			}
		}
		return embeddables;
	}

	private void assertEmbeddableAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(Embeddable.class)) {
			throw new PersistenceException("No @Embeddable annotated class " + cls);
		}
	}

	private void assertEntityAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(Entity.class)) {
			throw new PersistenceException("No @Entity annotated class " + cls);
		}
	}

	private void assertMappedSuperclassAnnotatedClass(Class<?> cls) {
		if (!cls.isAnnotationPresent(MappedSuperclass.class)) {
			throw new PersistenceException("No @MappedSuperclass annotated class " + cls);
		}
	}

}
