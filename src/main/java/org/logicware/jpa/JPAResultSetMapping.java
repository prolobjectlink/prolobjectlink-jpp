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
package org.logicware.jpa;

import java.util.Arrays;

public final class JPAResultSetMapping {

	private final String name;
	private final JPAEntityResult[] entities;

	private JPAConstructorResult[] classes;
	private JPAColumnResult[] columns;

	public JPAResultSetMapping(String name, JPAEntityResult[] entities) {
		this(name, entities, new JPAConstructorResult[0], new JPAColumnResult[0]);
	}

	public JPAResultSetMapping(String name, JPAEntityResult[] entities, JPAConstructorResult[] classes,
			JPAColumnResult[] columns) {
		this.name = name;
		this.entities = entities;
		this.classes = classes;
		this.columns = columns;
	}

	public JPAConstructorResult[] getClasses() {
		return classes;
	}

	public void setClasses(JPAConstructorResult[] classes) {
		this.classes = classes;
	}

	public JPAColumnResult[] getColumns() {
		return columns;
	}

	public void setColumns(JPAColumnResult[] columns) {
		this.columns = columns;
	}

	public String getName() {
		return name;
	}

	public JPAEntityResult[] getEntities() {
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
		JPAResultSetMapping other = (JPAResultSetMapping) obj;
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
