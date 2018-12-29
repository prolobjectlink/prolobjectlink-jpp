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
package org.logicware.database;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;

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
