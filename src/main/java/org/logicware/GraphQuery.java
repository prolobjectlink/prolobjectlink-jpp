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
package org.logicware;

public interface GraphQuery<V, E> {

	/**
	 * Filter out elements that do not have a property with provided key.
	 *
	 * @param key
	 *            the key of the property
	 * @return the modified query object
	 */
	public GraphQuery<V, E> has(String key);

	/**
	 * Filter out elements that have a property with provided key.
	 *
	 * @param key
	 *            the key of the property
	 * @return the modified query object
	 */
	public GraphQuery<V, E> hasNot(String key);

	/**
	 * Filter out elements that do not have a property value equal to provided
	 * value.
	 *
	 * @param key
	 *            the key of the property
	 * @param value
	 *            the value to check against
	 * @return the modified query object
	 */
	public GraphQuery<V, E> has(String key, Object value);

	/**
	 * Filter out elements that have a property value equal to provided value.
	 *
	 * @param key
	 *            the key of the property
	 * @param value
	 *            the value to check against
	 * @return the modified query object
	 */
	public GraphQuery<V, E> hasNot(String key, Object value);

	/**
	 * Filter out the element if it does not have a property with a comparable
	 * value.
	 *
	 * @param key
	 *            the key of the property
	 * @param value
	 *            the value to check against
	 * @param compare
	 *            the comparator to use for comparison
	 * @return the modified query object
	 */
	public <T extends Comparable<T>> GraphQuery<V, E> has(String key, T value, Comparable<T> compare);

	/**
	 * Filter out the element of its property value is not within the provided
	 * interval.
	 *
	 * @param key
	 *            the key of the property
	 * @param startValue
	 *            the inclusive start value of the interval
	 * @param endValue
	 *            the exclusive end value of the interval
	 * @return the modified query object
	 */
	public <T extends Comparable<?>> GraphQuery<V, E> interval(String key, T startValue, T endValue);

	/**
	 * Filter out the element if the take number of incident/adjacent elements
	 * to retrieve has already been reached.
	 *
	 * @param limit
	 *            the take number of elements to return
	 * @return the modified query object
	 */
	public GraphQuery<V, E> limit(int limit);

	/**
	 * Execute the query and return the matching edges.
	 *
	 * @return the unfiltered incident edges
	 */
	public Iterable<GraphEdge<E>> edges();

	/**
	 * Execute the query and return the vertices on the other end of the
	 * matching edges.
	 *
	 * @return the unfiltered adjacent vertices
	 */
	public Iterable<GraphVertex<V>> vertices();

}
