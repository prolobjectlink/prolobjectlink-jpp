/*
 * #%L
 * prolobjectlink-jpi
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
package org.logicware.pdb.prolog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

class PrologHashSet<E> extends AbstractSet<E> {

	private int size;
	private Object[] table;
	private static final long serialVersionUID = -3134064473893957230L;

	PrologHashSet() {
		this(16);
	}

	PrologHashSet(int initialCapacity) {
		table = new Object[initialCapacity];
	}

	PrologHashSet(Collection<? extends E> c) {
		if (c != null) {
			table = new Object[c.size()];
			addAll(c);
		}
	}

	private int indexOf(int hash) {
		int capacity = table.length;
		int i = hash < 0 ? -hash % capacity : hash % capacity;
		Object key = table[i] != null ? table[i] : null;
		while (key != null && key.hashCode() != hash) {
			i = (i + 1) % capacity;
			key = table[i] != null ? table[i] : null;
		}
		return i;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + size;
		result = prime * result + Arrays.hashCode(table);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrologHashSet<?> other = (PrologHashSet<?>) obj;
		if (size != other.size)
			return false;
		return Arrays.equals(table, other.table);
	}

	public boolean add(E e) {

		int hash = e.hashCode();
		int index = indexOf(hash);
		if (table[index] == null) {
			table[index] = e;
			float loadFactor = 0.75f;
			int capacity = table.length;
			if (++size >= capacity * loadFactor) {

				// rehashing and copy

				int newCapacity = 2 * capacity;
				Object[] oldTable = table;

				size = 0;
				table = new Object[newCapacity];
				for (int i = 0; i < oldTable.length; i++) {
					if (oldTable[i] != null) {
						add((E) oldTable[i]);
					}
				}

			}

			return true;

		}

		return false;
	}

	public boolean remove(Object o) {
		int hash = o.hashCode();
		int index = indexOf(hash);
		if (table[index] != null) {
			table[index] = null;
			size--;
			return true;
		}
		return false;
	}

	public void clear() {
		size = 0;
		int i = 0;
		while (i < table.length) {
			table[i++] = null;
		}
	}

	public int size() {
		return size;
	}

	public Iterator<E> iterator() {
		return new HashSetIterator();
	}

	private class HashSetIterator implements Iterator<E> {

		private Object next;
		private Object last;

		// indexes
		private int nextIndex;
		private int lastIndex;

		// check illegal state
		private boolean canRemove;

		public HashSetIterator() {
			last = next;
			lastIndex = nextIndex;
			next = table[nextIndex++];// FIXME INDEX OUT OF BOUND
			if (next == null) {
				while (nextIndex < table.length && next == null) {
					next = table[nextIndex++];
				}
			}
		}

		public boolean hasNext() {
			return next != null;
		}

		public E next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			canRemove = true;

			last = next;
			lastIndex = nextIndex;
			next = table[nextIndex++];// FIXME INDEX OUT OF BOUND
			if (next == null) {
				while (nextIndex < table.length && next == null) {
					next = table[nextIndex++];
				}
			}

			return (E) last;
		}

		public void remove() {

			if (!canRemove) {
				throw new IllegalStateException();
			}

			table[lastIndex - 1] = null;
			size--;
		}

	}

}
