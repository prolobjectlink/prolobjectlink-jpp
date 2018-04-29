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
package org.logicware.prolog;

import org.logicware.DatabaseView;
import org.logicware.Schema;

public final class PrologDatabaseView extends DatabaseView {

	private static final long serialVersionUID = 6645651570601921306L;

	private PrologDatabaseView() {
		// for internal reflection
	}

	public PrologDatabaseView(String path, Class<?> target, String comment, Schema schema, PrologProvider provider) {
		super(path, target, comment, schema, provider);
	}

}
