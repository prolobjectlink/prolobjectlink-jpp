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
import java.util.HashSet;
import java.util.Set;

public class ReadWriteHashSet<E> extends ReadWriteSet<E> implements Set<E> {

    public ReadWriteHashSet() {
	super(new HashSet<E>());
    }

    public ReadWriteHashSet(Collection<? extends E> c) {
	super(new HashSet<E>(c));
    }

    public ReadWriteHashSet(int initialCapacity, float loadFactor) {
	super(new HashSet<E>(initialCapacity, loadFactor));
    }

    public ReadWriteHashSet(int initialCapacity) {
	super(new HashSet<E>(initialCapacity));
    }

}
