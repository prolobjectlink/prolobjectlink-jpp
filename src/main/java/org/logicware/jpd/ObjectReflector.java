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
package org.logicware.jpd;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.logicware.jpi.InstantiationError;
import org.logicware.jpp.ClassNotFoundError;
import org.logicware.jpp.IllegalAccessError;
import org.logicware.jpp.IllegalArgumentError;
import org.logicware.jpp.InvocationTargetError;
import org.logicware.jpp.NoSuchFieldError;
import org.logicware.jpp.SecurityError;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class ObjectReflector {

	public final boolean isFinal(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isFinal(modifiers);
	}

	public final boolean isStatic(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isStatic(modifiers);
	}

	/**
	 * Check if the given field is {@code this} result of inner classes
	 * instantiation.
	 * 
	 * @param field
	 *            field to check if is Synthetic
	 * @return true if the given field is Synthetic false in otherwise
	 * @since 1.0
	 */
	public final boolean isSynthetic(Field field) {
		return field.isSynthetic();
	}

	public final boolean isTransient(Field field) {
		int modifiers = field.getModifiers();
		return Modifier.isTransient(modifiers);
	}

	/**
	 * Check if the given field
	 * {@code !ObjectReflector#isTransient(Field)&&!ObjectReflector#isSynthetic(Field)}
	 * 
	 * @param field
	 *            field to check if is Persistent
	 * @return true if the given field is Persistent false in otherwise
	 * @since 1.0
	 */
	public final boolean isPersistent(Field field) {
		return !isTransient(field) && !isSynthetic(field);
	}

	public final boolean isStaticAndFinal(Field field) {
		return isStatic(field) && isFinal(field);
	}

	public final Class<?> classForName(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new ClassNotFoundError(className, e);
		}
	}

	/**
	 * Find some constructor for create an empty instance. This method create an
	 * object instance under critical situations. They can be non-empty declared
	 * constructor, restricted access for alternative constructor with any
	 * number and type of parameter. The alternative constructor with any number
	 * and type of parameter will be create with an array with the default value
	 * (0 for primitive types, false for boolean and null for any class type).
	 * This is the way for invoke {@link Constructor#newInstance(Object...)}.
	 * The public empty constructor simplify the situation because no have
	 * additional parameters analysis.
	 * 
	 * @param clazz
	 *            type to be create an instance
	 * @return instance from given class.
	 * @since 1.0
	 */
	public final Object newInstance(Class<?> clazz) {
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			try {

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

				constructor.setAccessible(true);
				return constructor.newInstance(arguments);

			} catch (InstantiationException e) {
				throw new InstantiationError(getClass(), clazz.getName(), e);
			} catch (IllegalAccessException e) {
				throw new IllegalAccessError(clazz.getName(), e);
			} catch (IllegalArgumentException e) {
				throw new IllegalArgumentError(constructor.getName(), clazz.getName(), e);
			} catch (InvocationTargetException e) {

				// This exception is reported when a constructor not
				// initialize directly yours fields, but make the job

			} finally {
				constructor.setAccessible(false);
			}

		}
		throw new InstantiationError(getClass(), clazz.getName());
	}

	/**
	 * Read field value given some object owner
	 * 
	 * @param field
	 *            class field for read object value
	 * @param object
	 *            object that contain the filed to read
	 * @return object value read from given field
	 * @since 1.0
	 */
	public final Object readValue(Field field, Object object) {
		try {
			field.setAccessible(true);
			return field.get(object);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentError(field.getName(), object.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessError(object.getClass().getName(), e);
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * Write in a field value given some owner object and object value. This
	 * method treated an special case when try write an object array. In this
	 * case the array will be a copy with the array component type expected and
	 * defined in the filed.
	 * 
	 * @param field
	 *            class field for write object value
	 * @param object
	 *            object field owner where will be wrote the value
	 * @param value
	 *            value to be write.
	 * @since 1.0
	 */
	public final void writeValue(Field field, Object object, Object value) {
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

			field.setAccessible(true);
			field.set(object, value);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentError(field.getName(), object.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessError(object.getClass().getName(), e);
		} finally {
			field.setAccessible(false);
		}
	}

	/**
	 * Make a copy from given array to new array whit component type given like
	 * argument.
	 * 
	 * 
	 * @param array
	 *            object array to be copied
	 * @param type
	 *            new component type
	 * @since 1.0
	 * @return new array of the class type
	 * 
	 */
	public final Object castComponentType(Object[] array, Class<?> type) {
		Class<? extends Object[]> clazz = (Class<? extends Object[]>) type;
		return Arrays.copyOf(array, array.length, clazz);
	}

	public final Field getDeclaredField(Class<?> clazz, String attributeName) {
		try {
			return clazz.getDeclaredField(attributeName);
		} catch (NoSuchFieldException e) {
			throw new NoSuchFieldError(attributeName, clazz, e);
		} catch (SecurityException e) {
			throw new SecurityError(clazz.getName(), e);
		}
	}

	/**
	 * Invoke a method from some given owner object with given arguments.
	 * 
	 * @param object
	 *            method owner.
	 * @param method
	 *            method to be invoke.
	 * @param arguments
	 *            signature method arguments required.
	 * @return object result of invoke the given method.
	 */
	public final Object invoke(Object object, Method method, Object... arguments) {
		try {
			method.setAccessible(true);
			return method.invoke(object, arguments);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentError(method.getName(), object.getClass().getName(), e);
		} catch (IllegalAccessException e) {
			throw new IllegalAccessError(object.getClass().getName(), e);
		} catch (InvocationTargetException e) {
			throw new InvocationTargetError(object.getClass().getName(), e);
		} finally {
			method.setAccessible(false);
		}
	}
}
