/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.database.jpa;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.logicware.database.DatabaseEngine;
import org.logicware.database.ObjectConverter;
import org.logicware.database.util.JavaReflect;
import org.logicware.prolog.PrologTerm;

public final class JpaNativeQuery extends JpaQuery implements Query {

	private JpaResultSetMapping resultSetMapping;

	// TODO OPTIMIZE SAME CODE BLOCKS

	public JpaNativeQuery(DatabaseEngine database, String qlString) {
		super(database, qlString);
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			if (result instanceof Object[]) {
				Object[] objects = (Object[]) result;
				JpaTuple tuple = new JpaTuple(objects.length);
				for (int j = 0; j < objects.length; j++) {
					Class<?> clazz = classes.get(j);
					Object object = objects[j];
					Field[] fields = clazz.getDeclaredFields();
					for (int k = 0; k < fields.length; k++) {
						Class<?> javaType = fields[k].getType();
						String alias = fields[k].getName();
						Object value = JavaReflect.readValue(fields[k], object);
						tuple.add(new JpaTupleElement<Object>(alias, javaType, value));
					}
				}
				tuples.add(tuple);
			}
		}

	}

	public JpaNativeQuery(DatabaseEngine database, String qlString, Class<?> resultClass) {
		super(database, qlString, resultClass);
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			Object[] objects = (Object[]) result;
			JpaTuple tuple = new JpaTuple(objects.length);
			for (int j = 0; j < objects.length; j++) {
				if (resultClass.isInstance(objects[j])) {
					Object object = objects[j];
					tuple.add(new JpaTupleElement<Object>("", resultClass, object));
				}
			}
			tuples.add(tuple);
		}

	}

	public JpaNativeQuery(DatabaseEngine database, String qlString, JpaResultSetMapping resultSetMapping) {
		super(database, qlString);
		this.resultSetMapping = resultSetMapping;
		ObjectConverter<PrologTerm> converter = database.getConverter();
		PrologTerm[] prologTerms = converter.toTermsArray(qlString);
		List<Class<?>> classes = database.classesOf(prologTerms);
		List<Object> solutions = database.solutionsOf(prologTerms, classes);
		for (Iterator<?> i = solutions.iterator(); i.hasNext();) {
			Object result = i.next();
			Object[] objects = (Object[]) result;
			JpaTuple tuple = new JpaTuple(objects.length);
			for (int j = 0; j < objects.length; j++) {
				System.out.println(Arrays.toString(objects));
			}
			tuples.add(tuple);
		}

	}

}
