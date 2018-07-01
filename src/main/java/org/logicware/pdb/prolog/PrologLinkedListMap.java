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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class PrologLinkedListMap<K extends PrologTerm, V extends PrologTerm> extends AbstractMap<K, V>
		implements Map<K, V> {

	private PrologLinkedList<PrologEntry<K, V>> table;
	private static final long serialVersionUID = -5838441025116119863L;

	/**
	 * Create new empty map
	 */
	PrologLinkedListMap() {
		super();
	}

	public Class<?> toClass() {
		return LinkedHashMap.class;
	}

	public Object toObject() {
		return new LinkedHashMap<K, V>(this);
	}

	public int size() {
		return table.size();
	}

	public V put(K key, V value) {
		// TODO Auto-generated method stub
		return null;
	}

	public V remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

	public void clear() {
		// TODO Auto-generated method stub

	}

	public Set<K> keySet() {
		// TODO Auto-generated method stub
		return null;
	}

	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Entry<K, V>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Entry<K, V> getEntry(Object key) {
		// TODO Auto-generated method stub
		return null;
	}

}
