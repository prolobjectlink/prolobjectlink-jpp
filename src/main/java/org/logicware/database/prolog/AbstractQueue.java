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
package org.logicware.database.prolog;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Queue;

abstract class AbstractQueue<E> extends PrologArrayList<E> implements Queue<E> {

	private static final long serialVersionUID = -3472285760926699928L;

	AbstractQueue() {
		super();
	}

	AbstractQueue(int capacity) {
		super(capacity);
	}

	AbstractQueue(Collection<E> c) {
		super(c);
	}

	public boolean offer(E e) {
		return add(e);
	}

	public E remove() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return remove(0);
	}

	public E poll() {
		if (!isEmpty()) {
			return remove(0);
		}
		return null;
	}

	public E element() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		return get(0);
	}

	public E peek() {
		if (!isEmpty()) {
			return get(0);
		}
		return null;
	}

}
