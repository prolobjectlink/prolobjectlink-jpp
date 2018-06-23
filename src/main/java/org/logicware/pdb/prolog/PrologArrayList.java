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
package org.logicware.pdb.prolog;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

class PrologArrayList<E> extends AbstractCollection<E> implements List<E> {

	private int size;
	private Object[] elements;
	private static final long serialVersionUID = -5316210117663296419L;

	PrologArrayList() {
		this(10);
	}

	PrologArrayList(int capacity) {
		checkCapacity(capacity);
		elements = new Object[capacity];
	}

	PrologArrayList(Collection<E> c) {
		if (c != null) {
			elements = c.toArray();
			size = elements.length;

			// c.toArray might (incorrectly) not return Object[]
			if (elements.getClass() != Object[].class) {
				elements = Arrays.copyOf(elements, size, Object[].class);
			}
		}
	}

	private void checkIndex(int index) {
		if (index < 0 || index > elements.length) {
			throw new IndexOutOfBoundsException(
					"Index is out of bound exception: " + index + ". Max allowed: " + elements.length);
		}
	}

	private void checkRange(int low, int high) {
		checkIndex(low);
		checkIndex(high);
		if (low > high) {
			throw new IndexOutOfBoundsException("Index is out of bound exception: " + low + ". Max allowed: " + high);
		}
	}

	private void checkCapacity(int capacity) {
		if (capacity < 0) {
			throw new IllegalArgumentException("Illegal Capacity: " + capacity);
		}
	}

	private void duplicateCapacity() {
		if (size == elements.length) {
			elements = Arrays.copyOf(elements, size << 1);
		}
	}

