/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
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
