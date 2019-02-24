/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
