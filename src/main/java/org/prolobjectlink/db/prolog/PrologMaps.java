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
package org.prolobjectlink.db.prolog;

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
