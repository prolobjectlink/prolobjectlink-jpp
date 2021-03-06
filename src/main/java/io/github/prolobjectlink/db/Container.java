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

import java.util.Collection;
import java.util.List;

import javax.naming.spi.ObjectFactory;

import io.github.prolobjectlink.Wrapper;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

/**
 * Main concept for object contention mechanism. Are derived classes from this
 * concept the classes {@link VolatileContainer} and {@link PersistentContainer}
 * 
 * @author Jose Zalacain
 * @see VolatileContainer
 * @see PersistentContainer
 * @since 1.0
 */
public interface Container extends Wrapper {

	public List<Object> solutionsOf(PrologTerm[] prologTerms, List<Class<?>> classes);

	public Object[] solutionOf(PrologTerm[] prologTerms, List<Class<?>> classes);

	/**
	 * Check the existence of some give string based term with prolog syntax in this
	 * {@link Container}. The string based term will be parsed and the internal term
	 * representation query to the prolog engine. The contention concept is base on
	 * the prolog clause/2 built-in on instance prolog engine. The clause/2 check
	 * that exist some clause in the prolog program or database which unifies your
	 * head and your body. The clause/2 is based on terms unification and make more
	 * flexible the contention concept. If clause/2 success then in the prolog
	 * program/database some define clause match with converted object and can be
	 * resolved. If clause/2 fail then in the prolog program/database no one clause
	 * exist that resolve the converted object.
	 * 
	 * <pre>
	 * objectContainer.contains(&quot;'io.github.prolobjectlink.domain.geometry.Point'(Idp, X, Y)&quot;);
	 * objectContainer.contains(&quot;'io.github.prolobjectlink.domain.geometry.Point'( a, 3,14 )&quot;);
	 * </pre>
	 * 
	 * Formulated Prolog Query
	 * 
	 * <pre>
	 * ?- clause('io.github.prolobjectlink.domain.geometry.Point'( Idp, X, Y ),true).
	 * ?- clause('io.github.prolobjectlink.domain.geometry.Point'( a, 3,14 ),true).
	 * </pre>
	 * 
	 * @param string string based prolog term.
	 * @return true if string based term is defined in this {@link Container}, false
	 *         otherwise.
	 * @see Container#contains(Object)
	 * @see Container#contains(Class)
	 * @see Container#contains(Predicate)
	 * @see Container#contains(String, int)
	 * @since 1.0
	 */
	public boolean contains(String string);

	/**
	 * Check the existence of some give object in this {@link Container}. The object
	 * will be converted with {@link ObjectFactory} to equivalent prolog term and
	 * query to the prolog engine. The contention concept is base on the prolog
	 * clause/2 built-in on instance prolog engine. The clause/2 check that exist
	 * some clause in the prolog program or database which unifies your head and
	 * your body. The clause/2 is based on terms unification and make more flexible
	 * the contention concept. If clause/2 success then in the prolog
	 * program/database some define clause match with converted object and can be
	 * resolved. If clause/2 fail then in the prolog program/database no one clause
	 * exist that resolve the converted object.
	 * 
	 * <pre>
	 * objectContainer.contains(new Point(&quot;a&quot;, 3, 14));
	 * </pre>
	 * 
	 * 
	 * Formulated Prolog Query
	 * 
	 * <pre>
	 * ?- clause('io.github.prolobjectlink.domain.geometry.Point'( a, 3,14 ),true).
	 * </pre>
	 * 
	 * @param o object to be converted to equivalent prolog term and querying to the
	 *          prolog engine.
	 * @param   <O> type to be converted to equivalent prolog term and querying to
	 *          the prolog engine.
	 * @return true if the converted object to prolog term is defined in this
	 *         {@link Container}, false otherwise.
	 * @see Container#contains(String)
	 * @see Container#contains(Class)
	 * @see Container#contains(Predicate)
	 * @see Container#contains(String, int)
	 * @since 1.0
	 */
	public <O> boolean contains(O o);

