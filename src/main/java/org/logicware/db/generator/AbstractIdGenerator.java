/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.logicware.db.generator;

import java.io.Serializable;

import org.logicware.db.IdGenerator;
import org.logicware.db.util.Assertions;
import org.logicware.db.util.JavaReflect;

abstract class AbstractIdGenerator<O extends Serializable> implements IdGenerator<O> {

	private O value;
	private final String typeName;
	private transient Class<?> type;
	private static final long serialVersionUID = -994987235885802450L;

	public AbstractIdGenerator(O value, Class<?> type) {
		type = Assertions.requireNotNull(type);
		this.typeName = type.getName();
		this.value = value;
		this.type = type;
	}

	public final O getValue() {
		return value;
	}

	public final O setValue(O value) {
		this.value = value;
		return value;
	}

	public final Class<?> getType() {
		if (type == null) {
			type = JavaReflect.classForName(typeName);
		}
		return type;
	}

	@Override
	public final String toString() {
		return "IdGenerator [value=" + value + ", type=" + typeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typeName == null) ? 0 : typeName.hashCode());
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
		AbstractIdGenerator<?> other = (AbstractIdGenerator<?>) obj;
		if (typeName == null) {
			if (other.typeName != null)
				return false;
		} else if (!typeName.equals(other.typeName))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
