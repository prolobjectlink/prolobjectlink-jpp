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
package io.github.prolobjectlink.db.util;

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
	 * @param c initial collection
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
	 * Create a new HashSet.
	 * 
	 * @param            <T> the type
	 * @param c to be include in returning set
	 * @return the object
	 */
	public static <T> Set<T> hashSet(Collection<? extends T> c) {
		return new HashSet<T>(c);
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
	 * Create a new LinkedHashSet.
	 * 
	 * @param            <T> the type
	 * @param c to be include in returning set
	 * @return the object
	 */
	public static <T> Set<T> linkedHashSet(Collection<? extends T> c) {
		return new LinkedHashSet<T>(c);
	}

}
