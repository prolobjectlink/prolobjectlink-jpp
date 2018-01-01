/*
 * #%L
 * prolobjectlink
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
package org.logicware.util;

import java.util.Collection;

/**
 * Factory class to create news {@link ReadWriteSet}
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see ReadWriteSet
 * @see ReadWriteHashSet
 * @see ReadWriteLinkedHashSet
 *
 */
public final class ReadWriteCollections {

	private ReadWriteCollections() {
	}

	public static <E> ReadWriteList<E> newReadWriteArrayList() {
		return new ReadWriteArrayList<E>();
	}

	public static <E> ReadWriteList<E> newReadWriteArrayList(int initialCapacity) {
		return new ReadWriteArrayList<E>(initialCapacity);
	}

	public static <E> ReadWriteList<E> newReadWriteArrayList(E[] array) {
		ReadWriteList<E> arrayList = new ReadWriteArrayList<E>(array.length);
		for (int i = 0; i < array.length; i++) {
			arrayList.add(array[i]);
		}
		return arrayList;
	}

	public static <E> ReadWriteList<E> newReadWriteArrayList(Collection<? extends E> c) {
		return new ReadWriteArrayList<E>(c);
	}

	public static <E> ReadWriteList<E> newReadWriteLinkedList() {
		return new ReadWriteLinkedList<E>();
	}

	public static <E> ReadWriteList<E> newReadWriteLinkedList(Collection<? extends E> c) {
		return new ReadWriteLinkedList<E>(c);
	}

	public static <E> ReadWriteSet<E> newReadWriteHashSet() {
		return new ReadWriteHashSet<E>();
	}

	public static <E> ReadWriteSet<E> newReadWriteHashSet(int initialCapacity) {
		return new ReadWriteHashSet<E>(initialCapacity);
	}

	public static <E> ReadWriteSet<E> newReadWriteHashSet(int initialCapacity, float loadFactor) {
		return new ReadWriteHashSet<E>(initialCapacity, loadFactor);
	}

	public static <E> ReadWriteSet<E> newReadWriteHashSet(Collection<? extends E> c) {
		return new ReadWriteHashSet<E>(c);
	}

	public static <E> ReadWriteSet<E> newReadWriteLinkedHashSet() {
		return new ReadWriteLinkedHashSet<E>();
	}

	public static <E> ReadWriteSet<E> newReadWriteLinkedHashSet(int initialCapacity) {
		return new ReadWriteLinkedHashSet<E>(initialCapacity);
	}

	public static <E> ReadWriteSet<E> newReadWriteLinkedHashSet(int initialCapacity, float loadFactor) {
		return new ReadWriteLinkedHashSet<E>(initialCapacity, loadFactor);
	}

	public static <E> ReadWriteSet<E> newReadWriteLinkedHashSet(Collection<? extends E> c) {
		return new ReadWriteLinkedHashSet<E>(c);
	}

}
