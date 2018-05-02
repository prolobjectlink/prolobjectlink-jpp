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

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public final class JPASingularAttribute<X, Y> extends JPAAttribute<X, Y> implements SingularAttribute<X, Y> {

	private boolean id;
	private boolean version;

	public JPASingularAttribute(String name, Type<Y> javaType, ManagedType<X> managedType) {
		super(name, javaType, managedType);
	}

	public BindableType getBindableType() {
		return BindableType.SINGULAR_ATTRIBUTE;
	}

	public Class<Y> getBindableJavaType() {
		return getJavaType();
	}

	public boolean isId() {
		return id;
	}

	public boolean isVersion() {
		return version;
	}

	public boolean isOptional() {
		return !(getType() instanceof ManagedType);
	}

	public Type<Y> getType() {
		return type;
	}

	@Override
	public boolean isAssociation() {
		return getType().getPersistenceType() == PersistenceType.ENTITY;
	}

	@Override
	public boolean isCollection() {
		return false;
	}

	@Override
	public PersistentAttributeType getPersistentAttributeType() {
		if (getType().getPersistenceType() == PersistenceType.BASIC) {
			return PersistentAttributeType.BASIC;
		}
		return PersistentAttributeType.ONE_TO_ONE;
	}

	public void setId(boolean id) {
		this.id = id;
	}

	public void setVersion(boolean version) {
		this.version = version;
	}

	public void markLikeId() {
		setId(true);
	}

	public void markLikeVersion() {
		setVersion(true);
	}

}