	/**
	 * Check the existence of some give class in this {@link Container}. The class
	 * will be converted with {@link ObjectConverter} to equivalent prolog term and
	 * query to the prolog engine. The equivalent prolog term is the most general
	 * predicate that represent a relation in prolog context. The contention concept
	 * is base on the prolog clause/2 built-in on instance prolog engine. The
	 * clause/2 check that exist some clause in the prolog program or database which
	 * unifies your head and your body. The clause/2 is based on terms unification
	 * and make more flexible the contention concept. If clause/2 success then in
	 * the prolog program/database some define clause match with converted object
	 * and can be resolved. If clause/2 fail then in the prolog program/database no
	 * one clause exist that resolve the converted object.
	 * 
	 * <pre>
	 * objectContainer.contains(Point.class);
	 * </pre>
	 * 
	 * Formulated Prolog Query
	 * 
	 * <pre>
	 * ?- clause('io.github.prolobjectlink.domain.geometry.Point'( Idp, X, Y ),true).
	 * </pre>
	 * 
	 * @param clazz class to be converted to equivalent prolog term and querying to
	 *              the prolog engine.
	 * @param       <O> type to be converted to equivalent prolog term and querying
	 *              to the prolog engine.
	 * @return true if the converted class to prolog term is defined in this
	 *         {@link Container}, false otherwise.
	 * @see Container#contains(String)
	 * @see Container#contains(Object)
	 * @see Container#contains(Predicate)
	 * @see Container#contains(String, int)
	 * @since 1.0
	 */
	public <O> boolean contains(Class<O> clazz);

	public <O> boolean contains(Predicate<O> predicate);

	/**
	 * Check the existence of prolog predicates in this {@link Container} that have
	 * predicate indicator created from the given functor and arity. The contention
	 * concept is base on the prolog current_predicate/1 built-in on instanced
	 * prolog engine. The current_predicate/1 check that exist some predicate in the
	 * prolog program/database or engine defined built-ins with equals functor and
	 * arity. If current_predicate/1 success then in the prolog program/database or
	 * engine defined built-ins some define predicate match with the given functor
	 * and arity and can be resolved. If current_predicate/1 fail then in the prolog
	 * program/database or engine defined built-ins no one predicate exist.
	 * 
	 * @param functor predicate functor
	 * @param arity   predicate arity
	 * @return true if in the program/database or engine built-ins held by this
	 *         {@link Container} is defined at least one predicate with predicate
	 *         indicator created from the given functor and arity , false otherwise.
	 * @see Container#contains(Class)
	 * @see Container#contains(String)
	 * @see Container#contains(Object)
	 * @see Container#contains(Predicate)
	 * @since 1.0
	 */
	public boolean contains(String functor, int arity);

	public ContainerFactory getContainerFactory();

	public ObjectConverter<PrologTerm> getConverter();

	public Settings getProperties();

	public PrologProvider getProvider();

	public PrologEngine getEngine();

	/**
	 * Return a list with predicate classes present in prolog terms array
	 * 
	 * @param prologTerms prolog terms array
	 * @return list with predicate classes present in prolog terms array
	 * @since 1.0
	 */
	public List<Class<?>> classesOf(PrologTerm[] prologTerms);

	public List<Class<?>> classesOf(String string);

	/**
	 * List all classes in this container
	 * 
	 * @return classes in this container
	 * @since 1.0
	 */
	public Collection<Class<?>> classes();

	public <O> Class<O> classOf(Predicate<O> predicate);

	/**
	 * Allow known the class with functor/arity signature
	 * 
	 * @param functor functor class name
	 * @param arity   arity (fields number)
	 * @return class class that match with functor/arity signature
	 * @since 1.0
	 */
	public Class<?> classOf(String functor, int arity);

	/**
	 * Allow known the class of some given object
	 * 
	 * @param o object to known your class
	 * @param   <O> type of the object to known your class
	 * @return class of object {@code o}
	 * @since 1.0
	 */
	public <O> Class<O> classOf(O o);

}
