/*
 * #%L
 * prolobjectlink
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
import java.util.List;

import org.logicware.prolog.PrologProvider;
import org.logicware.util.ArrayList;

public abstract class DatabaseFunction extends DatabaseCode implements Serializable {

	private static final long serialVersionUID = -4680176548026593510L;

	protected DatabaseFunction() {
	}

	public DatabaseFunction(String name, DatabaseSchema schema, String path, PrologProvider provider) {
		super(CodifiableType.FUNCTION, path, name, schema, provider);
	}

	public final DatabaseFunction addParameter(String parameter) {
		getParameters().add(parameter);
		return this;
	}

	public final DatabaseFunction removeParameter(String parameter) {
		getParameters().remove(parameter);
		return this;
	}

	@Override
	public final DatabaseFunction setCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public final DatabaseFunction setSchema(DatabaseSchema schema) {
		this.schema = schema;
		return this;
	}

}
