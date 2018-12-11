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

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public final class JpaIn<X> extends JpaPredicate implements In<X> {

	public JpaIn(String alias, Class<? extends Boolean> javaType, Expression<?> expression, Metamodel metamodel,
			BooleanOperator operator, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, operator, expressions);
	}

	public BooleanOperator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNegated() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Expression<Boolean>> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate not() {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<X> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(X value) {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(Expression<? extends X> value) {
		// TODO Auto-generated method stub
		return null;
	}

}
