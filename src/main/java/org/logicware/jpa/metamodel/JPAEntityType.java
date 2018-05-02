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
package org.logicware.jpa.metamodel;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Type;

public final class JPAEntityType<X> extends JPABindableType<X> implements EntityType<X> {

	private final String name;

	public JPAEntityType(Class<X> javaType, String name, Type<?> idType) {
		super(javaType, idType);
		this.name = name;
	}

	public PersistenceType getPersistenceType() {
		return PersistenceType.ENTITY;
	}

	public BindableType getBindableType() {
		return BindableType.ENTITY_TYPE;
	}

	public String getName() {
		return name;
	}

}
