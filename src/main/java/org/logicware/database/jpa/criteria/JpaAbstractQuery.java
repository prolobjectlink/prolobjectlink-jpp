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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaConjuntion;

public abstract class JpaAbstractQuery<T> extends JpaAbstractCriteria<T> implements AbstractQuery<T> {

	protected boolean distinct;
//	protected Class<T> resultType;
	protected final Set<Root<?>> roots;

	/**
	 * @deprecated use restriction
	 */
	@Deprecated
	protected Expression<Boolean> whereClause;
	protected Expression<Boolean> havingClause;
	protected List<Expression<?>> groupBy = newList();

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

//	public AbstractQuery<T> where(Expression<Boolean> restriction) {
//		whereClause = restriction;
//		return this;
//	}
//
//	public AbstractQuery<T> where(Predicate... restrictions) {
//		for (Predicate predicate : restrictions) {
//			whereClause = new JpaConjuntion(null, Boolean.class, null, metamodel, newList(restriction, predicate));
//		}
//		return this;
//	}

//	public AbstractQuery<T> groupBy(Expression<?>... grouping) {
//		for (Expression<?> expression : grouping) {
//			groupBy.add(expression);
//		}
//		return this;
//	}
//
//	public AbstractQuery<T> groupBy(List<Expression<?>> grouping) {
//		groupBy = grouping;
//		return this;
//	}

//	public AbstractQuery<T> having(Expression<Boolean> restriction) {
//		havingClause = restriction;
//		return this;
//	}
//
//	public AbstractQuery<T> having(Predicate... restrictions) {
//		for (Predicate predicate : restrictions) {
//			restriction = new JpaConjuntion(null, Boolean.class, null, metamodel, newList(restriction, predicate));
//		}
//		return this;
//	}
//
//	public AbstractQuery<T> distinct(boolean distinct) {
//		this.distinct = distinct;
//		return this;
//	}

	public final Set<Root<?>> getRoots() {
		return roots;
	}

//	public Selection<T> getSelection() {
//		// TODO Auto-generated method stub
//		return null;
//	}

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

}
