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
import java.util.Iterator;
import java.util.Set;

import org.prolobjectlink.prolog.PrologTerm;

public class PrologLinkedSet<E extends PrologTerm> extends AbstractSet<E> implements Set<E> {

	private int size;
	private final PrologLinkedList<E> elements;
	private static final long serialVersionUID = 3122037421156085866L;

	public PrologLinkedSet() {
		this(new PrologLinkedList<E>());
	}

	public PrologLinkedSet(Collection<? extends E> c) {
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

}
