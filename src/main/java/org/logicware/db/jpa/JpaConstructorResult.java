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

public final class JpaConstructorResult {

	private final Class<?> targetClass;
	private JpaColumnResult[] columns;

	public JpaConstructorResult(Class<?> targetClass) {
		this(targetClass, new JpaColumnResult[0]);
	}

	public JpaConstructorResult(Class<?> targetClass, JpaColumnResult[] columns) {
		this.targetClass = targetClass;
		this.columns = columns;
	}

	public JpaColumnResult[] getColumns() {
		return columns;
	}

	public void setColumns(JpaColumnResult[] columns) {
		this.columns = columns;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(columns);
		result = prime * result + ((targetClass == null) ? 0 : targetClass.hashCode());
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
		JpaConstructorResult other = (JpaConstructorResult) obj;
		if (!Arrays.equals(columns, other.columns))
			return false;
		if (targetClass == null) {
			if (other.targetClass != null)
				return false;
		} else if (!targetClass.equals(other.targetClass))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAConstructorResult [targetClass=" + targetClass + ", columns=" + Arrays.toString(columns) + "]";
	}

}
