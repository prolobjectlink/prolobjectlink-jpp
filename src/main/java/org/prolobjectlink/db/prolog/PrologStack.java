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

import java.util.Collection;
import java.util.EmptyStackException;

public class PrologStack<E> extends PrologArrayList<E> {

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
