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
package org.logicware.pdb.prolog;

import java.util.AbstractCollection;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class PrologLinkedMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

	private int size;
	private PrologLinkedList<PrologEntry<K, V>> table;
	private static final long serialVersionUID = -5838441025116119863L;

	/**
	 * Create new empty map
	 */
	PrologLinkedMap() {
	}

	public int size() {
		return size;
	}

	public V put(K key, V value) {
		PrologEntry<K, V> e = new LinkedEntry<K, V>(key, value);
		ListIterator<PrologEntry<K, V>> i = table.listIterator();
		while (i.hasNext()) {
			Map.Entry<K, V> entry = i.next();
			if (entry.equals(e)) {
				V v = entry.getValue();
				size = size + 1;
				i.set(e);
				return v;
			}
		}
		return null;
	}

	public V remove(Object key) {
		Iterator<Entry<K, V>> i = new EntrySetIterator();
		while (i.hasNext()) {
			Map.Entry<K, V> entry = i.next();
			if (entry.getKey().equals(key)) {
				V v = entry.getValue();
				// i.remove()
				// decrement size
				i.remove();
				return v;
			}
		}
		return null;
	}

	public void clear() {
		table.clear();
		size = 0;
	}

	public Set<K> keySet() {
		return new KeySet();
	}

	public Collection<V> values() {
		return new ValueCollection();
	}

	public Set<Entry<K, V>> entrySet() {
		return new EntrySet();
	}

	@Override
	protected Entry<K, V> getEntry(Object key) {
		Iterator<Entry<K, V>> i = new EntrySetIterator();
		while (i.hasNext()) {
			Map.Entry<K, V> entry = i.next();
			if (entry.getKey().equals(key)) {
				return entry;
			}
		}
		return null;
	}

	public final class LinkedEntry<K0, V0> implements PrologEntry<K0, V0> {

		private K0 key;
		private V0 value;

		public LinkedEntry(K0 key, V0 value) {
			this.key = key;
			this.value = value;
		}

		public final K0 getKey() {
			return key;
		}

		public final V0 getValue() {
			return value;
		}

		public final V0 setValue(V0 value) {
			this.value = value;
			return value;
		}

		@Override
		public final String toString() {
			return key + " = " + value;
		}

		@Override
		public final int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public final boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			LinkedEntry<?, ?> other = (LinkedEntry<?, ?>) obj;
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

	private class EntrySet extends AbstractSet<Entry<K, V>> {

		@Override
		public Iterator<Entry<K, V>> iterator() {
			return new EntrySetIterator();
		}

		@Override
		public int size() {
			return size;
		}

	}

	private abstract class AbstractIterator {

		private Iterator<PrologEntry<K, V>> i;

		public AbstractIterator() {
			i = table.iterator();
		}

		public boolean hasNext() {
			return i.hasNext();
		}

		public void remove() {
			i.remove();
			size--;
		}

		protected PrologEntry<K, V> nextEntry() {
			return i.next();
		}

	}

	private class KeySetIterator extends AbstractIterator implements Iterator<K> {

		public K next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry().getKey();
		}

	}

	private class ValueSetIterator extends AbstractIterator implements Iterator<V> {

		public V next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry().getValue();
		}

	}

	private class EntrySetIterator extends AbstractIterator implements Iterator<Entry<K, V>> {

		public PrologEntry<K, V> next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return nextEntry();
		}

	}

}
