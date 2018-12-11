/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.jpa;

import javax.persistence.Parameter;
import javax.persistence.ParameterMode;

public final class JPAParameter<T> implements Parameter<T> {

	private Object value;
	private final String name;
	private final Integer position;
	private final Class<T> parameterType;
	private final ParameterMode parameterMode;

	public JPAParameter(String name, Integer position, Class<T> parameterType) {
		this(name, position, parameterType, ParameterMode.INOUT, null);
	}

	public JPAParameter(String name, Integer position, Class<T> parameterType, Object value) {
		this(name, position, parameterType, ParameterMode.INOUT, value);
	}

	public JPAParameter(String name, Integer position, Class<T> parameterType, ParameterMode parameterMode) {
		this(name, position, parameterType, parameterMode, null);
	}

	public JPAParameter(String name, Integer position, Class<T> parameterType, ParameterMode parameterMode,
			Object value) {
		this.name = name;
		this.value = value;
		this.position = position;
		this.parameterType = parameterType;
		this.parameterMode = parameterMode;
	}

	public String getName() {
		return name;
	}

	public Integer getPosition() {
		return position;
	}

	public Class<T> getParameterType() {
		return parameterType;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public ParameterMode getParameterMode() {
		return parameterMode;
	}

	@Override
	public String toString() {
		return "LogicParameter [value=" + value + ", name=" + name + ", position=" + position + ", parameterType="
				+ parameterType + ", parameterMode=" + parameterMode + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameterMode == null) ? 0 : parameterMode.hashCode());
		result = prime * result + ((parameterType == null) ? 0 : parameterType.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		JPAParameter<?> other = (JPAParameter<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parameterMode != other.parameterMode)
			return false;
		if (parameterType == null) {
			if (other.parameterType != null)
				return false;
		} else if (!parameterType.equals(other.parameterType))
			return false;
		if (position == null) {
			if (other.position != null)
				return false;
		} else if (!position.equals(other.position))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
