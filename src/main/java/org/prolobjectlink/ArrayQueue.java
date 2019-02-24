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
