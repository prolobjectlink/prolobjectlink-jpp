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

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.metamodel.IdentifiableType;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.Type;

public abstract class JPAIdentifiableType<X> extends JPAManagedType<X> implements IdentifiableType<X> {

	private final Type<?> idType;
	private IdentifiableType<? super X> superType;
	private SingularAttribute<? super X, ?> versionAttribute;
	private Set<SingularAttribute<? super X, ?>> idAttributes;

	public JPAIdentifiableType(Class<X> javaType, Type<?> idType) {
		super(javaType);
		this.idType = idType;
		idAttributes = new LinkedHashSet<SingularAttribute<? super X, ?>>();
	}

	public <Y> SingularAttribute<? super X, Y> getId(Class<Y> type) {
		return getDeclaredId(type);
	}

	public <Y> SingularAttribute<X, Y> getDeclaredId(Class<Y> type) {
		for (SingularAttribute<? super X, ?> idAttribute : idAttributes) {
			if (idAttribute.getType().getJavaType() == type) {
				return (SingularAttribute<X, Y>) idAttribute;
			}
		}
		throw new IllegalArgumentException("No id attribute of type " + type + " on " + getJavaType());
	}

	public <Y> SingularAttribute<? super X, Y> getVersion(Class<Y> type) {
		return getDeclaredVersion(type);
	}

	public <Y> SingularAttribute<X, Y> getDeclaredVersion(Class<Y> type) {
		if (hasVersionAttribute()) {
			Class<?> javaType = versionAttribute.getType().getJavaType();
			if (javaType == type) {
				return (SingularAttribute<X, Y>) versionAttribute;
			}
		}
		throw new IllegalArgumentException("No version attribute of type " + type + " on " + getJavaType());
	}

	public IdentifiableType<? super X> getSupertype() {
		return superType;
	}

	public boolean hasSingleIdAttribute() {
		return !idAttributes.isEmpty();
	}

	public boolean hasVersionAttribute() {
		return versionAttribute != null;
	}

	public Set<SingularAttribute<? super X, ?>> getIdClassAttributes() {
		return idAttributes;
	}

	public Type<?> getIdType() {
		return idType;
	}

	public void setSuperType(IdentifiableType<? super X> superType) {
		this.superType = superType;
	}

	public void setVersionAttribute(SingularAttribute<? super X, ?> versionAttribute) {
		this.versionAttribute = versionAttribute;
	}

	public void addIdAttribute(SingularAttribute<? super X, ?> idAttribute) {
		idAttributes.add(idAttribute);
	}

}
