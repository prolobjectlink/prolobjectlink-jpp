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

import java.util.Map;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Type;
import javax.persistence.metamodel.Type.PersistenceType;

public final class JPAMapAttribute<X, K, V> extends JPAPluralAttribute<X, Map<K, V>, V>
		implements MapAttribute<X, K, V> {

	private final Type<K> keyType;

	public JPAMapAttribute(String name, Type<Map<K, V>> type, ManagedType<X> managedType, Type<V> elementType,
			PersistentAttributeType attributeType, Type<K> keyType) {
		super(name, type, managedType, elementType, attributeType);
		this.keyType = keyType;
	}

	public Class<K> getKeyJavaType() {
		return keyType.getJavaType();
	}

	public Type<K> getKeyType() {
		return keyType;
	}

	public CollectionType getCollectionType() {
		return CollectionType.MAP;
	}

	@Override
	public boolean isAssociation() {
		return

		(getElementType().getPersistenceType() == PersistenceType.ENTITY)

				||

				(getKeyType().getPersistenceType() == PersistenceType.ENTITY);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((keyType == null) ? 0 : keyType.hashCode());
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
		JPAMapAttribute<?, ?, ?> other = (JPAMapAttribute<?, ?, ?>) obj;
		if (keyType == null) {
			if (other.keyType != null)
				return false;
		} else if (!keyType.equals(other.keyType))
			return false;
		return true;
	}

}
