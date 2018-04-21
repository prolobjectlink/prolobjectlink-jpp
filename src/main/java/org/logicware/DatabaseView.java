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
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;

import org.logicware.prolog.PrologProvider;

public abstract class DatabaseView extends DatabaseCode implements Serializable {

	private transient Class<?> target;
	private static final long serialVersionUID = 7552979263681672426L;

	protected DatabaseView() {
		// internal reflection
	}

	public DatabaseView(String path, Class<?> target, DatabaseSchema schema, PrologProvider provider) {
		super(CodifiableType.VIEW, path, target != null ? target.getName() : "", schema, provider);
		if (target != null) {
			// iterate over hierarchy
			Class<?> ptr = target;
			Deque<String> s = new ArrayDeque<String>();
			while (ptr != Object.class) {
				for (Field field : ptr.getDeclaredFields()) {
					String fieldName = field.getName();
					char n = Character.toUpperCase(fieldName.charAt(0));
					// s.push(n + fieldName.substring(1));
					parameters.add(n + fieldName.substring(1));
				}
				ptr = ptr.getSuperclass();
			}
			// while (!s.isEmpty()) {
			// parameters.add(s.pop());
			// }
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

	@Override
	public final DatabaseView setSchema(DatabaseSchema schema) {
		this.schema = schema;
		return this;
	}

	@Override
	public final DatabaseView addInstructions(String code) {
		instructions.add(code);
		return this;
	}

}
