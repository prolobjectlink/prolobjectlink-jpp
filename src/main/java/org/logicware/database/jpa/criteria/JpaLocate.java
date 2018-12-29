/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaLocate<X> extends JpaFunctionExpression<X> implements Expression<X> {

	protected final Expression<?> length;

	public JpaLocate(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from,
			Metamodel metamodel) {
		this(alias, javaType, x, from, null, metamodel);
	}

	public JpaLocate(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from, Expression<?> len,
			Metamodel metamodel) {
		super(alias, javaType, x, "SUBSTRING", from, metamodel);
		this.length = len;
	}

	@Override
	public String toString() {
		return "JpaLocate [length=" + length + ", operator=" + operator + ", right=" + right + ", metamodel="
				+ metamodel + ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias="
				+ alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((length == null) ? 0 : length.hashCode());
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
		JpaLocate<?> other = (JpaLocate<?>) obj;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length)) {
			return false;
		}
		return true;
	}

}
