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
package org.logicware.jpi;

import java.util.Map;

public interface PrologProvider {

    /**
     * True if under-laying engine implement ISO Prolog and false in other case
     * 
     * @return true if under-laying engine implement ISO Prolog and false in
     *         other case
     * @since 1.0
     */
    public boolean isCompliant();

    /**
     * True if under-laying engine save complex functors using quotes e.g.
     * 'Foo.Bar'(baz) in internal clause database. False if under-laying prolog
     * engine remove quotes for save complex functors e.g. Foo.Bar(baz). If
     * {@link #preserveQuotes()} return false the query 'Foo.Bar'(X) fail raise
     * an existence prolog error for 'Foo.Bar'/1 because the under-laying engine
     * remove quotes and no resolve the existing predicate is Foo.Bar(baz) with
     * indicator Foo.Bar/1.
     * 
     * @return True if under-laying engine save complex functors using quotes,
     *         false if remove quotes for save complex functors.
     * @since 1.0
     * @deprecated
     */
    public boolean preserveQuotes();

    // prolog constants term

    public PrologTerm prologNil();

    public PrologTerm prologCut();

    public PrologTerm prologFail();

    public PrologTerm prologTrue();

    public PrologTerm prologFalse();

    public PrologTerm prologEmpty();

    // term parser helpers

    public PrologTerm parsePrologTerm(String term);

    public PrologTerm[] parsePrologTerms(String stringTerms);

    public PrologList parsePrologList(String stringList);

    public PrologStructure parsePrologStructure(String stringStructure);

    @Deprecated
    public PrologExpression parsePrologExpression(String stringExpression);

    // engine

    public PrologEngine newEngine();

    // prolog term instantiation

    public PrologAtom newAtom(String functor);

    public PrologFloat newFloat();

    public PrologFloat newFloat(Number value);

    /**
     * Create a prolog double number instance.
     * 
     * @return prolog double number
     * @since 1.0
     * @deprecated Use {@link PrologFloat} because all prolog implementation use
     *             floating point number but not all use double precision number
     */
    public PrologDouble newDouble();

    /**
     * Create a prolog double number instance.
     * 
     * @param value
     *            numeric value
     * 
     * @return prolog double number
     * @since 1.0
     */
    public PrologDouble newDouble(Number value);

    public PrologInteger newInteger();

    public PrologInteger newInteger(Number value);

    public PrologLong newLong();

    public PrologLong newLong(Number value);

    /**
     * Create an anonymous variable instance.
     * 
     * @return An anonymous variable instance.
     * @throws UnsupportedOperationException
     *             if the under-laying prolog provider use an anonymous
     *             instantiation with associated index {@link #newVariable(int)}
     * @since 1.0
     * @see PrologProvider#newVariable(int)
     * @see PrologProvider#newVariable(String)
     * @see PrologProvider#newVariable(String, int)
     * @deprecated use {@link PrologProvider#newVariable(int)}
     */
    public PrologVariable newVariable();

    /**
     * Create a named variable instance.
     * 
     * @param name
     *            variable name (upper case beginning)
     * @return A named variable instance.
     * @throws UnsupportedOperationException
     *             if the under-laying prolog provider use an anonymous
     *             instantiation with associated index
     *             {@link PrologProvider#newVariable(String, int)}
     * @since 1.0
     * @see PrologProvider#newVariable()
     * @see PrologProvider#newVariable(int)
     * @see PrologProvider#newVariable(String, int)
     * @deprecated use {@link PrologProvider#newVariable(String, int)}
     */
    public PrologVariable newVariable(String name);

    /**
     * Create an anonymous variable instance with associated index. Index is a
     * non negative integer that represent the variable position of the
     * Structure where the variable is first time declared. If the under-laying
     * prolog provider no use specific index, have the same effect of
     * {@link PrologProvider#newVariable()}
     * 
     * @param position
     *            Position of its Structure where the variable is first time
     *            declared.
     * @return An anonymous variable instance with associated index.
     * @throws IllegalArgumentException
     *             if position is a negative number
     * @since 1.0
     * @see PrologProvider#newVariable()
     * @see PrologProvider#newVariable(String)
     * @see PrologProvider#newVariable(String, int)
     */
    public PrologVariable newVariable(int position);

    /**
     * Create an named variable instance with associated index. Index is a non
     * negative integer that represent the variable position of the Structure
     * where the variable is first time declared. If the under-laying prolog
     * provider no use specific index, have the same effect of
     * {@link PrologProvider#newVariable(String)}
     * 
     * @param name
     *            variable name (upper case beginning)
     * 
     * @param position
     *            Position of its Structure where the variable is first time
     *            declared.
     * @return A named variable instance with associated index.
     * @throws IllegalArgumentException
     *             if position is a negative number
     * @since 1.0
     * @see PrologProvider#newVariable()
     * @see PrologProvider#newVariable(int)
     * @see PrologProvider#newVariable(String)
     */
    public PrologVariable newVariable(String name, int position);

    public PrologList newList();

    public PrologList newList(PrologTerm[] arguments);

    public PrologList newList(PrologTerm head, PrologTerm tail);

    public PrologList newList(PrologTerm[] arguments, PrologTerm tail);

    public PrologStructure newStructure(String functor, PrologTerm... arguments);

    @Deprecated
    public PrologExpression newExpression(PrologTerm left, String operator, PrologTerm right);

    public <K extends PrologTerm, V extends Object> Map<String, PrologTerm>[] toTermMapArray(Map<String, V>[] map,
	    Class<K> from);

    public <K extends PrologTerm, V extends Object> Map<String, PrologTerm> toTermMap(Map<String, V> map,
	    Class<K> from);

    public <K extends PrologTerm> K[][] toTermTable(Object[][] oss, Class<K[][]> from);

    public <K extends PrologTerm> K[] toTermArray(Object[] os, Class<K[]> from);

    public <K extends PrologTerm> K toTerm(Object o, Class<K> from);

    public <K> K fromTerm(PrologTerm term, Class<K> to);

    public <K> K[] fromTermArray(PrologTerm[] terms, Class<K[]> to);

    public <K> K fromTerm(PrologTerm head, PrologTerm[] body, Class<K> to);

    public PrologConverter<?> getConverter();

    public int hashCode();

    public boolean equals(Object obj);

    public String toString();

}
