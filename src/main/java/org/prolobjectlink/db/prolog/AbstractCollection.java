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

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

/**
 * @see PrologHashSet
 * @see PrologTreeSet
 * @see PrologArrayList
 * @author Jose Zalacain
 * @since 1.0
 * @param <E> generic object type
 */
abstract class AbstractCollection<E> implements Collection<E>, Serializable {

	private static final long serialVersionUID = 1217111971532658016L;

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
