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

import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class DatabaseFunction extends DatabaseCode<DatabaseFunction>
		implements Serializable, SchemaElement<DatabaseFunction> {

	private static final long serialVersionUID = -4680176548026593510L;

	protected DatabaseFunction() {
		// internal reflection
	}

	public DatabaseFunction(String name, String comment, Schema schema, String path, PrologProvider provider) {
		super(name, comment, schema, path, provider);
	}

	public final DatabaseFunction addParameter(String parameter) {
		getParameters().add(parameter);
		return this;
	}

	public final DatabaseFunction removeParameter(String parameter) {
		getParameters().remove(parameter);
		return this;
	}

	public final DatabaseFunction setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public DatabaseFunction setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.FUNCTION;
	}

	@Override
	public DatabaseCodeType getType() {
		return DatabaseCodeType.FUNCTION;
	}

	public final DatabaseFunction save() {
		PrologEngine engine = getEngine();
		if (engine != null) {
			engine.consult(getPath());
			// TODO CHECK SYNTAX ERROR
//			engine.assertz(getCode()); 
			engine.persist(getPath());
		}
		return this;
	}

}
