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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Predicate.BooleanOperator;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaAndPredicate;
import org.logicware.database.jpa.criteria.predicate.JpaConjuntion;
import org.logicware.database.jpa.criteria.predicate.JpaNotNullPredicate;
import org.logicware.database.jpa.criteria.predicate.JpaNullPredicate;

public final class JpaSubQuery<T> extends JpaAbstractQuery<T> implements Subquery<T>, Selection<T>, Expression<T> {

	protected String alias;
	protected boolean distinct;
	protected Selection<?> selection;
	protected AbstractQuery<T> parent;
	protected Set<From<?, ?>> joins = new LinkedHashSet<From<?, ?>>();
	protected Set<Expression<?>> correlations = new LinkedHashSet<Expression<?>>();
	protected Set<Join<?, ?>> correlatedJoins = new LinkedHashSet<Join<?, ?>>();
	protected final List<Predicate> predicates = new LinkedList<Predicate>();

	public JpaSubQuery(boolean distinct, String alias, Selection<?> selection, Expression<Boolean> restriction,
			Metamodel metamodel, Class<T> resultType, AbstractQuery<T> parent) {
		super(restriction, metamodel, distinct, resultType, new HashSet<Root<?>>(), new ArrayList<Expression<?>>());
		this.selection = selection;
		this.distinct = distinct;
		this.parent = parent;
		this.alias = alias;
	}

	public Selection<T> alias(String name) {
		alias = name;
		return this;
	}

	public boolean isCompoundSelection() {
		return restriction instanceof CompoundSelection;
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<? extends T> getJavaType() {
		return resultType;
	}

	public String getAlias() {
		return alias;
	}

	public Predicate isNull() {
		return new JpaNullPredicate(null, Boolean.class, this, metamodel, null, null);
	}

	public Predicate isNotNull() {
		return new JpaNotNullPredicate(null, Boolean.class, this, metamodel, null, null);
	}

	public Predicate in(Object... values) {
		return new JpaIn(null, Object.class, this, metamodel, null, newList(values));
	}

	public Predicate in(Expression<?>... values) {
		return new JpaIn(null, Object.class, this, metamodel, null, newList(values));
	}

	public Predicate in(Collection<?> values) {
		return new JpaIn(null, Object.class, this, metamodel, null, newList(values));
	}

	public Predicate in(Expression<Collection<?>> values) {
		return new JpaIn(null, Object.class, this, metamodel, null, newList(values));
	}

	public <X> Expression<X> as(Class<X> type) {
		return (Expression<X>) this;
	}

	public Subquery<T> select(Expression<T> expression) {
		selection = expression;
		return this;
	}

	public <Y> Root<Y> correlate(Root<Y> parentRoot) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> Join<X, Y> correlate(Join<X, Y> parentJoin) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> CollectionJoin<X, Y> correlate(CollectionJoin<X, Y> parentCollection) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> SetJoin<X, Y> correlate(SetJoin<X, Y> parentSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, Y> ListJoin<X, Y> correlate(ListJoin<X, Y> parentList) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X, K, V> MapJoin<X, K, V> correlate(MapJoin<X, K, V> parentMap) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<?> getParent() {
		return parent;
	}

	public CommonAbstractCriteria getContainingQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Join<?, ?>> getCorrelatedJoins() {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(Class<X> entityClass) {
		Bindable<X> model = null;
		Set<Join<X, ?>> joinSet = new HashSet<Join<X, ?>>();
		Set<Fetch<X, ?>> fetches = new HashSet<Fetch<X, ?>>();
		char character = entityClass.getSimpleName().charAt(0);
		String als = "" + Character.toLowerCase(character) + "";
		ManagedType<X> managedType = metamodel.managedType(entityClass);
		if (managedType instanceof EntityType) {
			model = (EntityType<X>) managedType;
		}
		Path<X> pathParent = new JpaIdentification<X>(als, entityClass, null, metamodel, null, model);
		Root<X> from = new JpaFrom(als, entityClass, null, metamodel, pathParent, model, managedType, joinSet, fetches,
				false, false, null);
		roots.add(from);
		return from;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		return from(entity.getJavaType());
	}

	public Subquery<T> where(Expression<Boolean> restriction) {
		String alias = restriction.getAlias();
		Class<? extends Boolean> javaType = restriction.getJavaType();
		this.restriction = new JpaWhere(alias, javaType, restriction, metamodel, BooleanOperator.AND, newList());
		return this;
	}

	public Subquery<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			String als = restriction.getAlias();
			Class<? extends Boolean> javaType = restriction.getJavaType();
			restriction = new JpaAndPredicate(als, javaType, predicate, metamodel, null);
			predicates.add(predicate);
		}
		return this;
	}

	public Subquery<T> groupBy(Expression<?>... grouping) {
		for (Expression<?> expression : grouping) {
			groupBy.add(expression);
		}
		return this;
	}

	public Subquery<T> groupBy(List<Expression<?>> grouping) {
		groupBy = grouping;
		return this;
	}

	public Subquery<T> having(Expression<Boolean> restriction) {
		havingClause = restriction;
		return this;
	}

	public Subquery<T> having(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			restriction = new JpaConjuntion(null, Boolean.class, null, metamodel, newList(restriction, predicate));
		}
		return this;
	}

	public Subquery<T> distinct(boolean distinct) {
		this.distinct = distinct;
		return this;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		char character = type.getSimpleName().charAt(0);
		String als = "" + Character.toLowerCase(character) + "";
		return new JpaSubQuery<U>(distinct, als, selection, restriction, metamodel, type, null);
	}

	public Subquery<T> getSelection() {
		return this;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (distinct) {
			b.append("SELECT DISTINCT " + alias + " ");
		} else {
			b.append("SELECT " + alias + " ");
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
		int result = super.hashCode();
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((correlatedJoins == null) ? 0 : correlatedJoins.hashCode());
		result = prime * result + ((correlations == null) ? 0 : correlations.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		result = prime * result + ((predicates == null) ? 0 : predicates.hashCode());
		result = prime * result + ((joins == null) ? 0 : joins.hashCode());
		result = prime * result + ((selection == null) ? 0 : selection.hashCode());
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
		JpaSubQuery other = (JpaSubQuery) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (correlatedJoins == null) {
			if (other.correlatedJoins != null)
				return false;
		} else if (!correlatedJoins.equals(other.correlatedJoins))
			return false;
		if (correlations == null) {
			if (other.correlations != null)
				return false;
		} else if (!correlations.equals(other.correlations))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		if (predicates == null) {
			if (other.predicates != null)
				return false;
		} else if (!predicates.equals(other.predicates))
			return false;
		if (joins == null) {
			if (other.joins != null)
				return false;
		} else if (!joins.equals(other.joins))
			return false;
		if (selection == null) {
			if (other.selection != null)
				return false;
		} else if (!selection.equals(other.selection))
			return false;
		return true;
	}

}
