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
