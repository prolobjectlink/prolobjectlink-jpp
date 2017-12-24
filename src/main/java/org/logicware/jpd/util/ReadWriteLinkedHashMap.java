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

import java.util.LinkedHashMap;
import java.util.Map;

public final class ReadWriteLinkedHashMap<K, V> extends ReadWriteMap<K, V> implements Map<K, V> {

	public ReadWriteLinkedHashMap() {
		super(new LinkedHashMap<K, V>());
	}

	public ReadWriteLinkedHashMap(int initialCapacity) {
		super(new LinkedHashMap<K, V>(initialCapacity));
	}

	public ReadWriteLinkedHashMap(int initialCapacity, float loadFactor) {
		super(new LinkedHashMap<K, V>(initialCapacity, loadFactor));
	}

	public ReadWriteLinkedHashMap(Map<? extends K, ? extends V> m) {
		super(new LinkedHashMap<K, V>(m));
	}

}