	private Object removeElementAt(int index) {
		checkIndex(index);
		Object o = elements[index];
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elements, index + 1, elements, index, numMoved);
		elements[--size] = null;
		return o;
	}

	@Override
	public String toString() {
		return Arrays.toString(elements);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(elements);
		result = prime * result + size;
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
		PrologArrayList<?> other = (PrologArrayList<?>) obj;
		if (size != other.size)
			return false;
		return Arrays.equals(elements, other.elements);
	}

	public void clear() {
		int i = 0;
		while (i < size) {
			elements[i++] = null;
		}
		size = 0;
	}

	public int size() {
		return size;
	}

	public <T> T[] toArray(T[] a) {
		if (a.length < size) {
			return (T[]) Arrays.copyOf(elements, size, a.getClass());
		}
		System.arraycopy(elements, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	public E get(int index) {
		checkIndex(index);
		return (E) elements[index];
	}

	public E set(int index, E element) {
		checkIndex(index);
		E e = (E) elements[index];
		elements[index] = element;
		return e;
	}

	public boolean add(E o) {
		duplicateCapacity();
		elements[size++] = o;
		return true;
	}

	public void add(int index, E element) {
		checkIndex(index);
		duplicateCapacity();
		System.arraycopy(elements, index, elements, index + 1, size - index);
		elements[index] = element;
		size++;
	}

	public boolean addAll(Collection<? extends E> c) {
		Object[] array = c.toArray();
		int arraySize = array.length;
		elements = Arrays.copyOf(elements, size + arraySize);
		System.arraycopy(array, 0, elements, size, arraySize);
		size = size + arraySize;
		return arraySize > 0;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		checkIndex(index);
		Object[] a = c.toArray();
		int aLength = a.length;
		elements = Arrays.copyOf(elements, size + aLength);

		int numMoved = size - index;
		if (numMoved > 0) {
			System.arraycopy(elements, index, elements, index + aLength, numMoved);
		}

		System.arraycopy(a, 0, elements, index, aLength);
		size += aLength;
		return aLength != 0;
	}

	@Override
	public boolean contains(Object o) {
		for (int i = 0; i < elements.length; i++) {
			if (o.equals(elements[i])) {
				return true;
			}
		}
		return false;
	}

	public E remove(int index) {
		checkIndex(index);
		return (E) removeElementAt(index);
	}

	public boolean remove(Object o) {
		for (int i = 0; i < size; i++) {
			if (o.equals(elements[i])) {
				removeElementAt(i);
				return true;
			}
		}
		return false;
	}

	public int indexOf(Object o) {
		for (int i = 0; i < elements.length; i++) {
			if (o.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}

	public Iterator<E> iterator() {
		return new ArrayIterator();
	}

	public int lastIndexOf(Object o) {
		for (int i = elements.length - 1; i >= 0; i--) {
			if (o.equals(elements[i])) {
				return i;
			}
		}
		return -1;
	}

	public ListIterator<E> listIterator() {
		return new ArrayListIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return new ArrayListIterator(index);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		checkRange(fromIndex, toIndex);
		return new SubList(this, 0, fromIndex, toIndex);
	}

	private class SubList extends AbstractList<E> implements List<E> {

		private final List<E> parent;
		private final int parentOffset;
		private final int offset;
		private int size;

		public SubList(List<E> parent, int offset, int fromIndex, int toIndex) {
			this.parent = parent;
			this.parentOffset = fromIndex;
			this.offset = offset + fromIndex;
			this.size = toIndex - fromIndex;
		}

		@Override
		public E get(int index) {
			checkIndex(index);
			return (E) PrologArrayList.this.elements[offset + index];
		}

		@Override
		public E set(int index, E element) {
			checkIndex(index);
			E oldValue = (E) PrologArrayList.this.elements[offset + index];
			PrologArrayList.this.elements[offset + index] = element;
			return oldValue;
		}

		@Override
		public int size() {
			return this.size;
		}

		@Override
		public void add(int index, E element) {
			checkIndex(index);
			parent.add(parentOffset + index, element);
			this.size++;
		}

		@Override
		public E remove(int index) {
			checkIndex(index);
			E result = parent.remove(parentOffset + index);
			this.size--;
			return result;
		}

		@Override
		public boolean addAll(Collection<? extends E> c) {
			return addAll(size, c);
		}

		@Override
		public boolean addAll(int index, Collection<? extends E> c) {
			checkIndex(index);
			int cSize = c.size();
			if (cSize == 0) {
				return false;
			}
			parent.addAll(parentOffset + index, c);
			this.size += cSize;
			return true;
		}

		public Iterator<E> iterator() {
			return super.listIterator();
		}

		@Override
		public List<E> subList(int fromIndex, int toIndex) {
			checkRange(fromIndex, toIndex);
			return new SubList(this, offset, fromIndex, toIndex);
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = super.hashCode();
			result = prime * result + getOuterType().hashCode();
			result = prime * result + offset;
			result = prime * result + ((parent == null) ? 0 : parent.hashCode());
			result = prime * result + parentOffset;
			result = prime * result + size;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (!super.equals(obj))
				return false;
			if (getClass() != obj.getClass())
				return false;
			SubList other = (SubList) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (offset != other.offset)
				return false;
			if (parent == null) {
				if (other.parent != null)
					return false;
			} else if (!parent.equals(other.parent))
				return false;
			if (parentOffset != other.parentOffset)
				return false;
			return size == other.size;
		}

		private PrologArrayList<E> getOuterType() {
			return PrologArrayList.this;
		}

	}

	private class ArrayIterator implements Iterator<E> {

		protected int nextIndex;

		// check illegal state
		protected boolean canRemove;

		protected ArrayIterator() {
		}

		protected ArrayIterator(int index) {
			this.nextIndex = index;
		}

		public boolean hasNext() {
			return nextIndex < size;
		}

		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			canRemove = true;
			return (E) elements[nextIndex++];
		}

		public void remove() {
			if (!canRemove) {
				throw new IllegalStateException();
			}
			removeElementAt(--nextIndex);
			canRemove = false;
		}
	}

	private class ArrayListIterator extends ArrayIterator implements ListIterator<E> {

		private ArrayListIterator() {
			super();
		}

		private ArrayListIterator(int index) {
			super(index);
		}

		public boolean hasPrevious() {
			return nextIndex - 1 > 0;
		}

		public E previous() {
			return (E) elements[nextIndex - 1];
		}

		public int nextIndex() {
			return nextIndex;
		}

		public int previousIndex() {
			return nextIndex - 1;
		}

		public void set(E e) {
			PrologArrayList.this.set(nextIndex, e);
		}

		public void add(E e) {
			int i = nextIndex - 1;
			PrologArrayList.this.add(i, e);
		}

	}

}
