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
package org.logicware.pdb.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public final class JavaMaps {

	private JavaMaps() {
	}

	/**
	 * Create a new java.util.TreeMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap() {
		return new TreeMap<K, V>();
	}

	/**
	 * Create a new java.util.TreeMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap(Map<? extends K, ? extends V> m) {
		return new TreeMap<K, V>(m);
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(int capacity) {
		return new HashMap<K, V>(capacity);
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(Map<? extends K, ? extends V> m) {
		return new HashMap<K, V>(m);
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap() {
		return new LinkedHashMap<K, V>();
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap(int capacity) {
		return new LinkedHashMap<K, V>(capacity);
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param <K>
	 *            the key type
	 * @param <V>
	 *            the value type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap(Map<? extends K, ? extends V> m) {
		return new LinkedHashMap<K, V>(m);
	}

}
