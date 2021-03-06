/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.prolog;

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

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param <K> the key type
	 * @param <V> the value type
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedMap() {
		return new PrologLinkedMap<K, V>();
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedMap(int capacity) {
		return new PrologLinkedMap<K, V>(capacity);
	}

	/**
	 * Create a new java.util.LinkedHashMap.
	 * 
	 * @param          <K> the key type
	 * @param          <V> the value type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <K, V> Map<K, V> linkedMap(Map<? extends K, ? extends V> m) {
		return new PrologLinkedMap<K, V>(m);
	}

}
