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

import org.logicware.prolog.PrologProvider;

public abstract class DatabaseFunction extends DatabaseCode implements Serializable {

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

	@Override
	public final DatabaseFunction addInstructions(String code) {
		instructions.add(code);
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

}
