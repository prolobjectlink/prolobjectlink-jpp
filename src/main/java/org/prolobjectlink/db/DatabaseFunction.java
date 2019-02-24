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

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class DatabaseFunction extends DatabaseCode<DatabaseFunction>
		implements Serializable, SchemaElement<DatabaseFunction> {

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

	public final DatabaseFunction save() {
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
