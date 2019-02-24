/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
