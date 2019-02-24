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
package org.prolobjectlink;

/**
 * Graph Vertex
 * 
 * @param <V>
 * @author Jose Zalacain
 * @since 1.0
 */
public interface GraphVertex<V> extends GraphElement<V> {

	public Iterable<GraphVertex<V>> getIncomingsVertices();

	public Iterable<GraphVertex<V>> getOutgoingsVertices();

	public int countIncomings();

	public int countOutgoings();

	public boolean isWrappedFor(Class<?> cls);

	public <K> K unwrap(Class<K> cls);

	public boolean equals(Object obj);

	public int hashCode();

}
