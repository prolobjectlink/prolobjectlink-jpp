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
package org.logicware.pdb;

import java.io.Serializable;

import org.logicware.pdb.prolog.PrologProvider;

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

	public DatabaseTrigger addInstructions(String code) {
		instructions.add(code);
		return this;
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
		getEngine().consult(getPath());
		getEngine().assertz(getCode());
		getEngine().persist(getPath());
		return this;
	}

}
