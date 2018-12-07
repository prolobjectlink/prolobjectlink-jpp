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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;

public abstract class JPAManagedType<X> extends JPAType<X> implements ManagedType<X> {

	private final Map<String, Attribute<X, ?>> attributes;

	public JPAManagedType(Class<X> javaType) {
		super(javaType);
		this.attributes = new LinkedHashMap<String, Attribute<X, ?>>();
	}

	public void addAttribute(String name, Attribute<X, ?> attribute) {
		attributes.put(name, attribute);
	}

	public Set<Attribute<? super X, ?>> getAttributes() {
		Set<Attribute<? super X, ?>> set = new LinkedHashSet<Attribute<? super X, ?>>();
		Collection<Attribute<X, ?>> collection = attributes.values();
		for (Attribute<X, ?> attribute : collection) {
			set.add(attribute);
		}
		return set;
	}

	public Attribute<? super X, ?> getAttribute(String name) {
		return getDeclaredAttribute(name);
	}

	public Set<SingularAttribute<? super X, ?>> getSingularAttributes() {
		Set<SingularAttribute<? super X, ?>> set = new LinkedHashSet<SingularAttribute<? super X, ?>>();
		Collection<Attribute<X, ?>> collection = attributes.values();
		for (Attribute<X, ?> attribute : collection) {
			if (attribute instanceof SingularAttribute) {
				set.add((SingularAttribute<? super X, ?>) attribute);
			}
		}
		return set;
	}

	public SingularAttribute<? super X, ?> getSingularAttribute(String name) {
		return getDeclaredSingularAttribute(name);
	}

	public <Y> SingularAttribute<? super X, Y> getSingularAttribute(String name, Class<Y> type) {
		return getDeclaredSingularAttribute(name, type);
	}

	public Set<PluralAttribute<? super X, ?, ?>> getPluralAttributes() {
		Set<Attribute<? super X, ?>> allAttributes = getAttributes();
		Set<PluralAttribute<? super X, ?, ?>> set = new LinkedHashSet<PluralAttribute<? super X, ?, ?>>();
		for (Attribute<? super X, ?> attribute : allAttributes) {
			if (attribute.isCollection()) {
				set.add((PluralAttribute<? super X, ?, ?>) attribute);
			}
		}
		return set;
	}

	public CollectionAttribute<? super X, ?> getCollection(String name) {
		return getDeclaredCollection(name);
	}

	public <E> CollectionAttribute<? super X, E> getCollection(String name, Class<E> elementType) {
		return getDeclaredCollection(name, elementType);
	}

	public SetAttribute<? super X, ?> getSet(String name) {
		return getDeclaredSet(name);
	}

	public <E> SetAttribute<? super X, E> getSet(String name, Class<E> elementType) {
		return getDeclaredSet(name, elementType);
	}

	public ListAttribute<? super X, ?> getList(String name) {
		return getDeclaredList(name);
	}

	public <E> ListAttribute<? super X, E> getList(String name, Class<E> elementType) {
		return getDeclaredList(name, elementType);
	}

	public MapAttribute<? super X, ?, ?> getMap(String name) {
		return getDeclaredMap(name);
	}

	public <K, V> MapAttribute<? super X, K, V> getMap(String name, Class<K> keyType, Class<V> valueType) {
		return getDeclaredMap(name, keyType, valueType);
	}

	public Set<Attribute<X, ?>> getDeclaredAttributes() {
		Set<Attribute<X, ?>> set = new LinkedHashSet<Attribute<X, ?>>();
		Collection<Attribute<X, ?>> collection = attributes.values();
		for (Attribute<X, ?> attribute : collection) {
			set.add(attribute);
		}
		return set;
	}

	public Attribute<X, ?> getDeclaredAttribute(String name) {
		if (!attributes.containsKey(name)) {
			throw new IllegalArgumentException("Attribute " + name + " not found  on model " + getJavaType());
		}
		return attributes.get(name);
	}

	public Set<SingularAttribute<X, ?>> getDeclaredSingularAttributes() {
		Set<SingularAttribute<X, ?>> set = new LinkedHashSet<SingularAttribute<X, ?>>();
		Collection<Attribute<X, ?>> collection = attributes.values();
		for (Attribute<X, ?> attribute : collection) {
			if (attribute instanceof SingularAttribute) {
				set.add((SingularAttribute<X, ?>) attribute);
			}
		}
		return set;
	}

	public SingularAttribute<X, ?> getDeclaredSingularAttribute(String name) {
		Attribute<? super X, ?> attribute = getAttribute(name);
		if (!(attribute instanceof SingularAttribute)) {
			throw new IllegalArgumentException("No singular attribute " + name + "on model " + getJavaType());
		}
		return (SingularAttribute<X, ?>) attribute;
	}

