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
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Attribute.PersistentAttributeType;
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

	public abstract CollectionType getCollectionType();

	@Override
	public PersistentAttributeType getPersistentAttributeType() {
		return attributeType;
	}

	public Type<E> getElementType() {
		return elementType;
	}

	@Override
	public boolean isAssociation() {
		return getElementType().getPersistenceType() == PersistenceType.ENTITY;
	}

	@Override
	public boolean isCollection() {
		return true;
	}

}
