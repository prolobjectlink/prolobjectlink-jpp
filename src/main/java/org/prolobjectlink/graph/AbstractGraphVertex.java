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
