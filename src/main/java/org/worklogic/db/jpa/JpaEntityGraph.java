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
package org.worklogic.db.jpa;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.AttributeNode;
import javax.persistence.EntityGraph;
import javax.persistence.Subgraph;
import javax.persistence.metamodel.Attribute;

public final class JpaEntityGraph<T> extends JpaAttributeNode<T> implements EntityGraph<T>, Subgraph<T> {

	private final boolean inmutable;
	private final Class<T> classType;
	private final List<AttributeNode<?>> attributeNodes;

	public JpaEntityGraph(String graphName) {
		this(graphName, null);
	}

	JpaEntityGraph(String name, Class<T> classType) {
		super(name);
		this.inmutable = false;
		this.classType = classType;
		this.attributeNodes = new ArrayList<AttributeNode<?>>();
	}

	public JpaEntityGraph(String name, Map<Class, Subgraph> subgraphs, Map<Class, Subgraph> keySubgraphs,
			boolean allAttributes, List<AttributeNode<?>> attributeNodes, Class<T> classType) {
		super(name, subgraphs, keySubgraphs);
		this.attributeNodes = attributeNodes;
		this.inmutable = allAttributes;
		this.classType = classType;
	}

	public JpaEntityGraph(Class<T> rootType) {
		super(null);
		this.inmutable = false;
		this.classType = rootType;
		this.attributeNodes = new ArrayList<AttributeNode<?>>();
	}

	public String getName() {
		return attributeName;
	}

	public void addAttributeNodes(String... attributeName) {
		checkMutableCondition();
		Set<String> fieldSet = getDeclaredFields();
		for (String attribute : attributeName) {
			checkDeclaredField(fieldSet, attribute);
			attributeNodes.add(new JpaAttributeNode<T>(attribute));
		}
	}

	public void addAttributeNodes(Attribute<T, ?>... attributes) {
		checkMutableCondition();
		Set<String> fieldSet = getDeclaredFields();
		for (Attribute<T, ?> attr : attributes) {
			String attribute = attr.getName();
			checkDeclaredField(fieldSet, attribute);
			attributeNodes.add(new JpaAttributeNode<T>(attribute));
		}
	}

	public <X> Subgraph<X> addSubgraph(Attribute<T, X> attribute) {
		return addSubgraph(attribute.getName(), attribute.getJavaType());
	}

	public <X> Subgraph<? extends X> addSubgraph(Attribute<T, X> attribute, Class<? extends X> type) {
		return addSubgraph(attribute.getName(), type);
	}

	public <X> Subgraph<X> addSubgraph(String attributeName) {
		return addSubgraph(attributeName, null);
	}

	public <X> Subgraph<X> addSubgraph(String attributeName, Class<X> type) {
		return new JpaEntityGraph<X>(attributeName, type);
	}

	public <X> Subgraph<X> addKeySubgraph(Attribute<T, X> attribute) {
		return addKeySubgraph(attribute.getName(), attribute.getJavaType());
	}

	public <X> Subgraph<? extends X> addKeySubgraph(Attribute<T, X> attribute, Class<? extends X> type) {
		return addKeySubgraph(attribute.getName(), type);
	}

	public <X> Subgraph<X> addKeySubgraph(String attributeName) {
		return addKeySubgraph(attributeName, null);
	}

	public <X> Subgraph<X> addKeySubgraph(String attributeName, Class<X> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Subgraph<? extends X> addSubclassSubgraph(Class<? extends X> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<AttributeNode<?>> getAttributeNodes() {
		return attributeNodes;
	}

	public Class<T> getClassType() {
		return classType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (inmutable ? 1231 : 1237);
		result = prime * result + ((attributeNodes == null) ? 0 : attributeNodes.hashCode());
		result = prime * result + ((classType == null) ? 0 : classType.hashCode());
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
		JpaEntityGraph<?> other = (JpaEntityGraph<?>) obj;
		if (inmutable != other.inmutable)
			return false;
		if (attributeNodes == null) {
			if (other.attributeNodes != null)
				return false;
		} else if (!attributeNodes.equals(other.attributeNodes))
			return false;
		return true;
	}

	private Set<String> getDeclaredFields() {
		Set<String> fieldList = new HashSet<String>();
		Class<?> classItr = classType;
		while (classItr != Object.class) {
			Field[] fields = classItr.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				fieldList.add(fields[i].getName());
			}
			classItr = classItr.getSuperclass();
		}
		return fieldList;
	}

	private void checkDeclaredField(Set<String> fieldSet, String attribute) {
		if (!fieldSet.contains(attribute)) {
			throw new IllegalArgumentException(
					"Field named " + attribute + " is not present in " + classType.getName() + " hierarchy");
		}
	}

	private void checkMutableCondition() {
		if (inmutable) {
			throw new IllegalStateException("The entity graph was statically defined");
		}
	}

}
