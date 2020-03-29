/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.prolobjectlink.prolog.IndicatorError;
import io.github.prolobjectlink.prolog.PrologClause;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologList;
import io.github.prolobjectlink.prolog.PrologTerm;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractGenerator implements SchemaGenerator {

	private static final String KEY = "key/2";
	private static final String FIELD = "field/2";
	private static final String CLASS = "entity/3";
	private static final String ONETOONE = "oneToOne/2";
	private static final String ONETOMANY = "oneToMany/2";
	private static final String MANYTOONE = "manyToOne/2";
	private static final String MANYTOMANY = "manyToMany/2";

	private final PrologEngine engine;
	private final String modelLocation;
	private final DatabaseEngine database;
	private final ObjectConverter<PrologTerm> converter;

	protected AbstractGenerator(DatabaseEngine database, String modelLocation) {
		this.converter = database.getConverter();
		this.modelLocation = modelLocation;
		this.engine = database.getEngine();
		this.database = database;
	}

	private void assertValidClassIndicator(PrologClause clause) {
		if (!clause.getIndicator().equals(CLASS)) {
			throw new IndicatorError("No valid class descriptor predicate " + clause);
		}
	}

	private void assertValidFieldIndicator(PrologTerm prologTerm) {
		String indicator = prologTerm.getIndicator();
		if (!indicator.equals(FIELD) && !indicator.equals(KEY) && !indicator.equals(ONETOONE)
				&& !indicator.equals(ONETOMANY) && !indicator.equals(MANYTOONE) && !indicator.equals(MANYTOMANY)) {
			throw new IndicatorError("No valid field descriptor predicate " + prologTerm);
		}
	}

	public final Schema createSchema() {
		engine.consult(modelLocation);
		Schema schema = database.getSchema();

		Map<String, DatabaseClass> pending = new HashMap<String, DatabaseClass>();
//		Map<String, Class<?>> resolved = new HashMap<String, Class<?>>();

		for (PrologClause clause : engine) {

			assertValidClassIndicator(clause);

			PrologTerm head = clause.getHead();
			PrologTerm className = head.getArgument(0);
			PrologTerm classFields = head.getArgument(2);

			// class qualified name
			String name = (String) converter.toObject(className);

			// class short name
			int index = name.lastIndexOf('.') + 1;
			String shortName = name.substring(index);

			DatabaseClass dbclass = schema.addClass(shortName, "");
			PrologList fields = (PrologList) classFields;

			int position = 0;

			for (PrologTerm field : fields) {

				String ftype = "";
				Class<?> c = null;
				Class<?> linkedClass = null;
				DatabaseField attribute = null;

				assertValidFieldIndicator(field);

				PrologTerm fieldName = field.getArgument(0);
				PrologTerm fieldType = field.getArgument(1);

				String fname = (String) converter.toObject(fieldName);

				if (fieldType.hasIndicator("set", 1)) {
					PrologTerm linkedType = fieldType.getArgument(0);
					linkedClass = converter.toClass(linkedType);
					c = Set.class;
				} else if (fieldType.hasIndicator("list", 1)) {
					PrologTerm linkedType = fieldType.getArgument(0);
					linkedClass = converter.toClass(linkedType);
					c = List.class;
				} else if (fieldType.hasIndicator("collection", 1)) {
					PrologTerm linkedType = fieldType.getArgument(0);
					linkedClass = converter.toClass(linkedType);
					c = Collection.class;
				} else if (fieldType.hasIndicator("map", 2)) {
					PrologTerm linkedKeyType = fieldType.getArgument(0);
					PrologTerm linkedValueType = fieldType.getArgument(1);
					c = Map.class;
				} else {
					ftype = (String) converter.toObject(fieldType);
					c = converter.toClass(ftype);
				}

				if (c == null) {

					if (linkedClass != null) {
						attribute = dbclass.addField(fname, "", position++, c, linkedClass);
					} else {
						DatabaseClass type = new DatabaseClass(ftype, "", schema);
						attribute = dbclass.addField(fname, "", position++, Object.class);
						pending.put(fname, type);
					}

				} else {

					if (linkedClass != null) {
						attribute = dbclass.addField(fname, "", position++, c, linkedClass);
					} else {
						attribute = dbclass.addField(fname, "", position++, c);
					}

				}

				if (field.hasIndicator("key", 2)) {
					attribute.setPrimaryKey(true);
				}
				if (field.hasIndicator("oneToOne", 2)) {
					attribute.setOneToOne(true);
					attribute.setOneToMany(false);
					attribute.setManyToOne(false);
					attribute.setManyToMany(false);
				}
				if (field.hasIndicator("oneToMany", 2)) {
					attribute.setOneToOne(false);
					attribute.setOneToMany(true);
					attribute.setManyToOne(false);
					attribute.setManyToMany(false);
				}
				if (field.hasIndicator("manyToOne", 2)) {
					attribute.setOneToOne(false);
					attribute.setOneToMany(false);
					attribute.setManyToOne(true);
					attribute.setManyToMany(false);
				}
				if (field.hasIndicator("manyToMany", 2)) {
					attribute.setOneToOne(false);
					attribute.setOneToMany(false);
					attribute.setManyToOne(false);
					attribute.setManyToMany(true);
				}

			}

		}
		return schema;
	}

	public final List<Class<?>> compileSchema() {
		return createSchema().compile();
	}

	public final String generateSchema() {
		return createSchema().generate();
	}

}
