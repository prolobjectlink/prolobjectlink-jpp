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

import javax.persistence.Entity;
import javax.persistence.metamodel.EntityType;

import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.Schema;

public final class JpaEntityType<X> extends JpaIdentifiableType<X> implements EntityType<X> {

	public JpaEntityType(Schema schema, DatabaseClass databaseClass) {
		super(schema, databaseClass);
	}

	public PersistenceType getPersistenceType() {
		return PersistenceType.ENTITY;
	}

	public BindableType getBindableType() {
		return BindableType.ENTITY_TYPE;
	}

	public Class<X> getBindableJavaType() {
		return getJavaType();
	}

	public String getName() {
		Class<?> type = getJavaType();
		String name = type.getAnnotation(Entity.class).name();
		return name.isEmpty() ? type.getSimpleName() : name;
	}

}
