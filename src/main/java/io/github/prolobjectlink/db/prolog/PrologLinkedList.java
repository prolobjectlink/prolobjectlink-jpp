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
import java.util.List;

/**
 * Persistent {@link List} interface implementation.
 * 
 * @author Jose Zalacain
 *
 * @param <E>
 */
class PrologLinkedList<E> extends AbstractLinkedList<E> implements List<E> {

	private static final long serialVersionUID = 2370240611871530505L;

	/**
	 * Create new empty list
	 */
	PrologLinkedList() {
	}

	/**
	 * Create new list with element and empty tail
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	PrologLinkedList(E e) {
		super(e);
	}

	/**
	 * Create new list with element and next reference
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	PrologLinkedList(E e, AbstractLinkedList<E> next) {
		super(next);
	}

	PrologLinkedList(Collection<? extends E> c) {
		super(c);
	}

	public boolean add(E e) {
		if (element == null) {
			element = e;
		} else if (next == null) {
			setNext(new PrologLinkedList<E>(e));
		} else {
			AbstractLinkedList<E> listPtr = this;
			AbstractLinkedList<E> nextPtr = listPtr.next;
			while (nextPtr != null) {
				listPtr = nextPtr;
				nextPtr = listPtr.next;
			}

			// set created list as tail of the current list
			listPtr.setNext(new PrologLinkedList<E>(e));
		}
		return true;
	}

}
