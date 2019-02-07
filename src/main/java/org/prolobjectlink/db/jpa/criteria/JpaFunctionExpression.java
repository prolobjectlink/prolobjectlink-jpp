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
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaFunctionExpression<X> extends JpaExpression<X> implements Expression<X> {

	protected final String operator;
	protected final Expression<?> right;

	public JpaFunctionExpression(String alias, Class<? extends X> javaType, Expression<?> left, String operator,
			Expression<?> right, Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.operator = operator;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		JpaFunctionExpression<X> other = (JpaFunctionExpression<X>) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}
