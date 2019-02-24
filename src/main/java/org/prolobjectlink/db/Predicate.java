/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
