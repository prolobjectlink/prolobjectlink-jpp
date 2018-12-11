/*
 * #%L
 * prolobjectlink-jpp
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

import java.io.Serializable;

import org.logicware.database.predicate.AndPredicate;
import org.logicware.database.predicate.EqualPredicate;
import org.logicware.database.predicate.FalsePredicate;
import org.logicware.database.predicate.IdentityPredicate;
import org.logicware.database.predicate.InstanceOfPredicate;
import org.logicware.database.predicate.NotNullPredicate;
import org.logicware.database.predicate.NotPredicate;
import org.logicware.database.predicate.NullPredicate;
import org.logicware.database.predicate.OrPredicate;
import org.logicware.database.predicate.TruePredicate;

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
 * 
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
