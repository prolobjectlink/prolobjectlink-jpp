/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware;

import java.io.Serializable;

public final class DatabaseSequence implements Serializable {

	private int value;
	private String javaClass;
	private final String name;
	private final int increment;
	private transient Schema schema;
	private static final long serialVersionUID = 937204609884481388L;

	private DatabaseSequence() {
		this(null, null, 1, null);

		// TODO use object reflection helper
		// for internal reflection
	}

	public DatabaseSequence(String name, Class<?> clazz, int increment, Schema schema) {
		this.javaClass = clazz != null ? clazz.getName() : "";
		this.increment = increment;
		this.schema = schema;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public int getIncrement() {
		return increment;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int increment() {
		this.value++;
		return value;
	}

	public Schema getSchema() {
		return schema;
	}

	public DatabaseSequence setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public Class<?> getJavaClass() {
		return ReflectionUtils.classForName(javaClass);
	}

	public void setJavaClass(Class<?> javaClass) {
		this.javaClass = javaClass.getName();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + increment;
		result = prime * result + ((javaClass == null) ? 0 : javaClass.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + value;
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
		DatabaseSequence other = (DatabaseSequence) obj;
		if (increment != other.increment)
			return false;
		if (javaClass == null) {
			if (other.javaClass != null)
				return false;
		} else if (!javaClass.equals(other.javaClass))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (value != other.value)
			return false;
		return true;
	}

}
