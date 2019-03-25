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
