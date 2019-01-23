/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.worklogic.db.prolog;

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
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;

import org.logicware.prolog.PrologDouble;
import org.logicware.prolog.PrologFloat;
import org.logicware.prolog.PrologInteger;
import org.logicware.prolog.PrologLong;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologStructure;
import org.logicware.prolog.PrologTerm;
import org.logicware.prolog.PrologVariable;
import org.logicware.prolog.UnknownTermError;
import org.worklogic.ArrayStack;
import org.worklogic.Stack;
import org.worklogic.db.AbstractConverter;
import org.worklogic.db.ObjectConverter;
import org.worklogic.db.util.Assertions;
import org.worklogic.db.util.JavaLists;
import org.worklogic.db.util.JavaMaps;
import org.worklogic.db.util.JavaReflect;
import org.worklogic.db.util.JavaSets;
import org.worklogic.logging.LoggerConstants;
import org.worklogic.logging.LoggerUtils;

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
				LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
			}
			return null;
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
			object = JavaReflect.newInstance(classPtr);

			Stack<Field> stack = new ArrayStack<Field>();

			while (classPtr != null && classPtr != Object.class) {

				// getting declared fields
				Field[] fields = classPtr.getDeclaredFields();

				for (int i = fields.length - 1; i >= 0; i--) {
					Field field = fields[i];

					// check persistence condition
					if (JavaReflect.isPersistent(field) && !JavaReflect.isStaticAndFinal(field)) {
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
				JavaReflect.writeValue(field, object, value);

			}

			// java.util.date transformations
			if (structureClass == PrologDate.class) {
				return ((PrologDate) object).getJavaUtilDate();
			}

			// java.sql.time transformations
			else if (structureClass == Time.class) {
				return ((PrologTime) object).getJavaSqlTime();
			}

			// java.sql.time transformations
			else if (structureClass == Timestamp.class) {
				return ((PrologTimestamp) object).getJavaSqlTimestamp();
			}

			// java.util.locale transformations
			else if (structureClass == PrologLocale.class) {
				return ((PrologLocale) object).getJavaUtilLocale();
			}

			// java.util.currency transformations
			else if (structureClass == PrologCurrency.class) {
				return ((PrologCurrency) object).getJavaUtilCurrency();
			}

			// java.util.calendar transformations
			else if (structureClass == PrologCalendar.class) {
				return ((PrologCalendar) object).getJavaUtilCalendar();
			}

			// java.util.collections transformations
			else if (structureClass == PrologVector.class) {
				return JavaLists.vector((PrologVector<?>) object);
			} else if (structureClass == PrologStack.class) {
				return JavaLists.stack((PrologStack<?>) object);
			} else if (structureClass == PrologArrayList.class) {
				return JavaLists.arrayList((PrologArrayList<?>) object);
			} else if (structureClass == PrologLinkedList.class) {
				return JavaLists.linkedList((PrologLinkedList<?>) object);
			} else if (structureClass == PrologPriorityQueue.class) {
				return JavaLists.priorityQueue((PrologPriorityQueue<?>) object);
			} else if (structureClass == PrologHashMap.class) {
				return JavaMaps.hashMap((PrologHashMap<?, ?>) object);
			} else if (structureClass == PrologHashSet.class) {
				return JavaSets.hashSet((PrologHashSet<?>) object);
			} else if (structureClass == PrologTreeMap.class) {
				return JavaMaps.treeMap((PrologTreeMap) object);
			} else if (structureClass == PrologTreeSet.class) {
				return JavaSets.treeSet((PrologTreeSet) object);
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

		// java.sql.time transformations
		else if (object instanceof Time) {
			return toTerm(new PrologTime(((Time) object).getTime()));
		}

		// java.sql.timestamp transformations
		else if (object instanceof Timestamp) {
			return toTerm(new PrologTime(((Timestamp) object).getTime()));
		}

		// java.util.locale transformations
		else if (object instanceof Locale) {
			Locale locale = (Locale) object;
			String l = locale.getLanguage();
			String c = locale.getCountry();
			String v = locale.getVariant();
			return toTerm(new PrologLocale(l, c, v));
		}

		// java.util.currency transformations
		else if (object instanceof Currency) {
			Currency currency = (Currency) object;
			String c = currency.getCurrencyCode();
			return toTerm(new PrologCurrency(c));
		}

		// java.util.calendar transformations
		else if (object instanceof Calendar) {
			Calendar c = (Calendar) object;
			int minimalDaysInFirstWeek = c.getMinimalDaysInFirstWeek();
			int firstDayOfWeek = c.getFirstDayOfWeek();
			long timeInMillis = c.getTimeInMillis();
			boolean lenient = c.isLenient();
			return toTerm(new PrologCalendar(lenient, timeInMillis, firstDayOfWeek, minimalDaysInFirstWeek));
		}

		// java.util.collections transformations
		else if (object instanceof Vector) {
			Vector<?> l = (Vector<?>) object;
			return toTerm(PrologLists.vector(l));
		} else if (object instanceof java.util.Stack) {
			java.util.Stack<?> l = (java.util.Stack<?>) object;
			return toTerm(PrologLists.stack(l));
		} else if (object instanceof ArrayList) {
			ArrayList<?> l = (ArrayList<?>) object;
			return toTerm(PrologLists.arrayList(l));
		} else if (object instanceof LinkedList) {
			LinkedList<?> l = (LinkedList<?>) object;
			return toTerm(PrologLists.linkedList(l));
		} else if (object instanceof PriorityQueue) {
			PriorityQueue<?> l = (PriorityQueue<?>) object;
			return toTerm(PrologLists.priorityQueue(l));
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

			// check non empty attribute class or non persistent fields
			classPtr = Assertions.persistent(classPtr, "Non persistent " + classPtr);
			classPtr = Assertions.nonStaticFinal(classPtr, "Non persistent " + classPtr);

			// stack for resolve prolog structure arguments order
			Stack<PrologTerm> stack = new ArrayStack<PrologTerm>();

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

					if (JavaReflect.isPersistent(field) && !JavaReflect.isStaticAndFinal(field)) {

						String fieldName = field.getName();

						Object argument = JavaReflect.readValue(field, object);

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

		// check non empty attribute class or non persistent fields
		classPtr = Assertions.persistent(classPtr, "Non persistent " + classPtr);
		classPtr = Assertions.nonStaticFinal(classPtr, "Non persistent " + classPtr);

		// stack for resolve prolog structure arguments order
		Stack<PrologTerm> stack = new ArrayStack<PrologTerm>();

		// class name to convert in predicate functor
		String functor = "'" + classPtr.getName() + "'";

		// loop for resolve term inheritance classes
		while (classPtr != null && classPtr != Object.class) {

			// getting declared fields
			Field[] fields = classPtr.getDeclaredFields();

			for (int i = fields.length - 1; i >= 0; i--) {
				Field field = fields[i];

				// check persistence condition
				if (JavaReflect.isPersistent(field) && !JavaReflect.isStaticAndFinal(field)) {

					String fieldName = field.getName();

					if (object != null) {

						Object argument = JavaReflect.readValue(field, object);
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
