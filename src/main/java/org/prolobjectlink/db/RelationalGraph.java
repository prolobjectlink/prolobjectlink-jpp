/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.prolobjectlink.Direction;
import org.prolobjectlink.Graph;
import org.prolobjectlink.GraphEdge;
import org.prolobjectlink.GraphVertex;
import org.prolobjectlink.graph.AbstractGraphEdge;
import org.prolobjectlink.graph.DirectedGraph;
import org.prolobjectlink.graph.RelationalType;
import org.prolobjectlink.prolog.PrologTerm;

public class RelationalGraph<V, R> extends DirectedGraph<V, R> implements Graph<V, R> {

	public RelationalGraph() {

	}

	public RelationalGraph(RelationalGraph<V, R> graph) {
		super(graph);
	}

	public GraphEdge<R> addEdge(GraphVertex<V> from, GraphVertex<V> to, R edge, Direction direction) {
		GraphEdge<R> e = getEdge(from, to);

		// determining the relation type from R edge
		if (e == null && from != null && to != null) {

			// one to one
			if (isLinkLink(from, to)) {
				e = new OneToOneEdge(edge, from, to, direction);
			}

			// one to many
			else if (isLinkLinkList(from, to) || isLinkLinkSet(from, to) || isLinkLinkMap(from, to)) {
				e = new OneToManyEdge(edge, from, to, direction);
			}

			// many to one
			else if (isLinkListLink(from, to) || isLinkSetLink(from, to) || isLinkMapLink(from, to)) {
				e = new ManyToOneEdge(edge, from, to, direction);
			}

			// many to many
			else {
				e = new ManyToManyEdge(edge, from, to, direction);
			}

			from.unwrap(GenericGraphVertex.class).outgoing.put(to, e);
			to.unwrap(GenericGraphVertex.class).incoming.put(from, e);
			edges.add(e);

		}
		return e;
	}

	// one to one

	public final boolean isLinkLink(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return !isCollection(from) && !isCollection(to);
	}

	// one to many

	public final boolean isLinkLinkList(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return !isCollection(from) && isList(to);
	}

	public final boolean isLinkLinkMap(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return !isCollection(from) && isMap(to);
	}

	public final boolean isLinkLinkSet(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return !isCollection(from) && isSet(to);
	}

	// many to one

	public final boolean isLinkListLink(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isList(from) && !isCollection(to);
	}

	public final boolean isLinkMapLink(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isMap(from) && !isCollection(to);
	}

	public final boolean isLinkSetLink(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isSet(from) && !isCollection(to);
	}

	// many to many

	public final boolean isLinkListLinkList(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isList(from) && isList(to);
	}

	public final boolean isLinkMapLinkList(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isMap(from) && isList(to);
	}

	public final boolean isLinkSetLinkList(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isSet(from) && isList(to);
	}

	public final boolean isLinkListLinkMap(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isList(from) && isMap(to);
	}

	public final boolean isLinkMapLinkMap(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isMap(from) && isMap(to);
	}

	public final boolean isLinkSetLinkMap(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isSet(from) && isMap(to);
	}

	public final boolean isLinkListLinkSet(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isList(from) && isSet(to);
	}

	public final boolean isLinkMapLinkSet(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isMap(from) && isSet(to);
	}

	public final boolean isLinkSetLinkSet(GraphVertex<V> f, GraphVertex<V> t) {
		Class<?> from = f.getElementClass();
		Class<?> to = t.getElementClass();
		return isSet(from) && isSet(to);
	}

	public final boolean isList(Class<?> clazz) {
		return clazz.isAssignableFrom(List.class);
	}

	public final boolean isMap(Class<?> clazz) {
		return clazz.isAssignableFrom(Map.class);
	}

	public final boolean isSet(Class<?> clazz) {
		return clazz.isAssignableFrom(Set.class);
	}

	public final boolean isCollection(Class<?> clazz) {
		return clazz.isAssignableFrom(Collection.class);
	}

	/**
	 * Partial implementation of {@link RelationalEdge} interface.
	 * 
	 * @author Jose Zalacain
	 *
	 * @param <R> involved relation object type
	 * @since 1.0
	 */
	public abstract class AbstractRelationEdge extends AbstractGraphEdge<R> implements RelationalEdge<R> {

