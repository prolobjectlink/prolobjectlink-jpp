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
 * Graph
 *
 * @param <V>
 * @param <E>
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Graph<V, E> {

	public GraphEdge<E> addEdge(GraphVertex<V> from, GraphVertex<V> to, E edge, Direction direction);

	public GraphEdge<E> addEdge(GraphVertex<V> from, GraphVertex<V> to, E edge);

	public GraphEdge<E> addEdge(V from, V to, E edge, Direction direction);

	public GraphEdge<E> addEdge(V from, V to, E edge);

	public GraphVertex<V> addVertex(GraphVertex<V> vertex);

	public GraphVertex<V> addVertex(V vertex);

	/**
	 * Return the adjacent vertex from/out given vertex and incident edge.
	 * 
	 * @param vertex from/out vertex to find the adjacent vertex.
	 * @param edge   incident edge.
	 * @return adjacent vertex.
	 * @since 1.0
	 */
	public GraphVertex<V> getVertex(GraphVertex<V> vertex, GraphEdge<E> edge);

	public void removeEdge(GraphVertex<V> from, GraphVertex<V> to);

	public void removeVertex(GraphVertex<V> vertex);

	public void removeEdges(GraphVertex<V> vertex);

	public Iterable<GraphEdge<E>> outEdges(GraphVertex<V> vertex);

	public Iterable<GraphEdge<E>> inEdges(GraphVertex<V> vertex);

	public int countOutEdges(GraphVertex<V> vertex);

	public int countInEdges(GraphVertex<V> vertex);

	public GraphEdge<E> getEdge(GraphVertex<V> from, GraphVertex<V> to);

	public GraphEdge<E> getEdge(V from, V to);

	public GraphEdge<E> getEdge(Object o);

	public GraphVertex<V> getVertex(Object o);

	public Iterable<GraphEdge<E>> getEdges();

	public Iterable<GraphVertex<V>> getVertices();

	public boolean equals(Object obj);

	public int countVertices();

	public int countEdges();

	public int hashCode();

	public boolean isEmpty();

	public void clear();

}
