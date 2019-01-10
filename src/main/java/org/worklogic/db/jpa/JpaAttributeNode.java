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

import java.util.HashMap;
import java.util.Map;

import javax.persistence.AttributeNode;
import javax.persistence.Subgraph;

public class JpaAttributeNode<T> implements AttributeNode<T> {

	protected final String attributeName;
	protected final Map<Class, Subgraph> subgraphs;
	protected final Map<Class, Subgraph> keySubgraphs;

	public JpaAttributeNode(String attribute) {
		this(attribute, new HashMap<Class, Subgraph>(), new HashMap<Class, Subgraph>());
	}

	public JpaAttributeNode(String attributeName, Map<Class, Subgraph> subgraphs, Map<Class, Subgraph> keySubgraphs) {
		this.attributeName = attributeName;
		this.keySubgraphs = keySubgraphs;
		this.subgraphs = subgraphs;
	}

	public final String getAttributeName() {
		return attributeName;
	}

	public final Map<Class, Subgraph> getSubgraphs() {
		return subgraphs;
	}

	public final Map<Class, Subgraph> getKeySubgraphs() {
		return keySubgraphs;
	}

	public void addSubgraph(JpaEntityGraph<?> entityGraph) {
		subgraphs.put(entityGraph.getClassType(), entityGraph);
	}

	public void addKeySubgraph(JpaEntityGraph<?> entityGraphImpl) {
		keySubgraphs.put(entityGraphImpl.getClassType(), entityGraphImpl);
	}

	@Override
	public String toString() {
		return "LogicAttributeNode [attributeName=" + attributeName + ", subgraphs=" + subgraphs + ", keySubgraphs="
				+ keySubgraphs + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeName == null) ? 0 : attributeName.hashCode());
		result = prime * result + ((keySubgraphs == null) ? 0 : keySubgraphs.hashCode());
		result = prime * result + ((subgraphs == null) ? 0 : subgraphs.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaAttributeNode<?> other = (JpaAttributeNode<?>) obj;
		if (attributeName == null) {
			if (other.attributeName != null)
				return false;
		} else if (!attributeName.equals(other.attributeName))
			return false;
		if (keySubgraphs == null) {
			if (other.keySubgraphs != null)
				return false;
		} else if (!keySubgraphs.equals(other.keySubgraphs))
			return false;
		if (subgraphs == null) {
			if (other.subgraphs != null)
				return false;
		} else if (!subgraphs.equals(other.subgraphs))
			return false;
		return true;
	}

}
