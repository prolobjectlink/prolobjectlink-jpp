/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db;

import java.io.Serializable;
import java.lang.reflect.Field;

import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;

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
