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

import org.prolobjectlink.Direction;
import org.prolobjectlink.GraphEdge;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractGraphEdge<E> extends AbstractGraphElement<E> implements GraphEdge<E> {

	private final Direction direction;

	public AbstractGraphEdge(E element, Direction direction) {
		super(element);
		this.direction = direction;
	}

	public final Class<?> getFromVertexElementClass() {
		return getFrom().getElementClass();
	}

	public final Class<?> getToVertexElementClass() {
		return getTo().getElementClass();
	}

	public final Direction getDirection() {
		return direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((direction == null) ? 0 : direction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractGraphEdge<?> other = (AbstractGraphEdge<?>) obj;
		return direction == other.direction;
	}

	@Override
	public String toString() {
		return "" + getElement() + "";
	}

}
