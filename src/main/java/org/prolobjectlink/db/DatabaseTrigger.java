/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db;

import java.io.Serializable;

import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;

public class DatabaseTrigger extends DatabaseCode<DatabaseTrigger>
		implements Serializable, SchemaElement<DatabaseTrigger>, Trigger {

	private static final long serialVersionUID = -679826331549370975L;

	protected DatabaseTrigger() {
		// internal reflection
	}

	public DatabaseTrigger(String name, String comment, Schema schema, String path, PrologProvider provider) {
		super(name, comment, schema, path, provider);
	}

	public DatabaseTrigger setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public DatabaseTrigger setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.TRIGGER;
	}

	public DatabaseCodeType getType() {
		return DatabaseCodeType.TRIGGER;
	}

	public void onInsert(DatabaseEvent event) {
		// TODO Auto-generated method stub

	}

	public void onUpdate(DatabaseEvent event) {
		// TODO Auto-generated method stub

	}

	public void onDelete(DatabaseEvent event) {
		// TODO Auto-generated method stub

	}

	public final DatabaseTrigger save() {
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
