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

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * @see PrologHashSet
 * @see PrologTreeSet
 * @see PrologArrayList
 * @author Jose Zalacain
 * @since 1.0
 * @param <E>
 *            generic object type
 */
abstract class AbstractCollection<E> implements Collection<E> {

	public AbstractCollection() {
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	public Object[] toArray() {
		int index = 0;
		Object[] o = new Object[size()];
		for (Iterator<E> i = iterator(); i.hasNext();) {
			o[index++] = i.next();
		}
		return o;
	}

	public <T> T[] toArray(T[] a) {
		int index = 0;
		int size = size();
		Class<?> clazz = a.getClass().getComponentType();
		T[] t = (T[]) Array.newInstance(clazz, size);
		for (Iterator<E> i = iterator(); i.hasNext();) {
			t[index++] = (T) i.next();
		}
		return t;
	}

	public boolean contains(Object o) {
		for (Iterator<E> i = iterator(); i.hasNext();) {
			if (o.equals(i.next())) {
				return true;
			}
		}
		return false;
	}

	public boolean containsAll(Collection<?> c) {
		for (Object object : c) {
			if (!contains(object)) {
				return false;
			}
		}
		return true;
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean itChange = false;
		for (E e : c) {
			if (add(e)) {
				itChange = true;
			}
		}
		return itChange;
	}

	public boolean retainAll(Collection<?> c) {
		boolean itChange = false;
		Iterator<E> i = iterator();
		while (i.hasNext()) {
			if (!c.contains(i.next())) {
				i.remove();
				itChange = true;
			}
		}
		return itChange;
	}

	public boolean removeAll(Collection<?> c) {
		boolean itChange = false;
		Iterator<?> i = iterator();
		while (i.hasNext()) {
			if (c.contains(i.next())) {
				i.remove();
				itChange = true;
			}
		}
		return itChange;
	}

}
