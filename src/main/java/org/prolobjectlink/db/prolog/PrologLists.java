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
