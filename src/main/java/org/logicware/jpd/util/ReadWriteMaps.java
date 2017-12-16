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

import java.util.Map;

/**
 * Factory class to create news {@link ReadWriteMap}
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see ReadWriteMap
 * @see ReadWriteHashMap
 * @see ReadWriteLinkedHashMap
 *
 */
public final class ReadWriteMaps {

    public static <K, V> ReadWriteMap<K, V> newReadWriteHashMap() {
	return new ReadWriteHashMap<K, V>();
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteHashMap(int initialCapacity) {
	return new ReadWriteHashMap<K, V>(initialCapacity);
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteHashMap(int initialCapacity, float loadFactor) {
	return new ReadWriteHashMap<K, V>(initialCapacity, loadFactor);
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteHashMap(Map<? extends K, ? extends V> m) {
	return new ReadWriteHashMap<K, V>(m);
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteLinkedHashMap() {
	return new ReadWriteLinkedHashMap<K, V>();
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteLinkedHashMap(int initialCapacity) {
	return new ReadWriteLinkedHashMap<K, V>(initialCapacity);
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteLinkedHashMap(int initialCapacity, float loadFactor) {
	return new ReadWriteLinkedHashMap<K, V>(initialCapacity, loadFactor);
    }

    public static <K, V> ReadWriteMap<K, V> newReadWriteLinkedHashMap(Map<? extends K, ? extends V> m) {
	return new ReadWriteLinkedHashMap<K, V>(m);
    }

}
