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

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;

public final class JavaSets {

	private JavaSets() {
	}

	/**
	 * Create a new TreeSet.
	 * 
	 * @param <T> the type
	 * @return the object
	 */
	public static <T extends Comparable<? super T>> Set<T> treeSet(Collection<? extends T> c) {
		return new TreeSet<T>(c);
	}

	/**
	 * Create a new TreeSet.
	 * 
	 * @param <T> the type
	 * @return the object
	 */
	public static <T extends Comparable<? super T>> Set<T> treeSet() {
		return new TreeSet<T>();
	}

	/**
	 * Create a new HashSet.
	 * 
	 * @param <T> the type
	 * @return the object
	 */
	public static <T> Set<T> hashSet() {
		return new HashSet<T>();
	}

	/**
	 * Create a new LinkedHashSet.
	 * 
	 * @param <T> the type
	 * @return the object
	 */
	public static <T> Set<T> linkedHashSet() {
		return new LinkedHashSet<T>();
	}

	/**
	 * Create a new HashSet.
	 * 
	 * @param          <T> the type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <T> Set<T> hashSet(int capacity) {
		return new HashSet<T>(capacity);
	}

	/**
	 * Create a new LinkedHashSet.
	 * 
	 * @param          <T> the type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <T> Set<T> linkedHashSet(int capacity) {
		return new LinkedHashSet<T>(capacity);
	}

	/**
	 * Create a new HashSet.
	 * 
	 * @param          <T> the type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <T> Set<T> hashSet(Collection<? extends T> c) {
		return new HashSet<T>(c);
	}

}
