/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db;

import org.worklogic.db.predicate.AndPredicate;
import org.worklogic.db.predicate.EqualPredicate;
import org.worklogic.db.predicate.FalsePredicate;
import org.worklogic.db.predicate.IdentityPredicate;
import org.worklogic.db.predicate.InstanceOfPredicate;
import org.worklogic.db.predicate.NotNullPredicate;
import org.worklogic.db.predicate.NotPredicate;
import org.worklogic.db.predicate.NullPredicate;
import org.worklogic.db.predicate.OrPredicate;
import org.worklogic.db.predicate.TruePredicate;

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
