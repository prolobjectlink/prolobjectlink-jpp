/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.jpa.criteria;

import java.util.Collection;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaNotNullPredicate;
import org.logicware.database.jpa.criteria.predicate.JpaNullPredicate;

public abstract class JpaExpression<X> extends JpaSelection<X> implements Expression<X> {

	protected final Metamodel metamodel;

	public JpaExpression(String alias, Class<? extends X> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression);
		this.metamodel = metamodel;
	}

	public Predicate isNull() {
		return new JpaNullPredicate(null, Boolean.class, expression, metamodel, null, null);
	}

	public Predicate isNotNull() {
		return new JpaNotNullPredicate(null, Boolean.class, expression, metamodel, null, null);
	}

	public Predicate in(Object... values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Expression<?>... values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Collection<?> values) {
		return new JpaIn(null, Boolean.class, null, metamodel, null, newList(values));
	}

	public Predicate in(Expression<Collection<?>> values) {
		return new JpaIn(null, Boolean.class, values, metamodel, null, newList());
	}

	public <Y> Expression<Y> as(Class<Y> type) {
		return new JpaAs<Y>(null, type, null, metamodel);
	}

	@Override
	public String toString() {
		return "JpaExpression [expression=" + expression + ", alias=" + alias + ", value=" + value + ", javaType="
				+ javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((metamodel == null) ? 0 : metamodel.hashCode());
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
		JpaExpression<?> other = (JpaExpression<?>) obj;
		if (metamodel == null) {
			if (other.metamodel != null)
				return false;
		} else if (!metamodel.equals(other.metamodel)) {
			return false;
		}
		return true;
	}

}
