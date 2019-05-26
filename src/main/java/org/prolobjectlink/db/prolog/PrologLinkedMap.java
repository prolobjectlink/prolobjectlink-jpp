/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.prolog;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Persistent {@link Map} interface implementation for persist
 * {@link LinkedHashMap}. Don't have the same performance like
 * {@link LinkedHashMap} but is Prolog structure persistent. Is implemented used
 * a persistent linked list to preserve the insertion order.
 * 
 * @author Jose Zalacain
 *
 * @param <K> the type of the map keys
 * @param <V> the type of mapped values
 * @since 1.0
 */
final class PrologLinkedMap<K, V> implements Map<K, V>, Serializable {

	private int size;
	private final PrologLinkedList<Entry<K, V>> entries;
	private static final long serialVersionUID = -3973965266142457565L;

	PrologLinkedMap() {
		entries = new PrologLinkedList<Entry<K, V>>();
	}

	PrologLinkedMap(int capacity) {
		entries = new PrologLinkedList<Entry<K, V>>();
		size = capacity;
	}

	PrologLinkedMap(Map<? extends K, ? extends V> m) {
		entries = new PrologLinkedList<Entry<K, V>>();
		size = m.size();
		putAll(m);
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public boolean containsKey(Object key) {
		for (Entry<K, V> entry : entries) {
			if (entry.getKey().equals(key)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		for (Entry<K, V> entry : entries) {
			if (entry.getValue().equals(value)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public V get(Object key) {
		for (Entry<K, V> entry : entries) {
			if (entry.getKey().equals(key)) {
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public V put(K key, V value) {
		V old = get(key);
		Entry<K, V> e = new PrologEntry(key, value);
		entries.add(e);
		size = size + 1;
		return old;
	}

	@Override
	public V remove(Object key) {
		for (Entry<K, V> entry : entries) {
			if (entry.getKey().equals(key)) {
				entries.remove(entry);
				size = size - 1;
				return entry.getValue();
			}
		}
		return null;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		entries.clear();
		size = 0;
	}

	@Override
	public Set<K> keySet() {
		Set<Entry<K, V>> eSet = entrySet();
		Set<K> kSet = new LinkedHashSet<K>(size);
		for (Entry<K, V> entry : eSet) {
			kSet.add(entry.getKey());
		}
		return kSet;
	}

	@Override
	public Collection<V> values() {
		Set<Entry<K, V>> eSet = entrySet();
		Set<V> vSet = new LinkedHashSet<V>(size);
		for (Entry<K, V> entry : eSet) {
			vSet.add(entry.getValue());
		}
		return vSet;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> eSet = new LinkedHashSet<Entry<K, V>>(size);
		for (Entry<K, V> entry : entries) {
			eSet.add(entry);
		}
		return eSet;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entries == null) ? 0 : entries.hashCode());
		result = prime * result + size;
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
		PrologLinkedMap<?, ?> other = (PrologLinkedMap<?, ?>) obj;
		if (entries == null) {
			if (other.entries != null)
				return false;
		} else if (!entries.equals(other.entries)) {
			return false;
		}
		return size == other.size;
	}

	private class PrologEntry implements Map.Entry<K, V> {

		private final K key;
		private V value;

		private PrologEntry(K key, V value) {
			this.value = value;
			this.key = key;
		}

		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			V old = this.value;
			this.value = value;
			return old;
		}
	}

}
