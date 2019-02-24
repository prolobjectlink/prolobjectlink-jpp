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
