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

import org.logicware.jpp.Wrapper;

public interface PrologTerm extends Wrapper, Comparable<PrologTerm>, Cloneable {

    // constant for prolog variables
    public static final int VARIABLE_TYPE = 0x100;

    // constants for prolog numbers
    public static final int INTEGER_TYPE = 0x200;
    public static final int LONG_TYPE = 0x201;
    public static final int FLOAT_TYPE = 0x202;
    public static final int DOUBLE_TYPE = 0x203;

    // constant for prolog atoms
    public static final int ATOM_TYPE = 0x300;
    public static final int NIL_TYPE = 0x301;
    public static final int FALSE_TYPE = 0x302;
    public static final int TRUE_TYPE = 0x303;
    public static final int CUT_TYPE = 0x304;
    public static final int FAIL_TYPE = 0x305;

    // constant for special objects
    public static final int OBJECT_TYPE = 0x400;
    public static final int STREAM_TYPE = 0x401;

    // constants for prolog list and prolog structure
    public static final int EMPTY_TYPE = 0x500;
    public static final int LIST_TYPE = 0x501;
    public static final int STRUCTURE_TYPE = 0x502;

    @Deprecated
    public static final int EXPRESSION_TYPE = 0x503;

    /**
     * Gets the term indicator
     * 
     * @return term indicator
     * @since 1.0
     */
    public String getIndicator();

    public boolean hasIndicator(String functor, int arity);

    /**
     * Get the term type.
     * 
     * @return term type.
     * @since 1.0
     */
    public int getType();

    /**
     * True if this term is an atom
     * 
     * @return whether this Term represents an atom, false in other case
     * @since 1.0
     */
    public boolean isAtom();

    /**
     * True if this term is an number
     * 
     * @return whether this Term represents an number, false in other case
     * @since 1.0
     */
    public boolean isNumber();

    /**
     * True if this Term is a single precision floating point number, false in
     * other case
     * 
     * @return whether this Term represents a single precision floating point
     *         number, false in other case
     * @since 1.0
     */
    public boolean isFloat();

    /**
     * True if this Term is an integer number, false in other case
     * 
     * @return whether this Term represents an integer number
     * @since 1.0
     */
    public boolean isInteger();

    /**
     * True if this Term is a double precision floating point number, false in
     * other case
     * 
     * @return whether this Term represents a double precision floating point
     *         number, false in other case
     * @since 1.0
     */
    public boolean isDouble();

    /**
     * True if this Term is a large integer number, false in other case
     * 
     * @return whether this Term represents a large integer number, false in
     *         other case
     * @since 1.0
     */
    public boolean isLong();

    /**
     * True if this Term is a variable, false in other case
     * 
     * @return whether this Term is a variable
     * @since 1.0
     */
    public boolean isVariable();

    /**
     * True if this Term is a list, false in other case
     * 
     * @return whether this Term is a list
     * @since 1.0
     */
    public boolean isList();

    /**
     * True if this Term is a structured term, false in other case
     * 
     * @return whether this Term is a structured term
     * @since 1.0
     */
    public boolean isStructure();

    /**
     * True if this Term is a nil term (null term for prolog), false in other
     * case
     * 
     * @return whether this Term is a nil term
     * @since 1.0
     */
    public boolean isNil();

    /**
     * True if this Term is a empty list term ([]), false in other case
     * 
     * @return whether this Term is a empty list term
     * @since 1.0
     */
    public boolean isEmptyList();

    /**
     * True if this Term is a atomic term, false in other case
     * 
     * @return whether this Term is a atomic term
     * @since 1.0
     */
    public boolean isAtomic();

    /**
     * True if this Term is a compound term, false in other case
     * 
     * @return whether this Term is a compound term
     * @since 1.0
     */
    public boolean isCompound();

    /**
     * Check if the current term is a compound term and have like functor an
     * operator.
     * 
     * @return true if current term have like functor an operator.
     * @since 1.0
     */
    public boolean isEvaluable();

    public int getArity();

    public String getFunctor();

    public PrologTerm[] getArguments();

    /**
     * Check that two terms (x and y) unify. Prolog unification algorithm is
     * based on three principals rules:
     * <ul>
     * <li>If x and y are atomics constants then x and y unify only if they are
     * same object.</li>
     * <li>If x is a variable and y is anything then they unify and x is
     * instantiated to y. Conversely, if y is a variable then this is
     * instantiated to x.</li>
     * <li>If x and y are structured terms then unify only if they match (equals
     * funtor and arity) and all their correspondents arguments unify.</li>
     * </ul>
     * 
     * 
     * @param term
     *            the term to unify with the current term
     * @return true if the specified term unify whit the current term, false if
     *         not
     * @since 1.0
     */
    public boolean unify(PrologTerm term);

    // public <K> K unwrap(Class<K> cls);

    public int hashCode();

    public boolean equals(Object obj);

    public String toString();

    public PrologTerm clone();

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

    public PrologProvider getProvider();

}
