/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.prolog;

import java.util.Iterator;
import java.util.Set;

/**
 * @see PrologHashSet
 * @see PrologTreeSet
 * @author Jose Zalacain
 * @since 1.0
 * @param <E> generic object type
 */
abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

	private static final long serialVersionUID = 7705401669354067376L;

	public AbstractSet() {
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("[");
		Iterator<E> i = iterator();
		if (i.hasNext()) {
			E entry = i.next();
			result.append(entry);
			while (i.hasNext()) {
				entry = i.next();
				result.append(", ");
				result.append(entry);
			}
		}
		return result + "]";
	}

}
