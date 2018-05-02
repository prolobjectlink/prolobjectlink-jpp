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
package org.logicware.jpa.criteria;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public class JPAPredicate extends JPAExpression<Boolean> implements Predicate {

	protected final List expressions;
	protected final BooleanOperator operator;

	public JPAPredicate(String alias, Class<? extends Boolean> javaType, Expression<Boolean> expression,
			Metamodel metamodel, BooleanOperator operator, List<Expression<Boolean>> expressions) {
		super(alias, javaType, expression, metamodel);
		this.operator = operator;
		this.expressions = expressions;
	}

	public BooleanOperator getOperator() {
		return operator;
	}

	public boolean isNegated() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Expression<Boolean>> getExpressions() {
		return expressions;
	}

	public Predicate not() {
		// TODO Auto-generated method stub
		return null;
	}

}
