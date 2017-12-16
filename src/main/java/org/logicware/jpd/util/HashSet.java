/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashSet<E> extends AbstractSet<E> {

    private int size;
    private Object[] table;

    public HashSet() {
	table = new Object[16];
    }

    public HashSet(Collection<? extends E> c) {
	this();
	addAll(c);
    }

    private int indexOf(int hash) {
	int colision = 0;
	int capacity = table.length;
	int index = hash < 0 ? -hash % capacity : hash % capacity;
	while (table[index] != null && table[index].hashCode() != hash) {
	    index += 2 * ++colision - 1;
	    if (index >= table.length) {
		index -= table.length;
	    }
	}
	return index;
    }

    public int getSize() {
	return size;
    }

    public void setSize(int size) {
	this.size = size;
    }

    public Object[] getTable() {
	return table;
    }

    public void setTable(Object[] table) {
	this.table = table;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + size;
	result = prime * result + Arrays.hashCode(table);
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
	HashSet<?> other = (HashSet<?>) obj;
	if (size != other.size)
	    return false;
	if (!Arrays.equals(table, other.table))
	    return false;
	return true;
    }

    public boolean add(E e) {

	int hash = e.hashCode();
	int index = indexOf(hash);
	if (table[index] == null) {
	    table[index] = e;
	    float loadFactor = 0.75f;
	    int capacity = table.length;
	    if (++size >= capacity * loadFactor) {

		// rehashing and copy

		int newCapacity = 2 * capacity;
		Object[] oldTable = table;

		size = 0;
		table = new Object[newCapacity];
		for (int i = 0; i < oldTable.length; i++) {
		    if (oldTable[i] != null) {
			add((E) oldTable[i]);
		    }
		}

	    }

	    return true;

	}

	return false;
    }

    public boolean remove(Object o) {
	int hash = o.hashCode();
	int index = indexOf(hash);
	if (table[index] != null) {
	    table[index] = null;
	    size--;
	    return true;
	}
	return false;
    }

    public void clear() {
	size = 0;
	int i = 0;
	while (i < table.length) {
	    table[i++] = null;
	}
    }

    @Override
    public int size() {
	return size;
    }

    @Override
    public Iterator<E> iterator() {
	return new HashSetIterator();
    }

    private class HashSetIterator implements Iterator<E> {

	private Object next;
	private Object last;

	// indexes
	private int nextIndex;
	private int lastIndex;

	// check illegal state
	private boolean canRemove;

	public HashSetIterator() {
	    last = next;
	    lastIndex = nextIndex;
	    next = table[nextIndex++];
	    if (next == null) {
		while (nextIndex < table.length && next == null) {
		    next = table[nextIndex++];
		}
	    }
	}

	public boolean hasNext() {
	    return next != null;
	}

	public E next() {

	    if (!hasNext()) {
		throw new NoSuchElementException();
	    }

	    canRemove = true;

	    last = next;
	    lastIndex = nextIndex;
	    next = table[nextIndex++];
	    if (next == null) {
		while (nextIndex < table.length && next == null) {
		    next = table[nextIndex++];
		}
	    }

	    return (E) last;
	}

	public void remove() {

	    if (!canRemove) {
		throw new IllegalStateException();
	    }

	    table[lastIndex - 1] = null;
	    size--;
	}

    }

}
