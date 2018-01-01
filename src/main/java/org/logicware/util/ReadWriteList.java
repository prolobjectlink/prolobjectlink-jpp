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
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class ReadWriteList<E> implements List<E> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock writeLock = lock.writeLock();
	private final Lock readLock = lock.readLock();
	private final List<E> list;

	public ReadWriteList(List<E> list) {
		this.list = list;
	}

	public final int size() {
		readLock.lock();
		try {
			return list.size();
		} finally {
			readLock.unlock();
		}
	}

	public final boolean isEmpty() {
		readLock.lock();
		try {
			return list.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	public final boolean contains(Object o) {
		readLock.lock();
		try {
			return list.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	public final Iterator<E> iterator() {
		return new ReadWriteIterator();
	}

	public final Object[] toArray() {
		readLock.lock();
		try {
			return list.toArray();
		} finally {
			readLock.unlock();
		}
	}

	public final <T> T[] toArray(T[] a) {
		readLock.lock();
		try {
			return list.toArray(a);
		} finally {
			readLock.unlock();
		}
	}

	public final boolean add(E e) {
		writeLock.lock();
		try {
			return list.add(e);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean remove(Object o) {
		writeLock.lock();
		try {
			return list.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean containsAll(Collection<?> c) {
		readLock.lock();
		try {
			return list.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	public final boolean addAll(Collection<? extends E> c) {
		writeLock.lock();
		try {
			return list.addAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean retainAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean removeAll(Collection<?> c) {
		writeLock.lock();
		try {
			return list.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final void clear() {
		writeLock.lock();
		try {
			list.clear();
		} finally {
			writeLock.unlock();
		}
	}

	public E get(int index) {
		readLock.lock();
		try {
			return list.get(index);
		} finally {
			readLock.unlock();
		}
	}

	public E set(int index, E element) {
		writeLock.lock();
		try {
			return list.set(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	public void add(int index, E element) {
		writeLock.lock();
		try {
			list.add(index, element);
		} finally {
			writeLock.unlock();
		}
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		writeLock.lock();
		try {
			return list.addAll(index, c);
		} finally {
			writeLock.unlock();
		}
	}

	public E remove(int index) {
		writeLock.lock();
		try {
			return list.remove(index);
		} finally {
			writeLock.unlock();
		}
	}

	public int indexOf(Object o) {
		readLock.lock();
		try {
			return list.indexOf(o);
		} finally {
			readLock.unlock();
		}
	}

	public int lastIndexOf(Object o) {
		readLock.lock();
		try {
			return list.lastIndexOf(o);
		} finally {
			readLock.unlock();
		}
	}

	public ListIterator<E> listIterator() {
		return new ReadWriteListIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return new ReadWriteListIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return new ArrayList<E>();
	}

	private class ReadWriteIterator implements Iterator<E> {

		protected int nextIndex;

		// check illegal state
		protected boolean canRemove;

		protected ReadWriteIterator() {
		}

		protected ReadWriteIterator(int index) {
			this.nextIndex = index;
		}

		public boolean hasNext() {
			readLock.lock();
			try {
				return nextIndex < size();
			} finally {
				readLock.unlock();
			}
		}

		public E next() {
			readLock.lock();
			try {

				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				canRemove = true;
				return list.get(nextIndex++);

			} finally {
				readLock.unlock();
			}
		}

		public void remove() {
			writeLock.lock();
			try {

				if (!canRemove) {
					throw new IllegalStateException();
				}
				list.remove(--nextIndex);
				canRemove = false;

			} finally {
				writeLock.unlock();
			}
		}
	}

	private class ReadWriteListIterator extends ReadWriteIterator implements ListIterator<E> {

		private ReadWriteListIterator() {
			super();
		}

		private ReadWriteListIterator(int index) {
			super(index);
		}

		public boolean hasPrevious() {
			return nextIndex - 1 > 0;
		}

		public E previous() {
			return list.get(nextIndex - 1);
		}

		public int nextIndex() {
			return nextIndex;
		}

		public int previousIndex() {
			return nextIndex - 1;
		}

		public void set(E e) {
			list.set(nextIndex, e);
		}

		public void add(E e) {
			int i = nextIndex - 1;
			list.add(i, e);
		}

	}

}
