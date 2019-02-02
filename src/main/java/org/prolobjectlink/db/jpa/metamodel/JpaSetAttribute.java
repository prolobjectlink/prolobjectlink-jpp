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

import java.util.Set;

import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.Type;

public final class JpaSetAttribute<X, E> extends JpaPluralAttribute<X, Set<E>, E> implements SetAttribute<X, E> {

	public JpaSetAttribute(ManagedType<X> ownerType, String name, Type<Set<E>> type, Type<E> elementType,
			PersistentAttributeType attributeType) {
		super(ownerType, name, type, elementType, attributeType);
	}

	public CollectionType getCollectionType() {
		return CollectionType.SET;
	}

}
