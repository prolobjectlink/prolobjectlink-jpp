/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */

package org.prolobjectlink.db.util;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

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

	public static <K, V> Map<K, V> nonEmpty(Map<K, V> m) {
		if (m.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return m;
	}

	public static <K, V> Map<K, V> nonEmpty(Map<K, V> m, String message) {
		if (m.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return m;
	}

	public static <E> Collection<E> nonEmpty(Collection<E> c) {
		if (c.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return c;
	}

	public static <E> Collection<E> nonEmpty(Collection<E> c, String message) {
		if (c.isEmpty()) {
			throw new IllegalArgumentException(message);
		}
		return c;
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
