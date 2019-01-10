/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.jpa.metamodel;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public final class JpaSingularAttribute<X, Y> extends JpaAttribute<X, Y> implements SingularAttribute<X, Y> {

	private boolean id;
	private boolean version;

	protected JpaSingularAttribute(ManagedType<X> ownerType, String name, Type<Y> type) {
		super(ownerType, name, type);
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

	public boolean isAssociation() {
		return getType().getPersistenceType() == PersistenceType.ENTITY;
	}

	public boolean isCollection() {
		return false;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (id ? 1231 : 1237);
		result = prime * result + (version ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaSingularAttribute<?, ?> other = (JpaSingularAttribute<?, ?>) obj;
		if (id != other.id)
			return false;
		return version == other.version;
	}

}
