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
package org.logicware.db.jpa;

import java.util.Arrays;

public final class JpaResultSetMapping {

	private final String name;
	private final JpaEntityResult[] entities;

	private JpaConstructorResult[] classes;
	private JpaColumnResult[] columns;

	public JpaResultSetMapping(String name, JpaEntityResult[] entities) {
		this(name, entities, new JpaConstructorResult[0], new JpaColumnResult[0]);
	}

	public JpaResultSetMapping(String name, JpaEntityResult[] entities, JpaConstructorResult[] classes,
			JpaColumnResult[] columns) {
		this.name = name;
		this.entities = entities;
		this.classes = classes;
		this.columns = columns;
	}

	public JpaConstructorResult[] getClasses() {
		return classes;
	}

	public void setClasses(JpaConstructorResult[] classes) {
		this.classes = classes;
	}

	public JpaColumnResult[] getColumns() {
		return columns;
	}

	public void setColumns(JpaColumnResult[] columns) {
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public JpaEntityResult[] getEntities() {
		return entities;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(classes);
		result = prime * result + Arrays.hashCode(columns);
		result = prime * result + Arrays.hashCode(entities);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		JpaResultSetMapping other = (JpaResultSetMapping) obj;
		if (!Arrays.equals(classes, other.classes))
			return false;
		if (!Arrays.equals(columns, other.columns))
			return false;
		if (!Arrays.equals(entities, other.entities))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAResultSetMapping [name=" + name + ", entities=" + Arrays.toString(entities) + ", classes="
				+ Arrays.toString(classes) + ", columns=" + Arrays.toString(columns) + "]";
	}

}
