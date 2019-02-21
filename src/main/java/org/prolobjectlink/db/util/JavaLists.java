/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

public final class JavaLists {

	private JavaLists() {
	}

	/**
	 * Creates a new empty stack.
	 * 
	 * @return an empty stack.
	 * @since 1.0
	 */
	public static <T> Stack<T> stack() {
		return new Stack<T>();
	}

	public static <T> Stack<T> stack(Collection<T> c) {
		Stack<T> stack = new Stack<T>();
		for (T t : c) {
			stack.push(t);
		}
		return stack;
	}

	/**
	 * Creates a new empty vector
	 * 
	 * @return an empty stack
	 * @since 1.0
	 */
	public static <T> Vector<T> vector() {
		return new Vector<T>();
	}

	public static <T> Vector<T> vector(Collection<T> c) {
		return new Vector<T>(c);
	}

	/**
	 * Create a new java.util.ArrayList.
	 * 
	 * @param <T> the type
	 * @return the object
	 * @since 1.0
	 */
	public static <T> List<T> arrayList() {
		return new ArrayList<T>();
	}

	/**
	 * Create a new java.util.ArrayList.
	 * 
	 * @param          <T> the type
	 * @param capacity the initial capacity
	 * @return the object
	 * @since 1.0
	 */
	public static <T> List<T> arrayList(int capacity) {
		return new ArrayList<T>(capacity);
	}

	/**
	 * Create a new java.util.ArrayList.
	 * 
	 * @param   <T> the type
	 * @param c the collection
	 * @return the object
	 * @since 1.0
	 */
	public static <T> List<T> arrayList(Collection<T> c) {
		return new ArrayList<T>(c);
	}

	public static <T> List<T> linkedList() {
		return new LinkedList<T>();
	}

	public static <T> List<T> linkedList(Collection<T> c) {
		return new LinkedList<T>(c);
	}

	/**
	 * Creates a new empty priority queue of natural order items.
	 * 
	 * @return an empty priority queue of natural order items.
	 * @since 1.0
	 */
	public static <T> Queue<T> priorityQueue() {
		return new PriorityQueue<T>();
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
		return new PriorityQueue<T>(capacity);
	}

	public static <T> Queue<T> priorityQueue(Collection<T> c) {
		return new PriorityQueue<T>(c);
	}

}
