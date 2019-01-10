/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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

import java.util.Map;

final class PrologMaps {

	private PrologMaps() {
	}

	/**
	 * Create a new org.logicware.util.TreeMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap() {
		return new PrologTreeMap<K, V>();
	}

	/**
	 * Create a new org.logicware.util.TreeMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap(Map<K, V> m) {
		return new PrologTreeMap<K, V>(m);
	}

	/**
	 * Create a new org.logicware.util.HashMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap() {
		return new PrologHashMap<K, V>();
	}

	/**
	 * Create a new org.logicware.util.HashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(int capacity) {
		return new PrologHashMap<K, V>(capacity);
	}

	/**
	 * Create a new org.logicware.util.HashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(Map<? extends K, ? extends V> m) {
		return new PrologHashMap<K, V>(m);
	}

}
