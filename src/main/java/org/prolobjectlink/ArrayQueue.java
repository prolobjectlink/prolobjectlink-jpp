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
package org.prolobjectlink;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Array implementation of {@link Queue} interface.
 * 
 * @param <E> Generic Element Type
 * @author Jose Zalacain
 * @since 1.0
 * 
 */
public class ArrayQueue<E> extends ArrayList<E> implements Queue<E> {

	private static final long serialVersionUID = 8659174251748733801L;

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
