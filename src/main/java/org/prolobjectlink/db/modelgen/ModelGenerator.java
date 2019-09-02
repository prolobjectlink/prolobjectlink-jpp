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
package org.prolobjectlink.db.modelgen;

import java.util.List;

import org.prolobjectlink.db.DatabaseClass;
import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.SchemaGenerator;
import org.prolobjectlink.prolog.PrologClause;
import org.prolobjectlink.prolog.PrologList;
import org.prolobjectlink.prolog.PrologTerm;

public class ModelGenerator extends AbstractGenerator implements SchemaGenerator {

	private static final String CLASS_INDICATOR = "class/3";
	private static final String FIELD_INDICATOR = "field/2";

	private final ObjectConverter<PrologTerm> converter;
	private final String modelLocation;

	private void assertValidClassIndicator(PrologClause clause) {
		if (!clause.getIndicator().equals(CLASS_INDICATOR)) {
			throw new RuntimeException("No valid class descriptor predicate " + clause);
		}
	}

	private void assertValidFieldIndicator(PrologTerm prologTerm) {
		if (!prologTerm.getIndicator().equals(FIELD_INDICATOR)) {
			throw new RuntimeException("No valid field descriptor predicate " + prologTerm);
		}
	}

	public ModelGenerator(DatabaseEngine database, String modelLocation) {
		super(database);
		converter = database.getConverter();
		this.modelLocation = modelLocation;
	}

	public Schema createSchema() {
		engine.consult(modelLocation);
		Schema schema = databse.getSchema();
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

				assertValidFieldIndicator(field);

				PrologTerm fieldName = field.getArgument(0);
				PrologTerm fieldType = field.getArgument(1);

				String fname = (String) converter.toObject(fieldName);
				String ftype = (String) converter.toObject(fieldType);
				Class<?> c = converter.toClass(ftype);

				if (c == null) {
					// TODO We need generate the class
				}

				dbclass.addField(fname, "", position++, c);

			}

		}
		return schema;
	}

	public List<Class<?>> compileSchema() {
		return createSchema().compile();
	}

	public String generateSchema() {
		return createSchema().generate();
	}

	public void writePersistence() {
		// TODO Auto-generated method stub

	}

}
