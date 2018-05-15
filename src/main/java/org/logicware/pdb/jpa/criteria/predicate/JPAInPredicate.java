/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.pdb.jpa.criteria.JPAPredicate;

public class JPAInPredicate<X> extends JPAPredicate implements In<X> {

	private final Expression<X> leftExpression;

	public JPAInPredicate(String alias, Class<? extends Boolean> javaType, Expression<Boolean> expression,
			Metamodel metamodel, BooleanOperator operator, List<Expression<Boolean>> expressions,
			Expression<X> leftExpression) {
		super(alias, javaType, expression, metamodel, operator, expressions);
		this.leftExpression = leftExpression;
	}

	public Expression<X> getExpression() {
		return leftExpression;
	}

	public In<X> value(X value) {
		expressions.add(value);
		return this;
	}

	public In<X> value(Expression<? extends X> value) {
		expressions.add(value);
		return this;
	}

}
