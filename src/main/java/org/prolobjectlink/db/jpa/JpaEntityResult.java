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
package org.prolobjectlink.db.jpa;

import java.util.Arrays;

public final class JpaEntityResult {

	private final Class<?> entityClass;
	private JpaFieldResult[] fieldResults;
	private String discriminatorColumn;

	public JpaEntityResult(Class<?> entityClass) {
		this(entityClass, new JpaFieldResult[0], "");
	}

	public JpaEntityResult(Class<?> entityClass, JpaFieldResult[] fieldResults, String discriminatorColumn) {
		this.entityClass = entityClass;
		this.fieldResults = fieldResults;
		this.discriminatorColumn = discriminatorColumn;
	}

	public JpaFieldResult[] getFieldResults() {
		return fieldResults;
	}

	public void setFieldResults(JpaFieldResult[] fieldResults) {
		this.fieldResults = fieldResults;
	}

	public String getDiscriminatorColumn() {
		return discriminatorColumn;
	}

	public void setDiscriminatorColumn(String discriminatorColumn) {
		this.discriminatorColumn = discriminatorColumn;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discriminatorColumn == null) ? 0 : discriminatorColumn.hashCode());
		result = prime * result + ((entityClass == null) ? 0 : entityClass.hashCode());
		result = prime * result + Arrays.hashCode(fieldResults);
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
		JpaEntityResult other = (JpaEntityResult) obj;
		if (discriminatorColumn == null) {
			if (other.discriminatorColumn != null)
				return false;
		} else if (!discriminatorColumn.equals(other.discriminatorColumn))
			return false;
		if (entityClass == null) {
			if (other.entityClass != null)
				return false;
		} else if (!entityClass.equals(other.entityClass))
			return false;
		if (!Arrays.equals(fieldResults, other.fieldResults))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAEntityResult [entityClass=" + entityClass + ", fieldResults=" + Arrays.toString(fieldResults)
				+ ", discriminatorColumn=" + discriminatorColumn + "]";
	}

}
