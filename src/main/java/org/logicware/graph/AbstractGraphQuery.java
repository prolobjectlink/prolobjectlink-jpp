/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.graph;

import org.logicware.Graph;
import org.logicware.GraphEdge;
import org.logicware.GraphQuery;
import org.logicware.GraphVertex;

public class AbstractGraphQuery<V, E> implements GraphQuery<V, E> {

	private final Graph<V, E> graph;

	public AbstractGraphQuery(Graph<V, E> graph) {
		this.graph = graph;
	}

	public GraphQuery<V, E> has(String key) {
		// TODO Auto-generated method stub
		return null;

	}

	public GraphQuery<V, E> hasNot(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphQuery<V, E> has(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphQuery<V, E> hasNot(String key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Comparable<T>> GraphQuery<V, E> has(String key, T value, Comparable<T> compare) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T extends Comparable<?>> GraphQuery<V, E> interval(String key, T startValue, T endValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public GraphQuery<V, E> limit(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	public final Iterable<GraphEdge<E>> edges() {
		// TODO Auto-generated method stub
		return null;
	}

	public final Iterable<GraphVertex<V>> vertices() {
		// TODO Auto-generated method stub
		return null;
	}

}
