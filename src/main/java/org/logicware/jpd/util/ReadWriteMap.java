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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Read-Write map that allow multiple reader threads simultaneously and
 * exclusive access for writer threads. Have read and write locks obtained from
 * {@link ReadWriteLock}.
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see ReadWriteHashMap
 * @see ReadWriteLinkedHashMap
 *
 * @param <K>
 *            key generic type
 * @param <V>
 *            value generic type
 */
public abstract class ReadWriteMap<K, V> implements Map<K, V> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock writeLock = lock.writeLock();
	private final Lock readLock = lock.readLock();
	private final Map<K, V> map;

	protected ReadWriteMap(Map<K, V> map) {
		this.map = map;
	}

	public final int size() {
		readLock.lock();
		try {
			return map.size();
		} finally {
			readLock.unlock();
		}
	}

	public boolean isEmpty() {
		readLock.lock();
		try {
			return map.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	public boolean containsKey(Object key) {
		readLock.lock();
		try {
			return map.containsKey(key);
		} finally {
			readLock.unlock();
		}
	}

	public boolean containsValue(Object value) {
		readLock.lock();
		try {
			return map.containsValue(value);
		} finally {
			readLock.unlock();
		}
	}

	public V get(Object key) {
		readLock.lock();
		try {
			return map.get(key);
		} finally {
			readLock.unlock();
		}
	}

	public V put(K key, V value) {
		writeLock.lock();
		try {
			return map.put(key, value);
		} finally {
			writeLock.unlock();
		}
	}

	public V remove(Object key) {
		writeLock.lock();
		try {
			return map.remove(key);
		} finally {
			writeLock.unlock();
		}
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		writeLock.lock();
		try {
			map.putAll(m);
		} finally {
			writeLock.unlock();
		}
	}

	public void clear() {
		writeLock.lock();
		try {
			map.clear();
		} finally {
			writeLock.unlock();
		}
	}

	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

}