	public <Y> SingularAttribute<X, Y> getDeclaredSingularAttribute(String name, Class<Y> type) {
		SingularAttribute<? super X, ?> attribute = getSingularAttribute(name);
		Class<?> javaType = attribute.getType().getJavaType();
		if (javaType != type) {
			throw new IllegalArgumentException("Incorrect singular attribute type");
		}
		return (SingularAttribute<X, Y>) attribute;
	}

	public Set<PluralAttribute<X, ?, ?>> getDeclaredPluralAttributes() {
		Set<Attribute<? super X, ?>> allAttributes = getAttributes();
		Set<PluralAttribute<X, ?, ?>> set = new LinkedHashSet<PluralAttribute<X, ?, ?>>();
		for (Attribute<? super X, ?> attribute : allAttributes) {
			if (attribute.isCollection()) {
				set.add((PluralAttribute<X, ?, ?>) attribute);
			}
		}
		return set;
	}

	public CollectionAttribute<X, ?> getDeclaredCollection(String name) {
		Attribute<? super X, ?> attribute = getAttribute(name);
		if (!(attribute instanceof CollectionAttribute)) {
			throw new IllegalArgumentException("No collection type attribute " + name + "on model " + getJavaType());
		}
		return (CollectionAttribute<X, ?>) attribute;
	}

	public <E> CollectionAttribute<X, E> getDeclaredCollection(String name, Class<E> elementType) {
		CollectionAttribute<? super X, ?> collectionAttribute = getCollection(name);
		Class<?> eType = collectionAttribute.getElementType().getJavaType();
		if (eType != elementType) {
			throw new IllegalArgumentException(
					"Incorrect element type for collection attribute " + name + "on model " + getJavaType());
		}
		return (CollectionAttribute<X, E>) collectionAttribute;
	}

	public SetAttribute<X, ?> getDeclaredSet(String name) {
		Attribute<? super X, ?> attribute = getAttribute(name);
		if (!(attribute instanceof SetAttribute)) {
			throw new IllegalArgumentException("No set type attribute " + name + "on model " + getJavaType());
		}
		return (SetAttribute<X, ?>) attribute;
	}

	public <E> SetAttribute<X, E> getDeclaredSet(String name, Class<E> elementType) {
		SetAttribute<? super X, ?> setAttribute = getSet(name);
		Class<?> eType = setAttribute.getElementType().getJavaType();
		if (eType != elementType) {
			throw new IllegalArgumentException(
					"Incorrect element type for set attribute " + name + "on model " + getJavaType());
		}
		return (SetAttribute<X, E>) setAttribute;
	}

	public ListAttribute<X, ?> getDeclaredList(String name) {
		Attribute<? super X, ?> attribute = getAttribute(name);
		if (!(attribute instanceof ListAttribute)) {
			throw new IllegalArgumentException("No list type attribute " + name + "on model " + getJavaType());
		}
		return (ListAttribute<X, ?>) attribute;
	}

	public <E> ListAttribute<X, E> getDeclaredList(String name, Class<E> elementType) {
		ListAttribute<? super X, ?> listAttribute = getList(name);
		Class<?> eType = listAttribute.getElementType().getJavaType();
		if (eType != elementType) {
			throw new IllegalArgumentException(
					"Incorrect element type for list attribute " + name + "on model " + getJavaType());
		}
		return (ListAttribute<X, E>) listAttribute;
	}

	public MapAttribute<X, ?, ?> getDeclaredMap(String name) {
		Attribute<? super X, ?> attribute = getAttribute(name);
		if (!(attribute instanceof MapAttribute)) {
			throw new IllegalArgumentException("No map type attribute " + name + "on model " + getJavaType());
		}
		return (MapAttribute<X, ?, ?>) attribute;
	}

	public <K, V> MapAttribute<X, K, V> getDeclaredMap(String name, Class<K> keyType, Class<V> valueType) {
		MapAttribute<? super X, ?, ?> mapAttribute = getMap(name);
		Class<?> kType = mapAttribute.getKeyType().getJavaType();
		Class<?> vType = mapAttribute.getElementType().getJavaType();
		if (kType != keyType) {
			throw new IllegalArgumentException(
					"Incorrect key type for map attribute " + name + "on model " + getJavaType());
		}
		if (vType != valueType) {
			throw new IllegalArgumentException(
					"Incorrect value type for map attribute " + name + "on model " + getJavaType());
		}
		return (MapAttribute<X, K, V>) mapAttribute;
	}

}
