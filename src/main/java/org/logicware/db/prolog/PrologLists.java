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
package org.logicware.db.prolog;

import java.util.Collection;
import java.util.List;
import java.util.Queue;

final class PrologLists {

	private PrologLists() {
	}

	/**
	 * Creates a new empty stack.
	 * 
	 * @return an empty stack.
	 * @since 1.0
	 */
	public static <T> PrologStack<T> stack() {
		return new PrologStack<T>();
	}

	public static <T> PrologStack<T> stack(Collection<T> c) {
		return new PrologStack<T>(c);
	}

	/**
	 * Creates a new empty vector
	 * 
	 * @return an empty stack
	 * @since 1.0
	 */
	public static <T> PrologVector<T> vector() {
		return new PrologVector<T>();
	}

	public static <T> PrologVector<T> vector(Collection<T> c) {
		return new PrologVector<T>(c);
	}

	/**
	 * Create a new org.logicware.util.ArrayList.
	 * 
	 * @param <T> the type
	 * @return the object
	 */
	public static <T> List<T> arrayList() {
		return new PrologArrayList<T>();
	}

	/**
	 * Create a new org.logicware.util.ArrayList.
	 * 
	 * @param          <T> the type
	 * @param capacity the initial capacity
	 * @return the object
	 */
	public static <T> List<T> arrayList(int capacity) {
		return new PrologArrayList<T>(capacity);
	}

	/**
	 * Create a new org.logicware.util.ArrayList.
	 * 
	 * @param   <T> the type
	 * @param c the collection
	 * @return the object
	 */
	public static <T> List<T> arrayList(Collection<T> c) {
		return new PrologArrayList<T>(c);
	}

	public static <T> List<T> linkedList() {
		return new PrologLinkedList<T>();
	}

	public static <T> List<T> linkedList(Collection<T> c) {
		return new PrologLinkedList<T>(c);
	}

	/**
	 * Creates a new empty priority queue of natural order items.
	 * 
	 * @return an empty priority queue of natural order items.
	 * @since 1.0
	 */
	public static <T> Queue<T> priorityQueue() {
		return new PrologPriorityQueue<T>();
	}

	/**
	 * Creates a new empty priority queue of natural order items and specified
	 * capacity.
	 * 
	 * @return an empty priority queue of natural order items and specified
	 *         capacity.
	 * @since 1.0
	 */
	public static <T> Queue<T> priorityQueue(int capacity) {
		return new PrologPriorityQueue<T>(capacity);
	}

	public static <T> Queue<T> priorityQueue(Collection<T> c) {
		return new PrologPriorityQueue<T>(c);
	}

}
