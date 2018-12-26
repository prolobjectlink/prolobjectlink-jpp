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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Predicate.BooleanOperator;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaAndPredicate;
import org.logicware.database.jpa.criteria.predicate.JpaConjuntion;

public final class JpaCriteriaQuery<T> extends JpaAbstractQuery<T> implements CriteriaQuery<T> {

	protected Selection<T> selection;
	protected List<Order> orderBy = new ArrayList<Order>();
	protected List<From<?, ?>> joins = new ArrayList<From<?, ?>>();
	protected final List<Predicate> predicates = new LinkedList<Predicate>();
	protected final Set<ParameterExpression<?>> parameters = new LinkedHashSet<ParameterExpression<?>>();

	public JpaCriteriaQuery(Expression<Boolean> restriction, Metamodel metamodel, boolean distinct, Class<T> resultType,
			Set<Root<?>> roots, List<Expression<?>> groupBy) {
		super(restriction, metamodel, distinct, resultType, roots, groupBy);
	}

	public JpaCriteriaQuery(Predicate restriction, Metamodel metamodel, boolean b, Class<T> resultClass) {
		this(restriction, metamodel, b, resultClass, new HashSet<Root<?>>(), new ArrayList<Expression<?>>());
	}

	public CriteriaQuery<T> select(Selection<? extends T> selection) {
		String alias = selection.getAlias();
		Class<?> type = selection.getJavaType();
		roots.add((Root) selection);
		this.selection = new JpaSelection(distinct, alias, type, roots);
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
		String alias = restriction.getAlias();
		Class<? extends Boolean> javaType = restriction.getJavaType();
		this.restriction = new JpaWhere(alias, javaType, restriction, metamodel, BooleanOperator.AND, newList());
		return this;
	}

	public CriteriaQuery<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			String alias = restriction.getAlias();
			Class<? extends Boolean> javaType = restriction.getJavaType();
			restriction = new JpaAndPredicate(alias, javaType, predicate, metamodel, null);
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
		if (selection instanceof JpaSelection) {
			((JpaSelection<?>) selection).distinct = distinct;
		}
		this.distinct = distinct;
		return this;
	}

	public List<Order> getOrderList() {
		return orderBy;
	}

	public Set<ParameterExpression<?>> getParameters() {
		return parameters;
	}

	public <X> Root<X> from(Class<X> entityClass) {
		Bindable<X> model = null;
		Set<Join<X, ?>> joinSet = new HashSet<Join<X, ?>>();
		Set<Fetch<X, ?>> fetches = new HashSet<Fetch<X, ?>>();
		char character = entityClass.getSimpleName().charAt(0);
		String alias = "" + Character.toLowerCase(character) + "";
		ManagedType<X> managedType = metamodel.managedType(entityClass);
		if (managedType instanceof EntityType) {
			model = (EntityType<X>) managedType;
		}
		Path<X> pathParent = new JpaIdentification<X>(alias, entityClass, null, metamodel, null, model);
		Root<X> from = new JpaFrom(alias, entityClass, null, metamodel, pathParent, model, managedType, joinSet,
				fetches, false, false, null);
		roots.add(from);
		return from;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		return from(entity.getJavaType());
	}

	public Selection<T> getSelection() {
		return selection;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (selection != null) {
			b.append(selection);
		}
		if (!roots.isEmpty()) {
			b.append("FROM ");
			Iterator<?> i = roots.iterator();
			while (i.hasNext()) {
				b.append(i.next());
				if (i.hasNext()) {
					b.append(',');
				}
				b.append(' ');
			}
		}
		if (!groupBy.isEmpty()) {
			for (Expression<?> o : groupBy) {
				b.append("GROUP BY ");
				b.append(o);
			}
		}
		if (havingClause != null) {
			b.append(' ');
			b.append("HAVING ");
			b.append(havingClause);
		}
		if (restriction != null) {
			b.append(restriction);
		}
		if (!orderBy.isEmpty()) {
			for (Order o : orderBy) {
				b.append(o);
			}
		}
		if (!predicates.isEmpty()) {
			b.append(predicates);
		}
		if (joins != null && !joins.isEmpty()) {
			b.append(joins);
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (joins.hashCode());
		result = prime * result + (orderBy.hashCode());
		result = prime * result + (predicates.hashCode());
		result = prime * result + (selection.hashCode());
		result = prime * result + (parameters.hashCode());
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
		JpaCriteriaQuery<?> other = (JpaCriteriaQuery<?>) obj;
		if (joins == null) {
			if (other.joins != null)
				return false;
		} else if (!joins.equals(other.joins)) {
			return false;
		}
		if (!orderBy.equals(other.orderBy)) {
			return false;
		}
		if (!predicates.equals(other.predicates)) {
			return false;
		}
		if (!parameters.equals(other.parameters)) {
			return false;
		}
		if (selection == null) {
			if (other.selection != null)
				return false;
		} else if (!selection.equals(other.selection)) {
			return false;
		}
		return true;
	}

}
