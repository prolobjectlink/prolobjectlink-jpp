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
package io.github.prolobjectlink.db;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 * Query is a concept for retrieve objects from object containers. The query
 * object is created through {@link Storage},{@link PersistentContainer} which
 * have three variants {@link Storage#createQuery(Class)},
 * {@link Storage#createQuery(Object)} (query by example ) and
 * {@link Storage#createQuery(Predicate)}. This methods force to the prolog
 * engine to find all possibles solution for the formulated query and with this
 * solutions the query is builded. All before mentioned methods return a
 * {@link TypedQuery} object that hold a solution list that can be retrieved
 * whit the {@link TypedQuery#getSolutions()} method or simple retrieve the
 * first solution with {@link TypedQuery#getSolution()}.
 * </p>
 * 
 * <pre>
 * TypedQuery&lt;Point&gt; query = objectContainer.createQuery(Point.class);
 * TypedQuery&lt;Point&gt; query = objectContainer.createQuery(new Point(3, 14));
 * </pre>
 * 
 * <p>
 * {@link TypedQuery} object have some useful methods for fix the max solution
 * numbers to retrieve and allow fix the first solution position in the internal
 * list.
 * </p>
 * 
 * <pre>
 * query.setMaxSolution(2).setFirstSolution(2);
 * 
 * // fix 2 solutions number beginning at second position
 * </pre>
 * 
 * <p>
 * The internal solution list can be ordering in ascendant, descendant or by
 * some given comparator interface implementation. See
 * {@link TypedQuery#orderBy(Comparator)}
 * </p>
 * 
 * <pre>
 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).orderAscending().getSolutions();
 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).orderDescending().getSolutions();
 * </pre>
 * 
 * <p>
 * {@link TypedQuery} object allow select the objects to retrieve from all
 * solution internal list given a {@link Predicate} implementation of the match
 * method. See TypedQuery#having(Predicate)
 * </p>
 * 
 * <p>
 * {@link TypedQuery} object allow walk over the graph formed by the class
 * relationship described by your fields and retrieve the solutions builded with
 * objects values from visited field. See {@link TypedQuery#descend(String)}
 * </p>
 * 
 * <pre>
 * objectContainer.createQuery(Polygon.class).descend(&quot;segment0&quot;).descend(&quot;point0&quot;).descend(&quot;idp&quot;).getSolutions();
 * </pre>
 * 
 * <p>
 * {@link TypedQuery} object implement {@link Iterator} interface providing an
 * easy way to retrieve each object present in the internal solution list.
 * </p>
 * 
 * <pre>
 * TypedQuery&lt;Point&gt; query = container.createQuery(Point.class);
 * for (Iterator&lt;Point&gt; i = query; e.hasNext();) {
 * 	Point point = (Point) e.next();
 * 	// do something with the retrieved point object
 * }
 * </pre>
 * 
 * @param <O> Root object template class
 * 
 * @see Query
 * @author Jose Zalacain
 * @since 1.0
 * 
 */
public interface TypedQuery<O> extends Iterator<O>, Serializable {

	/**
	 * Return the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @return maximum number of solutions
	 * @since 1.0
	 */
	public int getMaxSolution();

	/**
	 * Fix the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @param maxSolution maximum number of solutions
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 */
	public TypedQuery<O> setMaxSolution(int maxSolution);

	/**
	 * Return the fixed position for the first solution to retrieve
	 * 
	 * @return position for the first solution
	 * @since 1.0
	 */
	public int getFirstSolution();

	/**
	 * Fix the position of the first solution to retrieve.
	 * 
	 * @param firstSolution position of the first solution
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 */
	public TypedQuery<O> setFirstSolution(int firstSolution);

	/**
	 * Return a single solution of the current {@code TypedQuery}. The solution
	 * object type is in correspondence whit root class type specified in the
	 * criteria query construction when its is created through {@link Storage}.
	 * 
	 * <pre>
	 * Point point = objectContainer.createQuery(Point.class).getSolution();
	 * </pre>
	 * 
	 * @return Return a single solution of the current {@code TypedQuery}
	 * @since 1.0
	 */
	public O getSolution();

	/**
	 * Return a all solutions of the current {@code TypedQuery}. The solutions list
	 * object type is in correspondence whit root class type specified in the
	 * criteria query construction when its is created through {@link Storage}.
	 * 
	 * <pre>
	 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).getSolutions();
	 * </pre>
	 * 
	 * @return Return all solutions of the current {@code TypedQuery}
	 * @since 1.0
	 */
	public List<O> getSolutions();

	/**
	 * <p>
	 * Ascendant ordering of all solutions to be returned by the solutions methods
	 * invocation. The order criterion is base in object hash code. Is suggested and
	 * very important the {@code Object#hashCode()} implementation in the model
	 * objects to achieve a corrected solution . The alternation with the
	 * {@code TypedQuery#orderDescending()} invocation only apply the latest
	 * ordering method invocation. For example if we want the list of stored point
	 * in ascendant order:
	 * </p>
	 * 
	 * <pre>
	 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).orderAscending().getSolutions();
	 * </pre>
	 * 
	 * <p>
	 * This example return a list with a, b, c, d Point class instances objects
	 * </p>
	 * 
	 * 
	 * 
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 * @see TypedQuery#orderDescending()
	 * @see TypedQuery#orderBy(Comparator)
	 */
	public TypedQuery<O> orderAscending();

	/**
	 * Descendant ordering of all solutions to be returned by the solutions methods
	 * invocation. The order criterion is base in object hash code. Is suggested and
	 * very important the {@code Object#hashCode()} implementation in the model
	 * objects to achieve a corrected solution . The alternation with the
	 * {@code TypedQuery#orderAscending()} invocation only apply the latest ordering
	 * method invocation. For example if we want the list of stored point in
	 * descendant order:
	 * 
	 * <pre>
	 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).orderDescending().getSolutions();
	 * </pre>
	 * 
	 * <p>
	 * This example return a list with d, c, b, a Point class instances objects
	 * </p>
	 * 
	 * 
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 * @see TypedQuery#orderAscending()
	 * @see TypedQuery#orderBy(Comparator)
	 */
	public TypedQuery<O> orderDescending();

	/**
	 * <p>
	 * Ordering of all solutions to be returned by the solutions methods invocation
	 * by a given comparator implementation. This alternative is useful when most
	 * specific compare criteria is needed between objects.
	 * </p>
	 * 
	 * <p>
	 * The followed example is equivalent to apply ascending order to point
	 * solutions based in point identifier. This example return a list with a, b, c,
	 * d Point class instances objects
	 * </p>
	 * 
	 * <pre>
	 * List&lt;Point&gt; points = objectContainer.createQuery(Point.class).orderBy(new Comparator&lt;Point&gt;() {
	 * 
	 * 	public int compare(Point o1, Point o2) {
	 * 		if (o1.getIdp().compareTo(o2.getIdp()) &lt; 0) {
	 * 			return -1;
	 * 		} else if (o1.getIdp().compareTo(o2.getIdp()) &gt; 0) {
	 * 			return 1;
	 * 		}
	 * 		return 0;
	 * 	}
	 * }).getSolutions();
	 * </pre>
	 * 
	 * @param comparator comparator used for compare an ordering solutions to be
	 *                   returned
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 * @see TypedQuery#orderAscending()
	 * @see TypedQuery#orderDescending()
	 */
	public TypedQuery<O> orderBy(Comparator<O> comparator);

	public O max();

	public O max(Comparator<O> comparator);

	public O min();

	public O min(Comparator<O> comparator);

	/**
	 * <p>
	 * Create a new {@code TypedQuery} descending to object store in the specified
	 * field that match with name parameter and present in the objects hold by the
	 * query instance. If the specified field is not found in the objects hold by
	 * the query instance then an exception is raised. This operation walk the graph
	 * represented by object relationship determined by yours field.
	 * </p>
	 * 
	 * <p>
	 * For example suppose a Polygon class is composed by segments objects and one
	 * segment by points objects and the point by one point identifier. If we want
	 * all identifiers of the initials points of the initials segments from all
	 * polygons:
	 * </p>
	 * 
	 * <pre>
	 * objectContainer.createQuery(Polygon.class).descend(&quot;segment0&quot;).descend(&quot;point0&quot;).descend(&quot;idp&quot;).getSolutions();
	 * </pre>
	 * 
	 * @param name class field name to descend
	 * @return new instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 */
	public TypedQuery<Object> descend(String name);

	public int count();

	/**
	 * Release all allocations and references for the current object
	 * 
	 * @since 1.0
	 */
	public void dispose();

}
