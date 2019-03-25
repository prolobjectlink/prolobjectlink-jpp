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
package org.prolobjectlink.db.prolog;

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
