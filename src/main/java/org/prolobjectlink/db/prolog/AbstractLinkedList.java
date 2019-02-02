/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.prolog;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractLinkedList<E> extends AbstractCollection<E> implements List<E> {

	private E element;
	private AbstractLinkedList<E> next;
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
		if (!isEmpty()) {
			Iterator<E> i = iterator();
			for (; i.hasNext(); i.next()) {
				counter++;
			}
		}
		return counter;
	}

	public Iterator<E> iterator() {
		return listIterator();
	}

	public boolean add(E e) {
		throw new UnsupportedOperationException();
//		if (element == null) {
//			element = e;
//			return true;
//		} else if (next != null) {
//			PrologLinkedList<E> listPtr = this;
//			PrologLinkedList<E> nextPtr = listPtr.next;
//			while (nextPtr != null) {
//				listPtr = nextPtr;
//				nextPtr = listPtr.next;
//			}
//
//			// set created list as tail of the current list
//			listPtr.setNext(new PrologLinkedList<E>(provider, e));
//			return true;
//		}
//		return false;
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

	public Class<?> toClass() {
		return LinkedList.class;
	}

	public Object toObject() {
		return new LinkedList<E>(this);
	}

	private class PrologIterator implements Iterator<E> {

		// next index to returned
		protected int nextIndex;

		// check illegal state flag
		protected boolean canRemove;

		// iterator pointers
		protected AbstractLinkedList<E> prevPtr;
		protected AbstractLinkedList<E> listPtr;
		protected AbstractLinkedList<E> headPtr;
		protected AbstractLinkedList<E> nextPtr;

		PrologIterator(AbstractLinkedList<E> list) {
			nextPtr = list;
		}

		public boolean hasNext() {
			return nextPtr != null;
		}

		public E next() {
			// TODO Auto-generated method stub
			return null;
		}

		public void remove() {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * List iterator class implementation
	 * 
	 * @author Jose Zalacain
	 */
	private class PrologListIterator extends PrologIterator implements ListIterator<E> {

		PrologListIterator(int index, AbstractLinkedList<E> list) {
			super(list);
			int i = 0;
			while (i < index) {
				super.next();
				i++;
			}
		}

		public void add(E e) {
			// TODO Auto-generated method stub
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
			listPtr.setElement(e);
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
