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

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashMap<K, V> extends AbstractMap<K, V> {

    private int size;
    private Entry<K, V>[] table;

    public HashMap() {
	table = new Entry[16];
    }

    private int indexOf(int hash) {
	int colision = 0;
	int capacity = table.length;
	int index = hash < 0 ? -hash % capacity : hash % capacity;
	while (table[index] != null && table[index].getKey().hashCode() != hash) {
	    index += 2 * ++colision - 1;
	    if (index >= table.length) {
		index -= table.length;
	    }
	}
	return index;
    }

    @Override
    protected Entry<K, V> getEntry(Object key) {
	int hash = (key == null) ? 0 : key.hashCode();
	int i = indexOf(hash);
	return table[i];
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
	HashMap<?, ?> other = (HashMap<?, ?>) obj;
	if (size != other.size)
	    return false;
	if (!Arrays.equals(table, other.table))
	    return false;
	return true;
    }

    public int size() {
	return size;
    }

    public V put(K key, V value) {

	int hash = key.hashCode();
	int index = indexOf(hash);
	Entry<K, V> entry = new Entry<K, V>(key, value);
	V old = table[index] != null ? table[index].getValue() : null;
	if (old != null) {
	    table[index].setValue(value);
	} else {
	    table[index] = entry;
	    size++;
	}

	float loadFactor = 0.75f;
	int capacity = table.length;
	if (size >= capacity * loadFactor) {

	    // rehashing and copy

	    int newCapacity = 2 * capacity;
	    Entry<K, V>[] oldTable = table;

	    size = 0;
	    table = new Entry[newCapacity];
	    for (int i = 0; i < oldTable.length; i++) {
		if (oldTable[i] != null) {
		    Entry<K, V> e = oldTable[i];
		    put(e.getKey(), e.getValue());
		}
	    }

	}

	return old;

    }

    public V remove(Object key) {
	int hash = key.hashCode();
	int index = indexOf(hash);
	Entry<K, V> e = table[index];
	V v = e.getValue();
	table[index] = null;
	size--;
	return v;
    }

    public void clear() {
	size = 0;
	int i = 0;
	while (i < table.length) {
	    table[i++] = null;
	}
    }

    public Set<K> keySet() {
	return new KeySet();
    }

    public Collection<V> values() {
	return new ValueCollection();
    }

    public Set<Map.Entry<K, V>> entrySet() {
	return new EntrySet();
    }

    public final class Entry<Key, Value> implements Map.Entry<Key, Value> {

	private Key key;
	private Value value;

	public Entry(Key key, Value value) {
	    this.key = key;
	    this.value = value;
	}

	public Key getKey() {
	    return key;
	}

	public Value getValue() {
	    return value;
	}

	public Value setValue(Value value) {
	    return this.value = value;
	}

	@Override
	public String toString() {
	    return key + " = " + value;
	}

	@Override
	public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((key == null) ? 0 : key.hashCode());
	    result = prime * result + ((value == null) ? 0 : value.hashCode());
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
	    Entry<Key, Value> other = (Entry<Key, Value>) obj;
	    if (key == null) {
		if (other.key != null)
		    return false;
	    } else if (!key.equals(other.key))
		return false;
	    if (value == null) {
		if (other.value != null)
		    return false;
	    } else if (!value.equals(other.value))
		return false;
	    return true;
	}

    }

    private class KeySet extends AbstractSet<K> {

	@Override
	public Iterator<K> iterator() {
	    return new KeySetIterator();
	}

	@Override
	public int size() {
	    return size;
	}

    }

    private class ValueCollection extends AbstractCollection<V> {

	@Override
	public Iterator<V> iterator() {
	    return new ValueSetIterator();
	}

	@Override
	public int size() {
	    return size;
	}

    }

    private class EntrySet extends AbstractSet<Map.Entry<K, V>> {

	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
	    return new EntrySetIterator();
	}

	@Override
	public int size() {
	    return size;
	}

    }

    private abstract class AbstractIterator {

	private int lastIndex;
	private Entry<K, V> next;
	private Entry<K, V> lastReturned;

	public AbstractIterator() {
	    int capacity = table.length;
	    while (lastIndex < capacity && next == null) {
		next = table[lastIndex];
		lastIndex++;
	    }
	}

	public boolean hasNext() {
	    return next != null;
	}

	public void remove() {
	    table[lastIndex] = null;
	}

	protected Entry<K, V> nextEntry() {
	    lastReturned = next;
	    next = table[lastIndex++];
	    int capacity = table.length;
	    for (; lastIndex < capacity && next == null; lastIndex++) {
		next = table[lastIndex];
	    }
	    return lastReturned;
	}

    }

    private class KeySetIterator extends AbstractIterator implements Iterator<K> {

	public K next() {
	    return nextEntry().getKey();
	}

    }

    private class ValueSetIterator extends AbstractIterator implements Iterator<V> {

	public V next() {
	    return nextEntry().getValue();
	}

    }

    private class EntrySetIterator extends AbstractIterator implements Iterator<Map.Entry<K, V>> {

	public Entry<K, V> next() {
	    return nextEntry();
	}

    }

}
