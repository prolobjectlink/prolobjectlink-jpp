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
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class ReadWriteSet<E> implements Set<E> {

	private final ReadWriteLock lock = new ReentrantReadWriteLock();
	private final Lock writeLock = lock.writeLock();
	private final Lock readLock = lock.readLock();
	private final Set<E> set;

	protected ReadWriteSet(Set<E> set) {
		this.set = set;
	}

	public final int size() {
		readLock.lock();
		try {
			return set.size();
		} finally {
			readLock.unlock();
		}
	}

	public final boolean isEmpty() {
		readLock.lock();
		try {
			return set.isEmpty();
		} finally {
			readLock.unlock();
		}
	}

	public final boolean contains(Object o) {
		readLock.lock();
		try {
			return set.contains(o);
		} finally {
			readLock.unlock();
		}
	}

	public final Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Object[] toArray() {
		readLock.lock();
		try {
			return set.toArray();
		} finally {
			readLock.unlock();
		}
	}

	public final <T> T[] toArray(T[] a) {
		readLock.lock();
		try {
			return set.toArray(a);
		} finally {
			readLock.unlock();
		}
	}

	public final boolean add(E e) {
		writeLock.lock();
		try {
			return set.add(e);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean remove(Object o) {
		writeLock.lock();
		try {
			return set.remove(o);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean containsAll(Collection<?> c) {
		readLock.lock();
		try {
			return set.containsAll(c);
		} finally {
			readLock.unlock();
		}
	}

	public final boolean addAll(Collection<? extends E> c) {
		writeLock.lock();
		try {
			return set.addAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean retainAll(Collection<?> c) {
		writeLock.lock();
		try {
			return set.retainAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final boolean removeAll(Collection<?> c) {
		writeLock.lock();
		try {
			return set.removeAll(c);
		} finally {
			writeLock.unlock();
		}
	}

	public final void clear() {
		writeLock.lock();
		try {
			set.clear();
		} finally {
			writeLock.unlock();
		}
	}

}
