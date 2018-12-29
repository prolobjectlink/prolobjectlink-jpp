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
package org.logicware.database;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.logicware.database.util.JavaReflect;
import org.logicware.prolog.PrologProvider;

public abstract class AbstractConverter<T> implements ObjectConverter<T> {

	protected final PrologProvider provider;

	protected AbstractConverter(PrologProvider provider) {
		this.provider = provider;
	}

	//
	public final boolean containApices(String functor) {
		if (functor != null && !functor.isEmpty()) {
			return functor.startsWith("\'") && functor.endsWith("\'");
		}
		return false;
	}

	public final String removeApices(String functor) {
		if (containApices(functor)) {
			return functor.substring(1, functor.length() - 1);
		}
		return functor;
	}

	/**
	 * Return a list with predicate classes present in prolog terms array
	 * 
	 * @param terms prolog terms array
	 * @return array with predicate classes present in prolog terms array
	 * @since 1.0
	 */
	public final Class<?>[] toClassArray(T[] terms) {
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		for (int i = 0; i < terms.length; i++) {
			classes.add(toClass(terms[i]));
		}
		return classes.toArray(new Class<?>[0]);
	}

	public final Object[] toObjectsArray(T[] terms) {
		Object array = Array.newInstance(Object.class, terms.length);
		for (int i = 0; i < terms.length; i++) {
			Array.set(array, i, toObject(terms[i]));
		}
		return (Object[]) array;
	}

	////////////////////////////////////

	public final Object toObject(Class<?> clazz, Map<String, T> solutionsMap) {
		Object object = null;

		// getting class from class map
		Class<?> classPtr = clazz;

		// create a new instance from class
		object = JavaReflect.newInstance(classPtr);

		while (classPtr != Object.class) {

			// getting declared fields
			Field[] fields = classPtr.getDeclaredFields();

			for (int i = fields.length - 1; i >= 0; i--) {
				Field field = fields[i];

				// check persistence condition
				if (JavaReflect.isPersistent(field) && !JavaReflect.isStaticAndFinal(field)) {

					// field name to be converted in variable name
					String fieldName = field.getName();

					// obtaining variable name with first char in upper case
					String varName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

					// retrieving prolog term from solution
					T prologTerm = solutionsMap.get(varName);

					// if prolog term is null is set to nil term else itself
					prologTerm = (T) (prologTerm == null ? provider.prologNil() : prologTerm);

					// recovery data object
					Object value = toObject(prologTerm);

					// write field with argument value
					JavaReflect.writeValue(field, object, value);

				}

			}
			// update class pointer for the next super class
			classPtr = classPtr.getSuperclass();
		}

		return object;
	}

	public final T[] toTermsArray(String string) {
		return (T[]) provider.parseTerms(string);
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractConverter<?> other = (AbstractConverter<?>) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

}
