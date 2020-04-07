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
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Persistent {@link Set} interface implementation for persist
 * {@link LinkedHashSet}. Don't have the same performance like
 * {@link LinkedHashSet} but is Prolog structure persistent. Is implemented used
 * a persistent linked list to preserve the insertion order.
 * 
 * @author Jose Zalacain
 *
 * @param <E> the type of the set elements
 */
final class PrologLinkedSet<E> extends AbstractSet<E> implements Set<E> {

	private int size;
	private final PrologLinkedList<E> elements;
	private static final long serialVersionUID = 3122037421156085866L;

	PrologLinkedSet() {
		this(new PrologLinkedList<E>());
	}

	PrologLinkedSet(int capacity) {
		elements = new PrologLinkedList<E>();
		size = capacity;
	}

	PrologLinkedSet(Collection<? extends E> c) {
		elements = new PrologLinkedList<E>(c);
	}

	public boolean add(E arg0) {
		size = size + 1;
		return elements.add(arg0);
	}

	public void clear() {
		elements.clear();
		size = 0;
	}

	public Iterator<E> iterator() {
		return elements.iterator();
	}

	public boolean remove(Object arg0) {
		size = size - 1;
		return elements.remove(arg0);
	}

	public int size() {
		return size;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((elements == null) ? 0 : elements.hashCode());
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
		PrologLinkedSet<?> other = (PrologLinkedSet<?>) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements)) {
			return false;
		}
		return size == other.size;
	}

}
