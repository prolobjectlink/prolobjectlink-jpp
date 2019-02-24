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
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap() {
		return new TreeMap<K, V>();
	}

	/**
	 * Create a new java.util.TreeMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> treeMap(Map<? extends K, ? extends V> m) {
		return new TreeMap<K, V>(m);
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap() {
		return new HashMap<K, V>();
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(int capacity) {
		return new HashMap<K, V>(capacity);
	}

	/**
	 * Create a new java.util.HashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> hashMap(Map<? extends K, ? extends V> m) {
		return new HashMap<K, V>(m);
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap() {
		return new LinkedHashMap<K, V>();
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap(int capacity) {
		return new LinkedHashMap<K, V>(capacity);
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedHashMap(Map<? extends K, ? extends V> m) {
		return new LinkedHashMap<K, V>(m);
	}

}
