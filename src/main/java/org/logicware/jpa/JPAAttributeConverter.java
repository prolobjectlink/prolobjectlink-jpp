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
package org.logicware.jpa;

import javax.persistence.AttributeConverter;

import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.prolog.PrologObjectConverter;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;

public final class JPAAttributeConverter implements AttributeConverter<Object, PrologTerm> {

	private final ObjectConverter<PrologTerm> converter;

	public JPAAttributeConverter(PrologProvider provider) {
		this.converter = new PrologObjectConverter(provider);
	}

	public PrologTerm convertToDatabaseColumn(Object attribute) {
		return converter.toTerm(attribute);
	}

	public Object convertToEntityAttribute(PrologTerm dbData) {
		return converter.toObject(dbData);
	}

}
