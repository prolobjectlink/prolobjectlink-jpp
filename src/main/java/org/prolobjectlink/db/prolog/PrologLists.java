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
package org.prolobjectlink.db.prolog;

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
