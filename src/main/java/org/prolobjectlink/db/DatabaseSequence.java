/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db;

import java.io.Serializable;

import org.prolobjectlink.db.util.JavaReflect;

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
