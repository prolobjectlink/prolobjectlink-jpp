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
package org.worklogic.db.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.worklogic.db.util.JavaReflect;

public class JpaCoalecse<X> extends JpaExpression<X> implements Coalesce<X> {

	protected final Expression<?> right;

	public JpaCoalecse(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.right = right;
	}

	public JpaCoalecse(String alias, Class<? extends X> javaType, Metamodel metamodel) {
		this(alias, javaType, null, null, metamodel);
	}

	public Coalesce<X> value(X value) {
		expression = new JpaObject<X>(value, JavaReflect.classOf(value));
		return this;
	}

	public Coalesce<X> value(Expression<? extends X> value) {
		expression = value;
		return this;
	}

	@Override
	public String toString() {
		return "JpaCoalecse [right=" + right + ", metamodel=" + metamodel + ", distinct=" + distinct + ", expression="
				+ expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		JpaCoalecse<?> other = (JpaCoalecse<?>) obj;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}
