/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd;

import java.util.List;

/**
 * <p>
 * {@link ConstraintQuery} concept used for help query creation front to the
 * limitation that have the class queries about of restriction.
 * {@link ConstraintQuery} concept supplies some Prolog operators methods for
 * append restrictions to the criteria object and build a final query from this
 * criteria.
 * 
 * A criteria is created by the {@link Document} taking as parameter the root
 * restriction class being this the first step of the work flow. Once created
 * criteria object, append the desired restrictions and finally create the query
 * object. The query object created by a criteria is an instance of TypedQuery
 * class.
 * 
 * </p>
 * 
 * <p>
 * The restrictions are divided in three groups attending to the operator
 * nature, logics (and, or, not), standard order Object comparison operators (=,
 * \=, ==, \==, @<, @>, @=<, @>=) and arithmetics operators (=:=, =\\=, >,
 * <, >=, =<).
 * 
 * 
 * For Object comparison operators and arithmetics operators the application of
 * this operators is over a class attribute that was annotated with the Argument
 * annotation as left operand and the right operand is a Object. In the
 * arithmetics operators cases this checking that the resolved operands be
 * numerics. If the operands are not numerics an exception is raised.
 * 
 * </p>
 * 
 * <pre>
 * CriteriaQuery criteria = container.createCriteriaQuery(Point.class);
 * criteria.after(&quot;id&quot;, &quot;a&quot;);
 * criteria.afterEquals(&quot;id&quot;, &quot;a&quot;);
 * criteria.unify(&quot;id&quot;, &quot;b&quot;);
 * criteria.before(&quot;id&quot;, &quot;c&quot;);
 * criteria.beforeEquals(&quot;id&quot;, &quot;c&quot;);
 * criteria.equivalent(&quot;id&quot;, &quot;b&quot;);
 * criteria.greater(&quot;x&quot;, 3.0);
 * criteria.less(&quot;y&quot;, 11.0);
 * criteria.greaterEqual(&quot;x&quot;, 3.0);
 * criteria.lessEqual(&quot;y&quot;, 11.0);
 * Point point = criteria.getSolution();
 * System.out.println(point);
 * </pre>
 * 
 * Formulated Prolog Query
 * 
 * <pre>
 * ?- 'org.prolobjectlink.domain.geometry.Point'( Id, X, Y ), 
 * 	Id @> a, 
 * 	Id @>= a, 
 * 	Id = b, 
 * 	Id @< c, 
 * 	Id @=< c, 
 * 	Id == b, 
 * 	X > 3.0, 
 * 	Y < 11.0, 
 * 	X >= 3.0, 
 * 	Y =< 11.0.
 * </pre>
 * 
 * 
 * <pre>
 * CriteriaQuery criteria = container.createCriteriaQuery(Segment.class);
 * criteria.equivalent(&quot;ids&quot;, &quot;bc&quot;);
 * criteria.unify(&quot;point0&quot;, new Point(&quot;b&quot;, 3.5, 10.14));
 * criteria.unify(&quot;point1&quot;, new Point(&quot;c&quot;, 3.5, 10.14));
 * Segment segment = criteria.getSolution();
 * System.out.println(segment);
 * </pre>
 * 
 * Formulated Prolog Query
 * 
 * <pre>
 * ?- 'org.prolobjectlink.domain.geometry.Segment'( Ids, Point0, Point1 ),
 *  Ids = bc, 
 *  Point0 = point( b, 3.5, 10.14 ), 
 *  Point1 = point( c, 3.5, 10.14 ).
 * </pre>
 * 
 * @see Query
 * @see TypedQuery
 * @author Jose Zalacain
 * @since 1.0
 */
public interface ConstraintQuery<O> {

	/**
	 * Return the maximum number of solutions to retrieve by the formulated
	 * query
	 * 
	 * @return maximum number of solutions
	 * @since 1.0
	 */
	public int getMaxSolution();

