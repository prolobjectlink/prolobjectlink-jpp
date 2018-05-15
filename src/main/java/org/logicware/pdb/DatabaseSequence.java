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
package org.logicware.pdb;

import java.io.Serializable;

import org.logicware.pdb.util.JavaReflect;

public final class DatabaseSequence extends AbstractElement<DatabaseSequence>
		implements Serializable, SchemaElement<DatabaseSequence> {

	private int value;
	private String javaClass;
	private final int increment;
	private static final long serialVersionUID = 937204609884481388L;

	/**
	 * for internal reflection only
	 */
	protected DatabaseSequence() {
		this(null, null, null, 1, null);
	}

	public DatabaseSequence(String name, String comment, Class<?> clazz, int increment, Schema schema) {
		super(name, comment, schema);
		this.javaClass = clazz != null ? clazz.getName() : "";
		this.increment = increment;
		this.schema = schema;
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

	public DatabaseSequence setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public Class<?> getJavaClass() {
		return JavaReflect.classForName(javaClass);
	}

	public void setJavaClass(Class<?> javaClass) {
		this.javaClass = javaClass.getName();
	}

	public DatabaseSequence setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.SEQUENCE;
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
		return value == other.value;
	}

}
