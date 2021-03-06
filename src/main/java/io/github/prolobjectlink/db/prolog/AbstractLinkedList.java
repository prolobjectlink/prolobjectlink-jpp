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
package io.github.prolobjectlink.db.prolog;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractLinkedList<E> extends AbstractCollection<E> implements List<E> {

	protected E element;
	protected AbstractLinkedList<E> next;
	private static final long serialVersionUID = 2812221898884199941L;

	/**
	 * Create new empty list
	 */
	AbstractLinkedList() {
	}

	/**
	 * Create new list with element and empty tail
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	AbstractLinkedList(E e) {
		this.element = e;
	}

	/**
	 * Create new list with element and next reference
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	AbstractLinkedList(E e, AbstractLinkedList<E> next) {
		this.element = e;
		this.next = next;
	}

	AbstractLinkedList(Collection<? extends E> c) {
		addAll(c);
	}

	void setNext(AbstractLinkedList<E> next) {
		this.next = next;
	}

	void setElement(E element) {
		this.element = element;
	}

	E getElement() {
		return element;
	}

	AbstractLinkedList<E> getNext() {
		return next;
	}

	public int size() {
		int counter = 0;
		Iterator<E> i = iterator();
		for (; i.hasNext(); i.next()) {
			counter++;
		}
		return counter;
	}

	public Iterator<E> iterator() {
		return listIterator();
	}

	public boolean remove(Object o) {
		Iterator<E> i = iterator();
		while (i.hasNext()) {
			if (o.equals(i.next())) {
				i.remove();
				return true;
			}
		}
		return false;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		try {
			boolean modified = false;
			ListIterator<E> e1 = listIterator(index);
			Iterator<? extends E> e2 = c.iterator();
			while (e2.hasNext()) {
				e1.add(e2.next());
				modified = true;
			}
			return modified;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("" + index + "");
		}
	}

	public void clear() {
		Iterator<E> it = iterator();
		while (it.hasNext()) {
			it.next();
			it.remove();
		}
	}

	public E get(int index) {
		try {
			return listIterator(index).next();
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("" + index + "");
		}
	}

	public E set(int index, E element) {
		try {
			ListIterator<E> e = listIterator(index);
			E oldVal = e.next();
			e.set(element);
			return oldVal;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("" + index + "");
		}
	}

	public void add(int index, E element) {
		try {
			listIterator(index).add(element);
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("" + index + "");
		}
	}

	public E remove(int index) {
		try {
			ListIterator<E> e = listIterator(index);
			E outCast = e.next();
			e.remove();
			return outCast;
		} catch (NoSuchElementException exc) {
			throw new IndexOutOfBoundsException("" + index + "");
		}
	}

	public int indexOf(Object o) {
		Iterator<E> itr = listIterator();
		for (int i = 0; itr.hasNext(); i++) {
			if (o.equals(itr.next())) {
				return i;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object o) {
		int lastIndex = -1;
		ListIterator<E> itr = listIterator();
		for (int i = 0; itr.hasNext(); i++) {
			if (o.equals(itr.next())) {
				lastIndex = i;
			}
		}
		return lastIndex;
	}

	public ListIterator<E> listIterator() {
		return listIterator(0);
	}

	public ListIterator<E> listIterator(int index) {
		return new PrologListIterator(index, this);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return new PrologSubList(this, size(), 0, fromIndex, toIndex);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((next == null) ? 0 : next.hashCode());
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
		AbstractLinkedList<?> other = (AbstractLinkedList<?>) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element)) {
			return false;
		}
		if (next == null) {
			if (other.next != null)
				return false;
		} else if (!next.equals(other.next)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Iterator<E> i = iterator();
		if (i.hasNext()) {
			E item = i.next();
			result.append(item);
			while (i.hasNext()) {
				item = i.next();
				result.append(", ");
				result.append(item);
			}
		}
		return result + "]";
	}

	private class PrologIterator implements Iterator<E> {

		// check illegal state flag
		protected boolean canRemove;

		// iterator pointers
		protected AbstractLinkedList<E> nextPtr;
		protected AbstractLinkedList<E> listRef;

		PrologIterator(AbstractLinkedList<E> list) {
			nextPtr = listRef = list;
		}

		public boolean hasNext() {
			if (element == null && next == null) {
				return false;
			}
			return nextPtr != null;
		}

		public E next() {

			if (!hasNext()) {
				throw new NoSuchElementException();
			}

			E e = nextPtr.element;
			nextPtr = nextPtr.next;
			canRemove = true;
			return e;

		}

		public void remove() {

			if (!canRemove) {
				throw new IllegalStateException();
			}

			if (hasNext()) {
				listRef.element = nextPtr.element;
				listRef.next = nextPtr.next;
			} else {
				listRef.element = null;
				listRef.next = null;
			}

			canRemove = false;
		}

	}

	/**
	 * List iterator class implementation
	 * 
	 * @author Jose Zalacain
	 */
	private class PrologListIterator extends PrologIterator implements ListIterator<E> {

		// next index to return
		private int nextIndex;

		PrologListIterator(int index, AbstractLinkedList<E> list) {
			super(list);
			int i = 0;
			while (i < index) {
				super.next();
				i++;
			}
		}

		public void add(E e) {
			listRef.setNext(new PrologLinkedList<E>(e, listRef.next));
		}

		public boolean hasPrevious() {
			throw new UnsupportedOperationException();
		}

		public int nextIndex() {
			return nextIndex;
		}

		public E previous() {
			throw new UnsupportedOperationException();
		}

		public int previousIndex() {
			throw new UnsupportedOperationException();
		}

		public void set(E e) {
			nextPtr.element = e;
			canRemove = false;
		}
	}

	/**
	 * Prolog SubList Implementation
	 * 
	 * @author Jose Zalacain
	 * @since 1.0
	 */
	private class PrologSubList extends PrologLinkedList<E> implements List<E> {

		private static final long serialVersionUID = -1043111763901403631L;
		private final AbstractLinkedList<E> parent;
		private final int parentOffset;
		private final int parentSize;
		private final int offset;
		private int size;

		private int checkListIndex(int index, int size) {
			return checkListIndex(index, size, "");
		}

		private int checkListIndex(int index, int size, String message) {
			if (index < 0 || index >= size) {
				throw new IndexOutOfBoundsException(message);
			}
			return index;
		}

		PrologSubList(AbstractLinkedList<E> parent, int parentSize, int offset, int fromIndex, int toIndex) {
			fromIndex = checkListIndex(fromIndex, parentSize);
			toIndex = checkListIndex(toIndex, parentSize);
			this.offset = offset + fromIndex;
			this.size = toIndex - fromIndex;
			this.parentOffset = fromIndex;
			this.parentSize = parentSize;
			this.parent = parent;
		}

	}
}
