/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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

package org.logicware.database.util;

import java.lang.reflect.Field;

public final class Assertions {

	public static <T> T notNull(T object) {
		if (object == null) {
			throw new NullPointerException();
		}
		return object;
	}

	public static <T> T notNull(T object, String message) {
		if (object == null) {
			throw new NullPointerException(message);
		}
		return object;
	}

	public static <T> T requireNotNull(T object) {
		if (object == null) {
			throw new IllegalArgumentException();
		}
		return object;
	}

	public static <T> T requireNotNull(T object, String message) {
		if (object == null) {
			throw new IllegalArgumentException(message);
		}
		return object;
	}

	public static <T> T requireNotEqualsRef(T target, T refernce) {
		if (target == refernce) {
			throw new IllegalArgumentException();
		}
		return target;
	}

	public static <T> T requireNotEqualsRef(T target, T refernce, String message) {
		if (target == refernce) {
			throw new IllegalArgumentException(message);
		}
		return target;
	}

	public static int checkIndex(int index, int size) {
		return checkIndex(index, size, "");
	}

	public static int checkIndex(int index, int size, String message) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(message);
		}
		return index;
	}

	public static Class<?> nonEmpty(Class<?> target) {
		if (target.getDeclaredFields().length <= 0) {
			throw new IllegalArgumentException();
		}
		return target;
	}

	public static Class<?> nonEmpty(Class<?> target, String message) {
		if (target.getDeclaredFields().length <= 0) {
			throw new IllegalArgumentException(message);
		}
		return target;
	}

	/**
	 * Check that the given class are not field empty or not have persistent fields.
	 * 
	 * @param target  the given class reference
	 * @param message {@link IllegalArgumentException} message if it is threw.
	 * @return the given class reference or thrown {@link IllegalArgumentException}
	 * @since 1.0
	 */
	public static Class<?> persistent(Class<?> target) {
		Field[] fields = target.getDeclaredFields();
		if (fields.length <= 0) {
			throw new IllegalArgumentException();
		}

		// check at least persistent filed
		for (int i = 0; i < fields.length; i++) {
			if (JavaReflect.isPersistent(fields[i])) {
				return target;
			}
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Check that the given class are not field empty or not have persistent fields.
	 * 
	 * @param target  the given class reference
	 * @param message {@link IllegalArgumentException} message if it is threw.
	 * @return the given class reference or thrown {@link IllegalArgumentException}
	 * @since 1.0
	 */
	public static Class<?> persistent(Class<?> target, String message) {
		Field[] fields = target.getDeclaredFields();
		if (fields.length <= 0) {
			throw new IllegalArgumentException(message);
		}

		// check at least persistent filed
		for (int i = 0; i < fields.length; i++) {
			if (JavaReflect.isPersistent(fields[i])) {
				return target;
			}
		}
		throw new IllegalArgumentException(message);
	}

	// check at least persistent filed
	public static Class<?> nonStaticFinal(Class<?> target) {
		Class<?> classPtr = target;
		while (classPtr != null && classPtr != Object.class) {
			Field[] fields = classPtr.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (!JavaReflect.isStaticAndFinal(fields[i])) {
					return target;
				}
			}
			classPtr = classPtr.getSuperclass();
		}
		throw new IllegalArgumentException();
	}

	// check at least persistent filed
	public static Class<?> nonStaticFinal(Class<?> target, String message) {
		Class<?> classPtr = target;
		while (classPtr != null && classPtr != Object.class) {
			Field[] fields = classPtr.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				if (!JavaReflect.isStaticAndFinal(fields[i])) {
					return target;
				}
			}
			classPtr = classPtr.getSuperclass();
		}
		throw new IllegalArgumentException(message);
	}

	private Assertions() {
	}
}
