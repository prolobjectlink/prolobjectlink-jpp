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
package org.prolobjectlink.db.util;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

public class JavaReflect {

	public static boolean isFinal(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isFinal(modifiers);
	}

	public static boolean isStatic(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isStatic(modifiers);
	}

	/**
	 * Check if the given field is {@code this} result of inner classes
	 * instantiation.
	 * 
	 * @param field field to check if is Synthetic
	 * @return true if the given field is Synthetic false in otherwise
	 * @since 1.0
	 */
	public static boolean isSynthetic(Field field) {
		return field.isSynthetic();
	}

	public static boolean isTransient(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isTransient(modifiers);
	}

	/**
	 * Check if the given field
	 * {@code !ObjectReflector#isTransient(Field)&&!ObjectReflector#isSynthetic(Field)}
	 * 
	 * @param field field to check if is Persistent
	 * @return true if the given field is Persistent false in otherwise
	 * @since 1.0
	 */
	public static boolean isPersistent(Field field) {
		return !isTransient(field) && !isSynthetic(field);
	}

	public static boolean isStaticAndFinal(Field field) {
		return isStatic(field) && isFinal(field);
	}

	/**
	 * Check if the given java class member (filed, method or constructor) is
	 * accessible or not.
	 * 
	 * @param member java member (filed, method or constructor) to be checked
	 * @return true if the given java class member is accessible, false if not.
	 * @since 1.0
	 */
	public static boolean isAccessible(AccessibleObject member) {
		return member.isAccessible();
	}

	public static void doAccessibleIfNeed(AccessibleObject member) {
		if (!isAccessible(member)) {
			member.setAccessible(true);
		}
	}

	public static void undoAccessibleIfNeed(AccessibleObject member) {
		if (!isAccessible(member)) {
			member.setAccessible(false);
		}
	}

	public static <O> Class<O> classOf(O o) {
		return (Class<O>) o.getClass();
	}

