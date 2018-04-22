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

public final class DatabasePackage extends AbstractElement implements SchemaElement {

	private static final long serialVersionUID = 1012619958416382243L;

	/**
	 * for internal reflection only
	 */
	protected DatabasePackage() {

	}

	public DatabasePackage(String name, Schema schema) {
		super(name, schema);
	}

	public DatabasePackage(String name, String comment, Schema schema) {
		super(name, comment, schema);
	}

	public DatabasePackage setSchema(Schema schema) {
		this.schema = schema;
		return this;
	}

	public DatabasePackage setComment(String comment) {
		this.comment = comment;
		return this;
	}

	public SchemaElementType geElementType() {
		return SchemaElementType.PACKAGE;
	}

}
