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
package org.logicware.prolog;

import static org.logicware.prolog.PrologTermType.ATOM_TYPE;
import static org.logicware.prolog.PrologTermType.DOUBLE_TYPE;
import static org.logicware.prolog.PrologTermType.EMPTY_TYPE;
import static org.logicware.prolog.PrologTermType.FALSE_TYPE;
import static org.logicware.prolog.PrologTermType.FLOAT_TYPE;
import static org.logicware.prolog.PrologTermType.INTEGER_TYPE;
import static org.logicware.prolog.PrologTermType.LIST_TYPE;
import static org.logicware.prolog.PrologTermType.LONG_TYPE;
import static org.logicware.prolog.PrologTermType.NIL_TYPE;
import static org.logicware.prolog.PrologTermType.STRUCTURE_TYPE;
import static org.logicware.prolog.PrologTermType.TRUE_TYPE;
import static org.logicware.prolog.PrologTermType.VARIABLE_TYPE;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.logicware.AbstractConverter;
import org.logicware.ClassNotFoundError;
import org.logicware.ObjectConverter;
import org.logicware.util.JavaLists;
import org.logicware.util.JavaMaps;
import org.logicware.util.JavaSets;

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

			// getting class from structure
			Class<?> structureClass = toClass(prologTerm);

			//
			Object object = null;

			// getting class from structure
			Class<?> classPtr = structureClass;

			// creating new instance
			object = newInstance(classPtr);

			Deque<Field> stack = new ArrayDeque<Field>();

			while (classPtr != null && classPtr != Object.class) {

				// getting declared fields
				Field[] fields = classPtr.getDeclaredFields();

				for (int i = fields.length - 1; i >= 0; i--) {
					Field field = fields[i];

					// check persistence condition
					if (isPersistent(field) && !isStaticAndFinal(field)) {
						stack.push(field);
					}

				}

				// update class pointer for the next super class
				classPtr = classPtr.getSuperclass();
			}

			PrologTerm[] prologArguments = prologTerm.getArguments();

			for (int i = 0; i < prologArguments.length && !stack.isEmpty(); i++) {

				//
				Field field = stack.pop();

				// recovery data object
				Object value = toObject(prologTerm.getArguments()[i]);

				// write field with argument value
				writeValue(field, object, value);

			}

			// java.util.date transformations
			if (structureClass == org.logicware.prolog.PrologDate.class) {
				return new Date(((PrologDate) object).getTime());
			}

			// java.util.collections transformations
			else if (structureClass == org.logicware.prolog.PrologArrayList.class) {
				return JavaLists.arrayList((org.logicware.prolog.PrologArrayList<?>) object);
			} else if (structureClass == org.logicware.prolog.PrologHashMap.class) {
				return JavaMaps.hashMap((org.logicware.prolog.PrologHashMap<?, ?>) object);
			} else if (structureClass == org.logicware.prolog.PrologHashSet.class) {
				return JavaSets.hashSet((org.logicware.prolog.PrologHashSet<?>) object);
			} else if (structureClass == org.logicware.prolog.PrologTreeMap.class) {
				return JavaMaps.treeMap((org.logicware.prolog.PrologTreeMap) object);
			} else if (structureClass == org.logicware.prolog.PrologTreeSet.class) {
				return JavaSets.treeSet((org.logicware.prolog.PrologTreeSet) object);
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

		// object array
		else if (object instanceof Object[]) {
			return provider.newList(toTermsArray((Object[]) object));
		}

		// java.util.date transformations
		else if (object instanceof Date) {
			return toTerm(new PrologDate(((Date) object).getTime()));
		}

		// java.util.collections transformations
		else if (object instanceof ArrayList) {
			ArrayList<?> l = (ArrayList<?>) object;
			return toTerm(PrologLists.arrayList(l));
		} else if (object instanceof HashMap) {
			HashMap<?, ?> m = (HashMap<?, ?>) object;
			return toTerm(PrologMaps.hashMap(m));
		} else if (object instanceof HashSet) {
			HashSet<?> s = (HashSet<?>) object;
			return toTerm(PrologSets.hashSet(s));
		} else if (object instanceof TreeMap) {
			TreeMap m = (TreeMap) object;
			return toTerm(PrologMaps.treeMap(m));
		} else if (object instanceof TreeSet) {
			TreeSet s = (TreeSet) object;
			return toTerm(PrologSets.treeSet(s));
		}

		// structure default case
		else {

			PrologStructure structure = toStructure(object.getClass(), object);

			return structure;
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
			while (classPtr != null && classPtr != Object.class) {

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
		while (classPtr != null && classPtr != Object.class) {

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
