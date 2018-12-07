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
package org.logicware.database.jpa.metamodel;

import java.util.List;

import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;

public final class JPAListAttribute<X, E> extends JPAPluralAttribute<X, List<E>, E> implements ListAttribute<X, E> {

	public JPAListAttribute(String name, Type<List<E>> type, ManagedType<X> managedType, Type<E> elementType,
			PersistentAttributeType attributeType) {
		super(name, type, managedType, elementType, attributeType);
	}

	public CollectionType getCollectionType() {
		return CollectionType.LIST;
	}

}