	/**
	 * Fix the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @param maxSolution
	 *            maximum number of solutions
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> setMaxSolution(int maxSolution);

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
	 * @param firstSolution
	 *            position of the first solution
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> setFirstSolution(int firstSolution);

	/**
	 * Add a class that will be converted to prolog structure and negate the
	 * existence of the equivalent prolog structure. This operator is
	 * correspondent to prolog negation <b>(\+) or (not)</b>.
	 * 
	 * @param clazz
	 *            class to be converted to prolog structure
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> not(Class<?> clazz);

	/**
	 * Add a class that will be converted to prolog structure like a conjunction
	 * in the formulated query.This operator is correspondent to prolog comma
	 * conjunction <b>( , )</b>.
	 * 
	 * @param clazz
	 *            class to be converted to prolog structure
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> and(Class<?> clazz);

	/**
	 * Add a class that will be converted to prolog structure like a disjunction
	 * in the formulated query.This operator is correspondent to prolog
	 * semicolon disjunction <b>( ; )</b>.
	 * 
	 * @param clazz
	 *            class to be converted to prolog structure
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> or(Class<?> clazz);

	// Object comparison operators

	/**
	 * Unify operator method put in the criteria object the restriction of the
	 * specified field store a Object <b>unify</b> to Object value when the
	 * query will be created and executed. The restriction is appended as a
	 * conjunction to the concrete prolog query to resolve by the engine.This
	 * method is the representation of prolog Object unification <b>( = )</b>
	 * that succeeds if a Object1 <b>unify</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 = Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #notUnify(String, Object)
	 */
	public ConstraintQuery<O> unify(String field, Object value);

	/**
	 * NotUnify operator method put in the criteria object the restriction of
	 * the specified field store a Object <b>not unify</b> to Object value when
	 * the query will be created and executed. The restriction is appended as a
	 * conjunction to the concrete prolog query to resolve by the engine.This
	 * method is the representation of prolog Object not unification <b>( \=
	 * )</b> that succeeds if a Object1 <b>not unify</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 \= Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #unify(String, Object)
	 */
	public ConstraintQuery<O> notUnify(String field, Object value);

	/**
	 * Equivalent operator method put in the criteria object the restriction of
	 * the specified field store a Object <b>equivalent</b> to Object value when
	 * the query will be created and executed. The restriction is appended as a
	 * conjunction to the concrete prolog query to resolve by the engine.This
	 * method is the representation of prolog Object comparison <b>( == )</b>
	 * that succeeds if a Object1 is <b>equivalent</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 == Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #notEquivalent(String, Object)
	 */
	public ConstraintQuery<O> equivalent(String field, Object value);

	/**
	 * NotEquivalent operator method put in the criteria object the restriction
	 * of the specified field store a Object <b>not equivalent</b> to Object
	 * value when the query will be created and executed. The restriction is
	 * appended as a conjunction to the concrete prolog query to resolve by the
	 * engine.This method is the representation of prolog Object comparison <b>(
	 * \== )</b> that succeeds if a Object1 is <b>not equivalent</b> to a
	 * Object2.
	 * 
	 * <pre>
	 * Object1 \== Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #equivalent(String, Object)
	 */
	public ConstraintQuery<O> notEquivalent(String field, Object value);

	/**
	 * Before operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>before</b> of the Object value
	 * attending to prolog standard order, when the query will be created and
	 * executed. The restriction is appended as a conjunction to the concrete
	 * prolog query to resolve by the engine.This method is the representation
	 * of prolog Object comparison <b>( @< )</b> that succeeds if a Object1 is
	 * <b>before</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 @< Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #beforeEquals(String, Object)
	 * @see #after(String, Object)
	 * @see #afterEquals(String, Object)
	 */
	public ConstraintQuery<O> before(String field, Object value);

	/**
	 * After operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>after</b> of the Object value
	 * attending to prolog standard order, when the query will be created and
	 * executed. The restriction is appended as a conjunction to the concrete
	 * prolog query to resolve by the engine.This method is the representation
	 * of prolog Object comparison <b>( @> )</b> that succeeds if a Object1 is
	 * <b>after</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 @> Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #before(String, Object)
	 * @see #beforeEquals(String, Object)
	 * @see #afterEquals(String, Object)
	 */
	public ConstraintQuery<O> after(String field, Object value);

	/**
	 * This operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>before or equal</b> of the
	 * Object value attending to prolog standard order, when the query will be
	 * created and executed. The restriction is appended as a conjunction to the
	 * concrete prolog query to resolve by the engine.This method is the
	 * representation of prolog Object comparison <b>( @=< )</b> that succeeds
	 * if a Object1 is <b>before or equal</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 @=< Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #before(String, Object)
	 * @see #after(String, Object)
	 * @see #afterEquals(String, Object)
	 */
	public ConstraintQuery<O> beforeEquals(String field, Object value);

