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
package org.prolobjectlink.db.prolog;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

/**
 * @see PrologHashMap
 * @see PrologTreeMap
 * @author Jose Zalacain
 * @since 1.0
 * @param <K> generic object type for key
 * @param <V> generic object type for value
 */
abstract class AbstractMap<K, V> implements Map<K, V>, Serializable {

	private static final long serialVersionUID = 6977777072929966719L;

	public AbstractMap() {
	}

	public boolean isEmpty() {
		return size() == 0;
	}

	protected abstract Entry<K, V> getEntry(Object key);

	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	public boolean containsValue(Object value) {
		for (V v : values()) {
			if (value == null) {
				return v == null;
			} else if (v.equals(value)) {
				return true;
			}
		}
		return false;
	}

	public V get(Object key) {
		Entry<K, V> e = getEntry(key);
		return e != null ? e.getValue() : null;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (Entry<? extends K, ? extends V> e : m.entrySet()) {
			put(e.getKey(), e.getValue());
		}
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (i.hasNext()) {
			Entry<K, V> entry = i.next();
			result.append(entry.getKey());
			result.append("=");
			result.append(entry.getValue());
			while (i.hasNext()) {
				entry = i.next();
				result.append(", ");
				result.append(entry.getKey());
				result.append("=");
				result.append(entry.getValue());
			}
		}
		return result + "]";
	}

}
