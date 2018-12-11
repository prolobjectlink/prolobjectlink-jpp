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
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaConjuntion;

public final class JpaCriteriaQuery<T> extends JpaAbstractQuery<T> implements CriteriaQuery<T> {

	protected List<Order> orderBy;
	protected Selection<?> selection;
	protected List<From<?, ?>> joins;
	protected final List<Predicate> predicates = new LinkedList<Predicate>();

	public JpaCriteriaQuery(Expression<Boolean> restriction, Metamodel metamodel, boolean distinct, Class<T> resultType,
			Set<Root<?>> roots, List<Expression<?>> groupBy) {
		super(restriction, metamodel, distinct, resultType, roots, groupBy);
	}

	public JpaCriteriaQuery(Predicate restriction, Metamodel metamodel, boolean b, Class<T> resultClass) {
		this(restriction, metamodel, b, resultClass, new HashSet<Root<?>>(), new ArrayList<Expression<?>>());
	}

	public CriteriaQuery<T> select(Selection<? extends T> selection) {
		this.selection = selection;
		return this;
	}

	public CriteriaQuery<T> multiselect(Selection<?>... selections) {
		this.selection = new JpaCompoundSelection<T>(null, (Class<? extends T>) Object.class, null, selections);
		return this;
	}

	public CriteriaQuery<T> multiselect(List<Selection<?>> selectionList) {
		this.selection = new JpaCompoundSelection<T>(null, (Class<? extends T>) Object.class, null, selectionList);
		return this;
	}

	public CriteriaQuery<T> where(Expression<Boolean> restriction) {
		this.restriction = restriction;
		return this;
	}

	public CriteriaQuery<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			predicates.add(predicate);
		}
		return this;
	}

	public CriteriaQuery<T> groupBy(Expression<?>... grouping) {
		for (Expression<?> expression : grouping) {
			groupBy.add(expression);
		}
		return this;
	}

	public CriteriaQuery<T> groupBy(List<Expression<?>> grouping) {
		groupBy = grouping;
		return this;
	}

	public CriteriaQuery<T> having(Expression<Boolean> restriction) {
		havingClause = restriction;
		return this;
	}

	public CriteriaQuery<T> having(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			restriction = new JpaConjuntion(null, Boolean.class, null, metamodel, newList(restriction, predicate));
		}
		return this;
	}

	public CriteriaQuery<T> orderBy(Order... o) {
		for (Order order : o) {
			orderBy.add(order);
		}
		return this;
	}

	public CriteriaQuery<T> orderBy(List<Order> o) {
		orderBy = o;
		return this;
	}

	public CriteriaQuery<T> distinct(boolean distinct) {
		this.distinct = distinct;
		return null;
	}

	public List<Order> getOrderList() {
		return orderBy;
	}

	public Set<ParameterExpression<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(Class<X> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
