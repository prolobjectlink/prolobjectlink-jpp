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
package org.logicware.prolog;

import java.util.Collection;
import java.util.Set;

final class PrologSets {

	private PrologSets() {
	}

	/**
	 * Create a new org.logicware.util.TreeSet.
	 * 
	 * @param <T>
	 *            the type
	 * @return the object
	 */
	public static <T extends Comparable<? super T>> Set<T> treeSet() {
		return new PrologTreeSet<T>();
	}

	/**
	 * Create a new org.logicware.util.TreeSet.
	 * 
	 * @param <T>
	 *            the type
	 * @param c
	 *            initial collection
	 * @return the object
	 */
	public static <T extends Comparable<? super T>> Set<T> treeSet(Collection<? extends T> c) {
		return new PrologTreeSet<T>(c);
	}

	/**
	 * Create a new org.logicware.util.HashSet.
	 * 
	 * @param <T>
	 *            the type
	 * @return the object
	 */
	public static <T> Set<T> hashSet() {
		return new PrologHashSet<T>();
	}

	/**
	 * Create a new org.logicware.util.HashSet.
	 * 
	 * @param <T>
	 *            the type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <T> Set<T> hashSet(int capacity) {
		return new PrologHashSet<T>(capacity);
	}

	/**
	 * Create a new org.logicware.util.HashSet.
	 * 
	 * @param <T>
	 *            the type
	 * @param c
	 *            initial collection
	 * @return the object
	 */
	public static <T> Set<T> hashSet(Collection<? extends T> c) {
		return new PrologHashSet<T>(c);
	}

}
