/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.pdb;

public final class NoSuchFieldError extends RuntimeError {

	private static final long serialVersionUID = 3248316676856294109L;
	private static final String THE_FIELD_NAMED = "The field named ";

	public NoSuchFieldError(String field) {
		super(THE_FIELD_NAMED + field + " is not declared");
	}

	public NoSuchFieldError(String field, Class<?> clazz) {
		super(THE_FIELD_NAMED + field + " is not declared in the class " + clazz.getName());
	}

	public NoSuchFieldError(String field, Class<?> clazz, Throwable throwable) {
		super(THE_FIELD_NAMED + field + " is not declared in the class " + clazz.getName(), throwable);
	}

}
