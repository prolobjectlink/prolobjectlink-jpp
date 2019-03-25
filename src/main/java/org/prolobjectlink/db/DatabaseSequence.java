/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
