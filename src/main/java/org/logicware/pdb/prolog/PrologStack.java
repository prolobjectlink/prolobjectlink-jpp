/*
 * #%L
 * prolobjectlink-jpi
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
package org.logicware.pdb.prolog;

import java.util.Collection;
import java.util.EmptyStackException;

import org.logicware.platform.Stack;

public class PrologStack<E> extends PrologArrayList<E> implements Stack<E> {

	private static final long serialVersionUID = 5252181262129160650L;

	PrologStack() {
		super();
	}

	PrologStack(int capacity) {
		super(capacity);
	}

	PrologStack(Collection<E> c) {
		super(c);
	}

	public boolean empty() {
		return isEmpty();
	}

	public E peek() {
		int n = size();
		if (n <= 0) {
			throw new EmptyStackException();
		} else {
			return get(n - 1);
		}
	}

	public E pop() {
		int n = size();
		if (n <= 0) {
			throw new EmptyStackException();
		} else {
			return remove(n - 1);
		}
	}

	public E push(E item) {
		add(item);
		return item;
	}
}
