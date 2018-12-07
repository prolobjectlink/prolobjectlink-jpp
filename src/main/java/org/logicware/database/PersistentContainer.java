/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.database;

import java.io.Closeable;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Container
 * @see VolatileContainer
 */
public interface PersistentContainer extends Closeable, Restorable, Container, Transactional, Defragtable {

	/**
	 * Open all file resources associated to this {@link PersistentContainer} and
	 * set ready to operate.
	 * 
	 * @since 1.0
	 *
	 * @deprecated use {@code #getTransaction()#begin()}
	 */
	@Deprecated
	public void open();

	/**
	 * Bulk insertion for non null and non empty objects array
	 * 
	 * @param objects objects arrays
	 */
	public <O> void insert(O... facts);

	public <O> void update(O match, O update);

	public void delete(Class<?> clazz);

	/**
	 * bulk deletion for non null objects array
	 * 
	 * @param         <O>
	 * @param objects
	 */
	public <O> void delete(O... facts);

	public Transaction getTransaction();

	/**
	 * <p>
	 * Query builded from a string with standard prolog syntax. The string query is
	 * translated to prolog terms and passed to the prolog engine for to be
	 * resolved. The predicate classes present in the prolog string will be store
	 * for resolve and return instances of those classes as result of querying and
	 * build predicates classes from result obtained of the prolog engine.
	 * 
	 * <pre>
	 * query = pm.createQuery(&quot;point(Idp, X, Y)&quot;);
	 * query = pm.createQuery(&quot;segment(Ids, Point0, Point1)&quot;);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * The string query allow the arithmetic comparison and term comparison but this
	 * operators will not be resolve to predicate classes. Only will be resolve
	 * predicate class that was specified in the configuration.
	 * 
	 * <pre>
	 * query = pm.createQuery(&quot;point(Idp, X, Y), X =:= 3.5, Y =:= 10.14&quot;);
	 * query = pm.createQuery(
	 * 		&quot;segment(Ids, Point0, Point1), Point0 == point(a, 3.5, 10.14), Point1 == point(b, 3.5, 10.14)&quot;);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * This query variant support the projection that is the presence of various
	 * predicate classes in the prolog string query.
	 * 
	 * <pre>
	 * query = pm.createQuery(&quot;point(Idp, X, Y), segment(Ids, Point0, Point1)&quot;);
	 * </pre>
	 * 
	 * </p>
	 * 
	 * <p>
	 * Waring with equals variables in different class. This cause that in the
	 * translated prolog query this variables can be instantiated with a value that
	 * not is the same expected value for different predicate classes or not return
	 * the expected query result.
	 * </p>
	 * 
	 * @param string string with standard prolog syntax.
	 * @return Query builded from a string with standard prolog syntax.
	 * @since 1.0
	 * @see PredicateManager#createQuery(S)
	 * @see PredicateManager#createQuery(Class)
	 */
	public Query createQuery(String string);

	/**
	 * <p>
	 * Query by Example implementation. This query is builded from a template Object
	 * object obtaining from this term the most general prolog Object term using
	 * reflection and and passed to the predicate context for to be resolved. In
	 * most general Object (or predicate) resolution the values of the fields are
	 * conserved and fields with null values are substituting by the variable name
	 * specified on field argument annotations. This process is named inspection.
	 * The inspection process find three variants for resolve the term to query.
	 * 
	 * </p>
	 * <p>
	 * 
	 * <li>If the Object term are fully instantiated only one solution is possible
	 * and will be a new Object term obtained from the predicate context that is
	 * equals to the template object.</li>
	 * 
	 * <pre>
	 * query = pm.createQuery(new Point(new Atom(&quot;a&quot;), new Float(3.5), new Float(10.14)));
	 * </pre>
	 * 
	 * Prolog Generated Query:
	 * 
	 * <pre>
	 * ?-point(a, 3.5, 10.14).
	 * </pre>
	 * 
	 * <li>If the Object term are fully empty (all field are null values) the query
	 * will be formulated from the most general Object term using as prolog
	 * variables names the names specified on fields arguments annotations. The
	 * solution for this query variant will be builded for all Object that match
	 * with the most general relation obtained</li>
	 * 
	 * <pre>
	 * query = pm.createQuery(new Point());
	 * query = pm.createQuery(new Segment());
	 * </pre>
	 * 
	 * Prolog Generated Query:
	 * 
	 * <pre>
	 * ?-point(Ipd, X, Y).
	 * ?-segment(Ips, Point0, Point1).
	 * </pre>
	 * 
	 * <li>If the Object term are partially instantiated/empty the query will be
	 * formulated as a combination of the two variants before mentioned. The
	 * instantiate fields preserve your values and null fields values will be
	 * replaced by your corresponded variable name.</li>
	 * 
	 * <pre>
	 * 
	 * query = pm.createQuery(new Point(new Float(3.5), new Float(10.14)));
	 * </pre>
	 * 
	 * Prolog Generated Query:
	 * 
	 * <pre>
	 * ?-point(Ipd, 3.5, 10.14).
	 * </pre>
	 * 
	 * </p>
	 * 
	 * @param o object template
	 * @return TypedQuery builded from a object template.
	 * @since 1.0
	 * @see Storage#createQuery(Class)
	 * @see Storage#createQuery(String)
	 */
	public <O> TypedQuery<O> createQuery(O o);

	/**
	 * Query builded from a Object class. The most general prolog Object term is
	 * obtaining and resolved. In most general Object (or predicate) resolution only
	 * terms used for build the query are the variables names specified on field
	 * argument annotations. In other words create a query from a Object class is
	 * always equivalent to query with the most general predicate. This query way
	 * will have the same result of the Query by example with an object template
	 * with fully empty. The solution for this query variant will be builded for all
	 * Object that match with the most general relation obtained
	 * 
	 * <pre>
	 * query = pm.createQuery(Point.class);
	 * query = pm.createQuery(Segment.class);
	 * </pre>
	 * 
	 * Prolog Generated Query:
	 * 
	 * <pre>
	 * ?-point(Ipd, X, Y).
	 * ?-segment(Ips, Point0, Point1).
	 * </pre>
	 * 
	 * @param clazz class for build the query
	 * @return TypedQuery builded from a Object class
	 * @since 1.0
	 * @see Storage#createQuery(O)
	 * @see Storage#createQuery(String)
	 */
	public <O> TypedQuery<O> createQuery(Class<O> clazz);

	public <O> TypedQuery<O> createQuery(Predicate<O> predicate);

	public ProcedureQuery createProcedureQuery(String functor, String... args);

	public PersistentContainer containerOf(Class<?> clazz);

	public String locationOf(Class<?> clazz);

	public ContainerFactory getContainerFactory();

	/**
	 * Include to the held engine the code from given prolog source file located at
	 * string path
	 * 
	 * @param path prolog source file location
	 * @since 1.0
	 */
	public void include(String path);

	/**
	 * Return an string that represent the current location of this
	 * {@link PersistentContainer}
	 * 
	 * @return string location of this {@link PersistentContainer}
	 * @since 1.0
	 */
	public String getLocation();

	/**
	 * Check if the current {@link PersistentContainer} instance is ready for
	 * operate
	 * 
	 * @return true if {@link PersistentContainer} instance is ready and false in
	 *         otherwise.
	 * @since 1.0
	 * @deprecated use {@code transaction is active}
	 */
	@Deprecated
	public boolean isOpen();

	/**
	 * @deprecated use {@code #getTransaction()#commit()}
	 */
	@Deprecated
	public void flush();

	/**
	 * Clear all memory data
	 * 
	 * @since 1.0
	 */
	public void clear();

}
