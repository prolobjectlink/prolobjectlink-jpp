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
package org.prolobjectlink.db;

import java.io.Serializable;

import org.prolobjectlink.db.predicate.AndPredicate;
import org.prolobjectlink.db.predicate.EqualPredicate;
import org.prolobjectlink.db.predicate.FalsePredicate;
import org.prolobjectlink.db.predicate.IdentityPredicate;
import org.prolobjectlink.db.predicate.InstanceOfPredicate;
import org.prolobjectlink.db.predicate.NotNullPredicate;
import org.prolobjectlink.db.predicate.NotPredicate;
import org.prolobjectlink.db.predicate.NullPredicate;
import org.prolobjectlink.db.predicate.OrPredicate;
import org.prolobjectlink.db.predicate.TruePredicate;

/**
 * <p>
 * Main class for support the Native Query implementation. Natives Queries are
 * the standard query interface in object oriented data base systems. This query
 * type is very useful because is over all things a type safe way to retrieve
 * objects. They are implemented with syntax of programming languages by methods
 * calling at compile-time.
 * </p>
 * 
 * <p>
 * The {@link Predicate} implementation is used by {@link Storage},
 * {@link TypedQuery} and {@link Query}. They go selecting from an object list
 * retrieve previously there objects that match with the condition implemented
 * in the {@link Predicate#evaluate(Object)} method.
 * </p>
 * 
 * <p>
 * Sample using {@link Predicate} for find all points with <tt>X == 3.5</tt> and
 * <tt>Y == 10.14</tt> from {@link Storage}.
 * </p>
 * 
 * <pre>
 * List&lt;Point&gt; points = objectContainer.findAll(new Predicate&lt;Point&gt;() {
 * 
 * 	public boolean evaluate(Point point) {
 * 		return (point.getX() == 3.5) &amp;&amp; (point.getY() == 10.14);
 * 	}
 * 
 * });
 * </pre>
 * 
 * 
 * @param <O> Object template to be evaluate by {@link Predicate}
 * @author Jose Zalacain
 * @since 1.0
 * @see AndPredicate
 * @see EqualPredicate
 * @see FalsePredicate
 * @see IdentityPredicate
 * @see InstanceOfPredicate
 * @see NotNullPredicate
 * @see NotPredicate
 * @see NullPredicate
 * @see OrPredicate
 * @see TruePredicate
 */
public interface Predicate<O> extends Serializable {

	/**
	 * Return true if some object match with the current {@link Predicate}. If the
	 * object match, will be include in the solution list.
	 * 
	 * @param o possible candidate to be match and include in the solution list.
	 * @return true if the object match and will be include in the solution list.
	 * @since 1.0
	 */
	public boolean evaluate(final O o);

}
