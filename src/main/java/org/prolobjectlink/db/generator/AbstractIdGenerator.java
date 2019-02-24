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
package org.prolobjectlink.db.generator;

import java.io.Serializable;

import org.prolobjectlink.db.IdGenerator;
import org.prolobjectlink.db.util.Assertions;
import org.prolobjectlink.db.util.JavaReflect;

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
