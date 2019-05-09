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

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;

import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractConverter<T> implements ObjectConverter<T> {

	protected final PrologProvider provider;

	protected AbstractConverter(PrologProvider provider) {
		this.provider = provider;
	}

	//
	public final boolean containQuotes(String functor) {
		if (functor != null && !functor.isEmpty()) {
			return functor.startsWith("\'") && functor.endsWith("\'");
		}
		return false;
	}

	public final String removeQuotes(String functor) {
		if (containQuotes(functor)) {
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
		} else if (!provider.equals(other.provider)) {
			return false;
		}
		return true;
	}

}
