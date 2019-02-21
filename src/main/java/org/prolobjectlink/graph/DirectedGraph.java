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
package org.prolobjectlink.graph;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.prolobjectlink.Direction;
import org.prolobjectlink.Graph;
import org.prolobjectlink.GraphEdge;
import org.prolobjectlink.GraphVertex;

/**
 * Directed graph implementation
 * 
 * @author Jose Zalacain
 *
 * @param <V> generic type for vertices
 * @param <E> generic type for edges
 * @since 1.0
 * @see AbstractGraph
 * @see Graph
 */
public class DirectedGraph<V, E> extends AbstractGraph<V, E> implements Graph<V, E> {

	public DirectedGraph() {
	}

	public DirectedGraph(Graph<V, E> graph) {
		super(graph);
	}

	public GraphEdge<E> addEdge(GraphVertex<V> from, GraphVertex<V> to, E edge, Direction direction) {
		GraphEdge<E> e = getEdge(from, to);
		if (e == null && from != null && to != null) {
			e = new GenericGraphEdge(edge, from, to, direction);
			switch (direction) {
			case IN:
				to.unwrap(GenericGraphVertex.class).incoming.put(from, e);
				break;
			case OUT:
				from.unwrap(GenericGraphVertex.class).outgoing.put(to, e);
				break;
			default:
				from.unwrap(GenericGraphVertex.class).outgoing.put(to, e);
				to.unwrap(GenericGraphVertex.class).incoming.put(from, e);
				break;
			}
			edges.add(e);
		}
		return e;
	}

	public final GraphVertex<V> addVertex(V vertex) {
		return addVertex(new GenericGraphVertex(vertex));
	}

	public void removeEdge(GraphVertex<V> from, GraphVertex<V> to) {
		GraphEdge<E> edge = getEdge(from, to);
		if (edge != null) {
			edges.remove(edge);
			from.unwrap(GenericGraphVertex.class).outgoing.remove(to);
			to.unwrap(GenericGraphVertex.class).incoming.remove(from);
		}
	}

	public final void removeVertex(GraphVertex<V> vertex) {
		vertices.remove(vertex);
		removeEdges(vertex);
	}

	public final Iterable<GraphEdge<E>> outEdges(GraphVertex<V> vertex) {
		if (vertex != null) {
			GenericGraphVertex v = vertex.unwrap(GenericGraphVertex.class);
			return v.outgoing.values();
		}
		return new ArrayList<GraphEdge<E>>();
	}

	public final Iterable<GraphEdge<E>> inEdges(GraphVertex<V> vertex) {
		if (vertex != null) {
			GenericGraphVertex v = vertex.unwrap(GenericGraphVertex.class);
			return new ArrayList<GraphEdge<E>>(v.incoming.values());
		}
		return new ArrayList<GraphEdge<E>>();
	}

	public final GraphEdge<E> getEdge(GraphVertex<V> from, GraphVertex<V> to) {
		if (from != null) {
			GenericGraphVertex v = from.unwrap(GenericGraphVertex.class);
			for (GraphEdge<E> edge : v.outgoing.values()) {
				if (edge.getTo().equals(to)) {
					return edge;
				}
			}
		}
		return null;
	}

	public GraphEdge<E> getEdge(V from, V to) {
		GraphVertex<V> f = new GenericGraphVertex(from);
		GraphVertex<V> t = new GenericGraphVertex(to);
		return getEdge(f, t);
	}

	private class GenericGraphEdge extends AbstractGraphEdge<E> implements GraphEdge<E> {

		private final GraphVertex<V> from;
		private final GraphVertex<V> to;

		private GenericGraphEdge(E element, GraphVertex<V> from, GraphVertex<V> to, Direction direction) {
			super(element, direction);
			this.from = from;
			this.to = to;

		}

		public GraphVertex<V> getFrom() {
			return from;
		}

		public GraphVertex<V> getTo() {
			return to;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

	}

	protected class GenericGraphVertex extends AbstractGraphVertex<V> implements GraphVertex<V> {

		public Map<GraphVertex<V>, GraphEdge<E>> incoming;
		public Map<GraphVertex<V>, GraphEdge<E>> outgoing;

		protected GenericGraphVertex(V element) {
			super(element);
			incoming = new LinkedHashMap<GraphVertex<V>, GraphEdge<E>>();
			outgoing = new LinkedHashMap<GraphVertex<V>, GraphEdge<E>>();
		}

		public Iterable<GraphVertex<V>> getIncomingsVertices() {
			return new ArrayList<GraphVertex<V>>(incoming.keySet());
		}

		public Iterable<GraphVertex<V>> getOutgoingsVertices() {
			return new ArrayList<GraphVertex<V>>(outgoing.keySet());
		}

		public int countIncomings() {
			return incoming.size();
		}

		public int countOutgoings() {
			return outgoing.size();
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			return super.equals(obj);
		}

	}

}
