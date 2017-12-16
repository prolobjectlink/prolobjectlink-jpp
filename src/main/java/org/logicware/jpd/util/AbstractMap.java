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

import java.util.Iterator;
import java.util.Map;

/**
 * @see HashMap
 * @see TreeMap
 * @author Jose Zalacain
 * @since 1.0
 * @param <K>
 *            generic object type for key
 * @param <V>
 *            generic object type for value
 */
public abstract class AbstractMap<K, V> implements Map<K, V> {

    public AbstractMap() {
    }

    public abstract int size();

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
	String result = "[";
	Iterator<Entry<K, V>> i = entrySet().iterator();
	if (i.hasNext()) {
	    Entry<K, V> entry = (Entry<K, V>) i.next();
	    result += entry.getKey();
	    result += "=";
	    result += entry.getValue();
	    while (i.hasNext()) {
		entry = (Entry<K, V>) i.next();
		result += ", ";
		result += entry.getKey();
		result += "=";
		result += entry.getValue();
	    }
	}
	return result + "]";
    }

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);

}
