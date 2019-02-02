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
