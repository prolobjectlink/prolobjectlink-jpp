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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Metamodel;

public abstract class JpaAbstractQuery<T> extends JpaAbstractCriteria<T> implements AbstractQuery<T> {

	protected boolean distinct;
	protected final Set<Root<?>> roots;
	protected Expression<Boolean> havingClause;
	protected List<Expression<?>> groupBy = newList();

	@Deprecated
	public JpaAbstractQuery(Expression<Boolean> restriction, Metamodel metamodel, Class<T> resultType) {
		this(restriction, metamodel, false, resultType, new HashSet<Root<?>>(), new ArrayList<Expression<?>>());
	}

	public JpaAbstractQuery(Expression<Boolean> restriction, Metamodel metamodel, boolean distinct, Class<T> resultType,
			Set<Root<?>> roots, List<Expression<?>> groupBy) {
		super(restriction, metamodel, resultType);
		this.distinct = distinct;
		this.groupBy = groupBy;
		this.roots = roots;
	}

	public final Set<Root<?>> getRoots() {
		return roots;
	}

	public final List<Expression<?>> getGroupList() {
		return groupBy;
	}

	public final Predicate getGroupRestriction() {
		return unwrap(havingClause, Predicate.class);
	}

	public final boolean isDistinct() {
		return distinct;
	}

	public final Class<T> getResultType() {
		return resultType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (distinct ? 1231 : 1237);
		result = prime * result + ((groupBy == null) ? 0 : groupBy.hashCode());
		result = prime * result + ((havingClause == null) ? 0 : havingClause.hashCode());
		result = prime * result + ((roots == null) ? 0 : roots.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaAbstractQuery<?> other = (JpaAbstractQuery<?>) obj;
		if (distinct != other.distinct)
			return false;
		if (groupBy == null) {
			if (other.groupBy != null)
				return false;
		} else if (!groupBy.equals(other.groupBy)) {
			return false;
		}
		if (havingClause == null) {
			if (other.havingClause != null)
				return false;
		} else if (!havingClause.equals(other.havingClause)) {
			return false;
		}
		if (roots == null) {
			if (other.roots != null)
				return false;
		} else if (!roots.equals(other.roots)) {
			return false;
		}
		return true;
	}

}
