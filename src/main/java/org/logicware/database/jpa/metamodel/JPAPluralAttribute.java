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
package org.logicware.database.jpa.metamodel;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public abstract class JPAPluralAttribute<X, C, E> extends JPAAttribute<X, C> implements PluralAttribute<X, C, E> {

	protected final Type<E> elementType;
	protected final PersistentAttributeType attributeType;

	protected JPAPluralAttribute(String name, Type<C> type, ManagedType<X> managedType, Type<E> elementType,
			PersistentAttributeType attributeType) {
		super(name, type, managedType);
		this.elementType = elementType;
		this.attributeType = attributeType;
	}

	public BindableType getBindableType() {
		return BindableType.PLURAL_ATTRIBUTE;
	}

	public Class<E> getBindableJavaType() {
		return elementType.getJavaType();
	}

	public PersistentAttributeType getPersistentAttributeType() {
		return attributeType;
	}

	public Type<E> getElementType() {
		return elementType;
	}

	public boolean isAssociation() {
		return getElementType().getPersistenceType() == PersistenceType.ENTITY;
	}

	public boolean isCollection() {
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((attributeType == null) ? 0 : attributeType.hashCode());
		result = prime * result + ((elementType == null) ? 0 : elementType.hashCode());
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
		JPAPluralAttribute<?, ?, ?> other = (JPAPluralAttribute<?, ?, ?>) obj;
		if (attributeType != other.attributeType)
			return false;
		if (elementType == null) {
			if (other.elementType != null)
				return false;
		} else if (!elementType.equals(other.elementType))
			return false;
		return true;
	}

}
