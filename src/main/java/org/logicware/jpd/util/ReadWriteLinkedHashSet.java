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
import java.util.LinkedHashSet;
import java.util.Set;

public class ReadWriteLinkedHashSet<E> extends ReadWriteSet<E> implements Set<E> {

	public ReadWriteLinkedHashSet() {
		super(new LinkedHashSet<E>());
	}

	public ReadWriteLinkedHashSet(Collection<? extends E> c) {
		super(new LinkedHashSet<E>(c));
	}

	public ReadWriteLinkedHashSet(int initialCapacity, float loadFactor) {
		super(new LinkedHashSet<E>(initialCapacity, loadFactor));
	}

	public ReadWriteLinkedHashSet(int initialCapacity) {
		super(new LinkedHashSet<E>(initialCapacity));
	}

}
