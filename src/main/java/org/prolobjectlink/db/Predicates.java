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

public final class Predicates {

	private Predicates() {
	}

	public static <O> Predicate<O> andPredicate(Predicate<O> leftPredicate, Predicate<O> rigthPredicate) {
		return new AndPredicate<O>(leftPredicate, rigthPredicate);
	}

	public static <O> Predicate<O> orPredicate(Predicate<O> leftPredicate, Predicate<O> rigthPredicate) {
		return new OrPredicate<O>(leftPredicate, rigthPredicate);
	}

	public static <O> Predicate<O> notPredicate(Predicate<O> predicate) {
		return new NotPredicate<O>(predicate);
	}

	public static <O> Predicate<O> equalPredicate(Object value) {
		return new EqualPredicate<O>(value);
	}

	public static <O> Predicate<O> identityPredicate(Object value) {
		return new IdentityPredicate<O>(value);
	}

	public static <O> Predicate<O> instanceOfPredicate(Class<?> clazz) {
		return new InstanceOfPredicate<O>(clazz);
	}

	public static <O> Predicate<O> falsePredicate() {
		return new FalsePredicate<O>();
	}

	public static <O> Predicate<O> truePredicate() {
		return new TruePredicate<O>();
	}

	public static <O> Predicate<O> notNullPredicate() {
		return new NotNullPredicate<O>();
	}

	public static <O> Predicate<O> nullPredicate() {
		return new NullPredicate<O>();
	}

}
