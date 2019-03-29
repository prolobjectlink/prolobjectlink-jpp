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