	/**
	 * This operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>after or equal</b> of the
	 * Object value attending to prolog standard order, when the query will be
	 * created and executed. The restriction is appended as a conjunction to the
	 * concrete prolog query to resolve by the engine.This method is the
	 * representation of prolog Object comparison <b>( @>= )</b> that succeeds
	 * if a Object1 is <b>after or equal</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 @>= Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #before(String, Object)
	 * @see #beforeEquals(String, Object)
	 * @see #after(String, Object)
	 */
	public ConstraintQuery<O> afterEquals(String field, Object value);

	// arithmetics expression operators

	/**
	 * This operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>equal</b> of the Object value
	 * attending to prolog standard order, when the query will be created and
	 * executed. The restriction is appended as a conjunction to the concrete
	 * prolog query to resolve by the engine.This method is the representation
	 * of prolog Object comparison <b>( =:= )</b> that succeeds if a Object1 is
	 * <b>after or equal</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 =:= Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #notEquals(String, Object)
	 */
	public ConstraintQuery<O> equals(String field, Object value);

	/**
	 * This operator method put in the criteria object the restriction of the
	 * specified field store a Object that is <b>not equal</b> of the Object
	 * value attending to prolog standard order, when the query will be created
	 * and executed. The restriction is appended as a conjunction to the
	 * concrete prolog query to resolve by the engine.This method is the
	 * representation of prolog Object comparison <b>( =\= )</b> that succeeds
	 * if a Object1 is <b>after or equal</b> to a Object2.
	 * 
	 * <pre>
	 * Object1 =\= Object2
	 * </pre>
	 * 
	 * @param field
	 *            class field annotated with argument annotation.
	 * @param value
	 *            Object value to compare.
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 * 
	 * @see #equals(String, Object)
	 */
	public ConstraintQuery<O> notEquals(String field, Object value);

	public ConstraintQuery<O> greater(String field, Object value);

	public ConstraintQuery<O> less(String field, Object value);

	public ConstraintQuery<O> greaterEquals(String field, Object value);

	public ConstraintQuery<O> lessEquals(String field, Object value);

	// criteria for two declared fields

	public ConstraintQuery<O> unifyField(String left, String right);

	public ConstraintQuery<O> notUnifyField(String left, String right);

	public ConstraintQuery<O> equivalentField(String left, String right);

	public ConstraintQuery<O> notEquivalentField(String left, String right);

	public ConstraintQuery<O> beforeField(String left, String right);

	public ConstraintQuery<O> afterField(String left, String right);

	public ConstraintQuery<O> beforeEqualsField(String left, String right);

	public ConstraintQuery<O> afterEqualsField(String left, String right);

	public ConstraintQuery<O> equalsField(String left, String right);

	public ConstraintQuery<O> notEqualsField(String left, String right);

	public ConstraintQuery<O> lessField(String left, String right);

	public ConstraintQuery<O> greaterField(String left, String right);

	public ConstraintQuery<O> greaterEqualsField(String left, String right);

	public ConstraintQuery<O> lessEqualsField(String left, String right);

	/**
	 * Return a single solution of the current criteria query. The solution
	 * object type is in correspondence whit root class type specified in the
	 * criteria query construction when its is created through {@link Document}.
	 * It is a shortcut to the create the typed query from this criteria query
	 * and request to the typed query one single solution.
	 * 
	 * <pre>
	 * Point point = objectContainer.createCriteriaQuery(Point.class).getSolution();
	 * </pre>
	 * 
	 * @return Return a single solution of the current criteria query
	 * @throws NonSolutionError
	 *             throw an exception if not exist any solution object
	 * @since 1.0
	 */
	public O getSolution() throws NonSolutionError;

	/**
	 * Return a all solutions of the current criteria query. The solutions list
	 * object type is in correspondence whit root class type specified in the
	 * criteria query construction when its is created through {@link Document}.
	 * It is a shortcut to the create the typed query from this criteria query
	 * and request to the typed query all solutions.
	 * 
	 * <pre>
	 * List&lt;Point&gt; points = objectContainer.createCriteriaQuery(Point.class).getSolutions();
	 * </pre>
	 * 
	 * @return Return all solutions of the current criteria query
	 * @since 1.0
	 */
	public List<O> getSolutions();

	/**
	 * Create a {@code TypedQuery} of root criteria class type that include the
	 * restrictions appended to the current criteria object.
	 * 
	 * @return {@code TypedQuery} of root criteria class type.
	 * @since 1.0
	 */
	public TypedQuery<O> createQuery();

	/**
	 * Trace the low level prolog query execute by the prolog engine.
	 * 
	 * @return current instance of {@code CriteriaQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ConstraintQuery<O> trace();

	/**
	 * Release all allocations and references for the current object
	 */
	public void dispose();

}
