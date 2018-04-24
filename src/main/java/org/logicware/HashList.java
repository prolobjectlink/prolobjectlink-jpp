/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

public final class HashList<E> extends AbstractList<E> implements List<E> {

	private LinkedHashSet<E> e;

	public HashList() {
		this(16);
	}

	public HashList(int initialCapacity) {
		e = new LinkedHashSet<E>(initialCapacity);
	}

	public HashList(Collection<? extends E> c) {
		e = new LinkedHashSet<E>(c);
	}

	@Override
	public boolean add(E e) {
		return this.e.add(e);
	}

	@Override
	public Iterator<E> iterator() {
		return e.iterator();
	}

	@Override
	public boolean contains(Object o) {
		return e.contains(o);
	}

	@Override
	public Object[] toArray() {
		return e.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return e.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		return e.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return e.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		return e.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return e.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return e.retainAll(c);
	}

	@Override
	public E get(int index) {
		if (index < 0 || index >= size()) {
			String s = "The index is out of range";
			throw new IndexOutOfBoundsException(s);
		}
		int counter = 0;
		for (E i : e) {
			if (counter == index) {
				return i;
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return e.isEmpty();
	}

	@Override
	public void clear() {
		e.clear();
	}

	@Override
	public int size() {
		return e.size();
	}

	@Override
	public String toString() {
		return "" + e + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((e == null) ? 0 : e.hashCode());
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
		HashList<?> other = (HashList<?>) obj;
		if (e == null) {
			if (other.e != null)
				return false;
		} else if (!e.equals(other.e))
			return false;
		return true;
	}

}
