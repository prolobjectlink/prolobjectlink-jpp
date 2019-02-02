/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.prolobjectlink.db.jpa.criteria.JpaPredicate;

public class JpaInPredicate<X> extends JpaPredicate implements In<X> {

	private final Expression<X> leftExpression;

	public JpaInPredicate(String alias, Class<? extends Boolean> javaType, Expression<?> expression,
			Metamodel metamodel, BooleanOperator operator, List<Expression<?>> expressions,
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

	@Override
	public String toString() {
		return "JpaInPredicate [leftExpression=" + leftExpression + ", expressions=" + expressions + ", operator="
				+ operator + ", metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression
				+ ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((leftExpression == null) ? 0 : leftExpression.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaInPredicate<?> other = (JpaInPredicate<?>) obj;
		if (leftExpression == null) {
			if (other.leftExpression != null)
				return false;
		} else if (!leftExpression.equals(other.leftExpression)) {
			return false;
		}
		return true;
	}

}
