/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.database.jpa.criteria;

import java.util.List;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaAndPredicate;

public class JpaGroupBy<T> extends JpaAbstractQuery<T> implements AbstractQuery<T> {

	public JpaGroupBy(Expression<?>[] grouping, Metamodel metamodel, Class<T> resultType) {
		super(null, metamodel, resultType);
		for (Expression<?> predicate : grouping) {
			restriction = new JpaAndPredicate(null, Boolean.class, null, metamodel, newList(restriction, predicate));
		}
	}

	public JpaGroupBy(List<Expression<?>> grouping, Metamodel metamodel, Class<T> resultType) {
		super(null, metamodel, resultType);
		for (Expression<?> predicate : grouping) {
			restriction = new JpaAndPredicate(null, Boolean.class, null, metamodel, newList(restriction, predicate));
		}
	}

}
