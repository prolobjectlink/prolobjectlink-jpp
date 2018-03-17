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

import org.logicware.prolog.PrologProvider;

public abstract class DatabaseView extends DatabaseCode implements Serializable {

	private transient Class<?> target;
	private static final long serialVersionUID = 7552979263681672426L;

	public DatabaseView() {
		// TODO use refelection utils remove this
	}

	public DatabaseView(String path, Class<?> target, DatabaseSchema schema, PrologProvider provider) {
		super(CodifiableType.VIEW, path, target != null ? target.getName() : "", schema, provider);
		this.target = target;
	}

	public final Class<?> getTarget() {
		return target;
	}

	public final DatabaseView setTarget(Class<?> target) {
		this.target = target;
		return this;
	}

	@Override
	public final DatabaseView setCode(String code) {
		this.code = code;
		return this;
	}

	@Override
	public final DatabaseView setSchema(DatabaseSchema schema) {
		this.schema = schema;
		return this;
	}

}
