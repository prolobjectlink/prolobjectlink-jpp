/*
 * #%L
 * prolobjectlink
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
package org.logicware.jpd.jpi;

import static org.logicware.jpi.PrologTermType.ATOM_TYPE;
import static org.logicware.jpi.PrologTermType.DOUBLE_TYPE;
import static org.logicware.jpi.PrologTermType.EMPTY_TYPE;
import static org.logicware.jpi.PrologTermType.FALSE_TYPE;
import static org.logicware.jpi.PrologTermType.FLOAT_TYPE;
import static org.logicware.jpi.PrologTermType.INTEGER_TYPE;
import static org.logicware.jpi.PrologTermType.LIST_TYPE;
import static org.logicware.jpi.PrologTermType.LONG_TYPE;
import static org.logicware.jpi.PrologTermType.NIL_TYPE;
import static org.logicware.jpi.PrologTermType.STRUCTURE_TYPE;
import static org.logicware.jpi.PrologTermType.TRUE_TYPE;
import static org.logicware.jpi.PrologTermType.VARIABLE_TYPE;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import org.logicware.jpd.AbstractConverter;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpi.PrologDouble;
import org.logicware.jpi.PrologFloat;
import org.logicware.jpi.PrologInteger;
import org.logicware.jpi.PrologLong;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologStructure;
import org.logicware.jpi.PrologTerm;
import org.logicware.jpi.PrologVariable;
import org.logicware.jpi.UnknownTermError;
import org.logicware.jpp.ClassNotFoundError;

public final class PrologObjectConverter extends AbstractConverter<PrologTerm> implements ObjectConverter<PrologTerm> {

	public PrologObjectConverter(PrologProvider provider) {
		super(provider);
	}

	public Class<?> toClass(PrologTerm prologTerm) {
		switch (prologTerm.getType()) {
		case NIL_TYPE:
			return null;
		case TRUE_TYPE:
			return Boolean.class;
		case FALSE_TYPE:
			return Boolean.class;
		case ATOM_TYPE:
			return String.class;
		case INTEGER_TYPE:
			return Integer.class;
		case FLOAT_TYPE:
			return Float.class;
		case LONG_TYPE:
			return Long.class;
		case DOUBLE_TYPE:
			return Double.class;
		case EMPTY_TYPE:
			return Object[].class;
		case LIST_TYPE:
			return Object[].class;
		case STRUCTURE_TYPE:
			String className = removeApices(prologTerm.getFunctor());
			try {
				return Class.forName(className);
			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundError(className, e);
			}
		default:
			throw new UnknownTermError(prologTerm);
		}
	}

	public Object toObject(PrologTerm prologTerm) {

		if (prologTerm == null) {
			return null;
		}

		switch (prologTerm.getType()) {
		case NIL_TYPE:
			return null;
		case TRUE_TYPE:
			return true;
		case FALSE_TYPE:
			return false;
		case EMPTY_TYPE:
			return new Object[0];
		case ATOM_TYPE:
			return removeApices(prologTerm.getFunctor());
		case INTEGER_TYPE:
			return ((PrologInteger) prologTerm).getIntValue();
		case FLOAT_TYPE:
			return ((PrologFloat) prologTerm).getFloatValue();
		case LONG_TYPE:
			return ((PrologLong) prologTerm).getLongValue();
		case DOUBLE_TYPE:
			return ((PrologDouble) prologTerm).getDoubleValue();
		case VARIABLE_TYPE:
			return null;
		case LIST_TYPE:
			return toObjectsArray(prologTerm.getArguments());
		case STRUCTURE_TYPE:

			PrologStructure prologStructure = (PrologStructure) prologTerm;

			Object object = null;

			// getting prolog structure functor that have complex atom syntax
			String functor = prologStructure.getFunctor();

			// class name is removed quotes of the complex functor syntax
			String className = removeApices(functor);

			try {

				// getting class from class map
				Class<?> classPtr = Class.forName(className);

				// creating new instance
				object = newInstance(classPtr);

				Deque<Field> stack = new ArrayDeque<Field>();

				while (classPtr != Object.class) {

					// getting declared fields
					Field[] fields = classPtr.getDeclaredFields();

					for (int i = fields.length - 1; i >= 0; i--) {

						// staking field for write
						// in FIFO order
						stack.push(fields[i]);

					}

					// update class pointer for the next super class
					classPtr = classPtr.getSuperclass();
				}

				PrologTerm[] prologArguments = prologStructure.getArguments();

				for (int i = 0; i < prologArguments.length && !stack.isEmpty(); i++) {

					//
					Field field = stack.pop();

					// recovery data object
					Object value = toObject(prologStructure.getArguments()[i]);

					// write field with argument value
					writeValue(field, object, value);

				}

			} catch (ClassNotFoundException e) {
				throw new ClassNotFoundError(className, e);
			}

			return object;

		default:
			throw new UnknownTermError(prologTerm);
		}
	}

	public PrologTerm toTerm(Object object) {

		// null pointer
		if (object == null) {
			return provider.prologNil();
		}

		// class data type
		else if (object instanceof Class) {
			return toStructure((Class<?>) object, null);
		}

		// string data type
		else if (object instanceof String) {
			return provider.newAtom("" + (String) object + "");
		}

		// primitives and wrappers data types
		else if (object.getClass() == boolean.class || object instanceof Boolean) {
			return (Boolean) object ? provider.prologTrue() : provider.prologFalse();
		} else if (object.getClass() == int.class || object instanceof Integer) {
			return provider.newInteger((Integer) object);
		} else if (object.getClass() == float.class || object instanceof Float) {
			return provider.newFloat((Float) object);
		} else if (object.getClass() == long.class || object instanceof Long) {
			return provider.newLong((Long) object);
		} else if (object.getClass() == double.class || object instanceof Double) {
			return provider.newDouble((Double) object);
		}

		//
		else if (object instanceof Object[]) {
			return provider.newList(toTermsArray((Object[]) object));
		} else {
			return toStructure(object.getClass(), object);
		}

	}

	public PrologTerm toTerm(Object object, Map<String, PrologTerm> solutionsMap) {

		// null pointer
		if (object == null) {
			return provider.prologNil();
		}

		// class data type
		else if (object instanceof Class) {
			return toStructure((Class<?>) object, null);
		}

		// string data type
		else if (object instanceof String) {
			return provider.newAtom("" + (String) object + "");
		}

		// primitives and wrappers data types
		else if (object.getClass() == boolean.class || object instanceof Boolean) {
			return (Boolean) object ? provider.prologTrue() : provider.prologFalse();
		} else if (object.getClass() == int.class || object instanceof Integer) {
			return provider.newInteger((Integer) object);
		} else if (object.getClass() == float.class || object instanceof Float) {
			return provider.newFloat((Float) object);
		} else if (object.getClass() == long.class || object instanceof Long) {
			return provider.newLong((Long) object);
		} else if (object.getClass() == double.class || object instanceof Double) {
			return provider.newDouble((Double) object);
		}

		//
		else if (object instanceof Object[]) {
			return provider.newList(toTermsArray((Object[]) object));
		} else {

			// retrieve object class
			Class<?> classPtr = object.getClass();

			// stack for resolve prolog structure arguments order
			Deque<PrologTerm> stack = new ArrayDeque<PrologTerm>();

			// class name to convert in predicate functor
			String className = classPtr.getName();

			// setting quotes to complex class name
			String functor = "'" + className + "'";

			// loop for resolve inheritance classes
			while (classPtr != Object.class) {

				// getting declared fields
				Field[] fields = classPtr.getDeclaredFields();

				for (int i = fields.length - 1; i >= 0; i--) {
					Field field = fields[i];

					if (isPersistent(field) && !isStaticAndFinal(field)) {

						String fieldName = field.getName();

						Object argument = readValue(field, object);

						// variable name = field name first char in upper case
						String variableName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

						if (argument == null) {
							if (solutionsMap.containsKey(variableName)) {
								stack.push(solutionsMap.get(variableName));
							} else {
								PrologVariable variableArgument = provider.newVariable(variableName, i);
								stack.push(variableArgument);
							}
						} else {
							PrologTerm prologArgument = toTerm(argument);
							stack.push(prologArgument);
							solutionsMap.put(variableName, prologArgument);
						}

					}

				}

				// update class pointer for the next super class
				classPtr = classPtr.getSuperclass();
			}

			PrologTerm[] prologArguments = new PrologTerm[stack.size()];
			for (int i = 0; i < prologArguments.length || !stack.isEmpty(); i++) {
				prologArguments[i] = stack.pop();
			}

			return provider.newStructure(functor, prologArguments);

		}
	}

	public PrologTerm[] toTermsArray(Object[] objects) {
		PrologTerm[] terms = new PrologTerm[objects.length];
		for (int i = 0; i < objects.length; i++) {
			terms[i] = toTerm(objects[i]);
		}
		return terms;
	}

	private PrologStructure toStructure(Class<?> clazz, Object object) {

		Class<?> classPtr = clazz;

		// stack for resolve prolog structure arguments order
		Deque<PrologTerm> stack = new ArrayDeque<PrologTerm>();

		// class name to convert in predicate functor
		String functor = "'" + classPtr.getName() + "'";

		// loop for resolve term inheritance classes
		while (classPtr != Object.class) {

			// getting declared fields
			Field[] fields = classPtr.getDeclaredFields();

			for (int i = fields.length - 1; i >= 0; i--) {
				Field field = fields[i];

				// check persistence condition
				if (isPersistent(field) && !isStaticAndFinal(field)) {

					String fieldName = field.getName();

					if (object != null) {

						Object argument = readValue(field, object);
						PrologTerm prologArgument = toTerm(argument);
						stack.push(prologArgument);

					} else {

						// variable name = field name first char in upper case
						String varName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);

						// pushing the variable name for create predicate
						stack.push(provider.newVariable(varName, i));
					}

				}

			}

			// update class pointer for the next super class
			classPtr = classPtr.getSuperclass();
		}

		PrologTerm[] prologArguments = new PrologTerm[stack.size()];
		for (int i = 0; i < prologArguments.length || !stack.isEmpty(); i++) {
			prologArguments[i] = stack.pop();
		}

		return provider.newStructure(functor, prologArguments);
	}

}