		private final RelationalType relationType;
		private final Map<PrologTerm, PrologTerm> cache;

		public AbstractRelationEdge(R element, Direction direction, RelationalType relationType) {
			super(element, direction);
			this.relationType = relationType;
			this.cache = new WeakHashMap<PrologTerm, PrologTerm>();
		}

		// one to one

		public final boolean isLinkLink() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return !isCollection(from) && !isCollection(to);
		}

		// one to many

		public final boolean isLinkLinkList() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return !isCollection(from) && isList(to);
		}

		public final boolean isLinkLinkMap() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return !isCollection(from) && isMap(to);
		}

		public final boolean isLinkLinkSet() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return !isCollection(from) && isSet(to);
		}

		// many to one

		public final boolean isLinkListLink() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isList(from) && !isCollection(to);
		}

		public final boolean isLinkMapLink() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isMap(from) && !isCollection(to);
		}

		public final boolean isLinkSetLink() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isSet(from) && !isCollection(to);
		}

		// many to many

		public final boolean isLinkListLinkList() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isList(from) && isList(to);
		}

		public final boolean isLinkMapLinkList() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isMap(from) && isList(to);
		}

		public final boolean isLinkSetLinkList() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isSet(from) && isList(to);
		}

		public final boolean isLinkListLinkMap() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isList(from) && isMap(to);
		}

		public final boolean isLinkMapLinkMap() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isMap(from) && isMap(to);
		}

		public final boolean isLinkSetLinkMap() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isSet(from) && isMap(to);
		}

		public final boolean isLinkListLinkSet() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isList(from) && isSet(to);
		}

		public final boolean isLinkMapLinkSet() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isMap(from) && isSet(to);
		}

		public final boolean isLinkSetLinkSet() {
			Class<?> from = getFromVertexElementClass();
			Class<?> to = getToVertexElementClass();
			return isSet(from) && isSet(to);
		}

		public final boolean isList(Class<?> clazz) {
			return clazz.isAssignableFrom(List.class);
		}

		public final boolean isMap(Class<?> clazz) {
			return clazz.isAssignableFrom(Map.class);
		}

		public final boolean isSet(Class<?> clazz) {
			return clazz.isAssignableFrom(Set.class);
		}

		public final boolean isCollection(Class<?> clazz) {
			return clazz.isAssignableFrom(Collection.class);
		}

		public final Map<PrologTerm, PrologTerm> getCache() {
			return cache;
		}

		public final RelationalType getRelationType() {
			return relationType;
		}

	}

	private abstract class RelationalGraphEdge extends AbstractRelationEdge implements RelationalEdge<R> {

		private final GraphVertex<V> from;
		private final GraphVertex<V> to;

		private RelationalGraphEdge(R relation, GraphVertex<V> from, GraphVertex<V> to, Direction direction,
				RelationalType relationType) {
			super(relation, direction, relationType);
			this.from = from;
			this.to = to;
		}

		public GraphVertex<V> getFrom() {
			return from;
		}

		public GraphVertex<V> getTo() {
			return to;
		}

	}

	private final class OneToOneEdge extends RelationalGraphEdge implements RelationalEdge<R> {

		private OneToOneEdge(R relation, GraphVertex<V> from, GraphVertex<V> to, Direction direction) {
			super(relation, from, to, direction, RelationalType.ONE_TO_ONE);
		}

	}

	private final class OneToManyEdge extends RelationalGraphEdge implements RelationalEdge<R> {

		private OneToManyEdge(R relation, GraphVertex<V> from, GraphVertex<V> to, Direction direction) {
			super(relation, from, to, direction, RelationalType.ONE_TO_MANY);
		}

	}

	private final class ManyToOneEdge extends RelationalGraphEdge implements RelationalEdge<R> {

		private ManyToOneEdge(R relation, GraphVertex<V> from, GraphVertex<V> to, Direction direction) {
			super(relation, from, to, direction, RelationalType.MANY_TO_ONE);
		}

	}

	private final class ManyToManyEdge extends RelationalGraphEdge implements RelationalEdge<R> {

		private ManyToManyEdge(R relation, GraphVertex<V> from, GraphVertex<V> to, Direction direction) {
			super(relation, from, to, direction, RelationalType.MANY_TO_MANY);
		}

	}

}