	public static Class<?> classForName(String className) {
		Class<?> clazz = null;
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.CLASS_NOT_FOUND, e);
		}
		return clazz;
	}

	/**
	 * Find some constructor for create an empty instance. This method create an
	 * object instance under critical situations. There are non-empty declared
	 * constructor, restricted access for alternative constructor with any number
	 * and type of parameters. The alternative constructor with any number and type
	 * of parameter will be create with an array with the default value (0 for
	 * primitive types, false for boolean and null for any class type). This is the
	 * way for invoke {@link Constructor#newInstance(Object...)}. The public empty
	 * constructor simplify the situation because no have additional parameters
	 * analysis.
	 * 
	 * @param clazz type to be create an instance
	 * @return instance from given class.
	 * @since 1.0
	 */
	public static Object newInstance(Class<?> clazz) {
		Object instance = null;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		if (constructors.length > 0) {
			Constructor<?> constructor = constructors[0];
			Class<?>[] classes = constructor.getParameterTypes();
			Object[] arguments = new Object[classes.length];
			for (int i = 0; i < classes.length; i++) {
				if (classes[i] == String.class) {
					arguments[i] = "";
				} else if (classes[i] == boolean.class) {
					arguments[i] = false;
				} else if (classes[i].isPrimitive()) {
					arguments[i] = 0;
				}
			}
			try {

				doAccessibleIfNeed(constructor);
				instance = constructor.newInstance(arguments);
				undoAccessibleIfNeed(constructor);

			} catch (InstantiationException e) {
				LoggerUtils.error(JavaReflect.class, LoggerConstants.INSTANTIATION, e);
			} catch (IllegalArgumentException e) {
				LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ARGUMENT, e);
			} catch (IllegalAccessException e) {
				LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ACCESS, e);
			} catch (InvocationTargetException e) {

				// This exception is reported when a constructor not
				// initialize directly yours fields, but make the job

			}

		}
		return instance;
	}

	public static <T> T newInstance(Class<T> cls, Class<?>[] parameterTypes, Object[] parameterValues) {
		T value = null;
		try {
			Constructor<T> constructor = cls.getDeclaredConstructor(parameterTypes);
			doAccessibleIfNeed(constructor);
			value = constructor.newInstance(parameterValues);
			undoAccessibleIfNeed(constructor);
		} catch (InstantiationException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.INSTANTIATION, e);
		} catch (IllegalAccessException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ACCESS, e);
		} catch (NoSuchMethodException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.NO_SUCH_METHOD, e);
		} catch (SecurityException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.SECURITY, e);
		} catch (IllegalArgumentException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ARGUMENT, e);
		} catch (InvocationTargetException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.INVOCATION_TARGET, e);
		}
		return value;
	}

	/**
	 * Read field value given some object owner
	 * 
	 * @param field  class field for read object value
	 * @param object object that contain the filed to read
	 * @return object value read from given field
	 * @since 1.0
	 */
	public static Object readValue(Field field, Object object) {
		Object value = null;
		try {
			doAccessibleIfNeed(field);
			value = field.get(object);
			undoAccessibleIfNeed(field);
		} catch (IllegalArgumentException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ARGUMENT, e);
		} catch (IllegalAccessException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ACCESS, e);
		}
		return value;
	}

	/**
	 * Write in a field value given some owner object and object value. This method
	 * treated an special case when try write an object array. In this case the
	 * array will be a copy with the array component type expected and defined in
	 * the filed.
	 * 
	 * @param field  class field for write object value
	 * @param object object field owner where will be wrote the value
	 * @param value  value to be write.
	 * @since 1.0
	 */
	public static void writeValue(Field field, Object object, Object value) {
		try {

			Class<?> clazz = field.getType();
			if (value == null) {
				if (clazz == boolean.class) {
					value = false;
				} else if (clazz == int.class) {
					value = 0;
				} else if (clazz == long.class) {
					value = 0L;
				} else if (clazz == float.class) {
					value = 0F;
				} else if (clazz == double.class) {
					value = 0D;
				}
			} else if (value instanceof Object[] && clazz != Object[].class) {
				value = castComponentType((Object[]) value, clazz);
			}

			doAccessibleIfNeed(field);
			field.set(object, value);
			undoAccessibleIfNeed(field);
		} catch (IllegalArgumentException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ARGUMENT, e);
		} catch (IllegalAccessException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ACCESS, e);
		}
	}

	/**
	 * Make a copy from given array to new array whit cast component type.
	 * 
	 * 
	 * @param array object array to be copied
	 * @param type  new component type
	 * @return new array of the class type
	 * @since 1.0
	 */
	public static Object castComponentType(Object[] array, Class<?> type) {
		Class<? extends Object[]> clazz = (Class<? extends Object[]>) type;
		return Arrays.copyOf(array, array.length, clazz);
	}

	public static Field getDeclaredField(Class<?> clazz, String attributeName) {
		Field field = null;
		try {
			field = clazz.getDeclaredField(attributeName);
		} catch (NoSuchFieldException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.NO_SUCH_FIELD, e);
		} catch (SecurityException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.SECURITY, e);
		}
		return field;
	}

	/**
	 * Invoke a method from some given owner object with given arguments.
	 * 
	 * @param object    method owner.
	 * @param method    method to be invoke.
	 * @param arguments signature method arguments required.
	 * @return object result of invoke the given method.
	 */
	public static Object invoke(Object object, Method method, Object... arguments) {
		Object ret = null;
		try {
			doAccessibleIfNeed(method);
			ret = method.invoke(object, arguments);
			undoAccessibleIfNeed(method);
		} catch (IllegalArgumentException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ARGUMENT, e);
		} catch (IllegalAccessException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.ILLEGAL_ACCESS, e);
		} catch (InvocationTargetException e) {
			LoggerUtils.error(JavaReflect.class, LoggerConstants.INVOCATION_TARGET, e);
		}
		return ret;
	}

	protected JavaReflect() {
	}

}
