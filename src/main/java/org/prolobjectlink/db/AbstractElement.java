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

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractElement<E extends SchemaElement<?>> implements SchemaElement<E>, Serializable {

	protected final String name;
	protected String comment;
	protected transient Schema schema;
	private static final long serialVersionUID = -1360649262423474799L;

	protected AbstractElement() {
		// for internal reflection
		this.comment = null;
		this.schema = null;
		this.name = null;
	}

	public AbstractElement(String name, String comment, Schema schema) {
		this.name = name;
		this.comment = comment;
		this.schema = schema;
	}

	public final String getName() {
		return name;
	}

	public final Schema getSchema() {
		return schema;
	}

	public final String getComment() {
		return comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		AbstractElement<?> other = (AbstractElement<?>) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
