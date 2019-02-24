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
import java.lang.reflect.Field;

import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class DatabaseView extends DatabaseCode<DatabaseView>
		implements Serializable, SchemaElement<DatabaseView> {

	private transient Class<?> target;
	private static final long serialVersionUID = 7552979263681672426L;

	protected DatabaseView() {
		// internal reflection
	}

	public DatabaseView(String path, Class<?> target, String comment, Schema schema, PrologProvider provider) {
		super(target != null ? target.getName() : "", comment, schema, path, provider);
		if (target != null) {
			Class<?> ptr = target;
			while (ptr != Object.class) {
				for (Field field : ptr.getDeclaredFields()) {
					String fieldName = field.getName();
					char n = Character.toUpperCase(fieldName.charAt(0));
					getParameters().add(n + fieldName.substring(1));
				}
				ptr = ptr.getSuperclass();
			}
		}
		this.target = target;
	}

	public final Class<?> getTarget() {
		return target;
	}

	public final DatabaseView setTarget(Class<?> target) {
		this.target = target;
		return this;
	}

	public final DatabaseView setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public DatabaseView setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.VIEW;
	}

	@Override
	public DatabaseCodeType getType() {
		return DatabaseCodeType.VIEW;
	}

	public final DatabaseView save() {
		PrologEngine engine = getEngine();
		if (engine != null) {
			engine.consult(getPath());
			// TODO CHECK SYNTAX ERROR
//			engine.assertz(getCode()); 
			engine.persist(getPath());
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DatabaseView other = (DatabaseView) obj;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

}
