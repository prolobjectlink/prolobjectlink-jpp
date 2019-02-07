/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.graph;

import org.prolobjectlink.GraphVertex;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractGraphVertex<V> extends AbstractGraphElement<V> implements GraphVertex<V> {

	public AbstractGraphVertex(V element) {
		super(element);
	}

	public final <K> K unwrap(Class<K> cls) {
		if (cls.isAssignableFrom(getClass())) {
			return cls.cast(this);
		}
		return null;
	}

	public final boolean isWrappedFor(Class<?> cls) {
		return cls.isInstance(this);
	}

	@Override
	public String toString() {
		return "" + getElement() + "";
	}

}
