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

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaConjuntion;
import org.logicware.database.jpa.criteria.predicate.JpaNotNullPredicate;
import org.logicware.database.jpa.criteria.predicate.JpaNullPredicate;

public final class JpaSubQuery<T> extends JpaAbstractQuery<T> implements Subquery<T>, Selection<T>, Expression<T> {

	protected String alias;
	protected Expression<T> selection;
	protected AbstractQuery<T> parent;
	protected Set<From<?, ?>> processedJoins;
	protected Set<Expression<?>> correlations;
	protected Set<Join<?, ?>> correlatedJoins;

	public JpaSubQuery(Expression<Boolean> restriction, Class<T> type, Metamodel metamodel) {
		super(restriction, metamodel, type);
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
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> where(Expression<Boolean> restriction) {
		whereClause = restriction;
		return this;
	}

	public Subquery<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			whereClause = new JpaConjuntion(null, Boolean.class, null, metamodel, newList(restriction, predicate));
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

	public Subquery<T> getSelection() {
		return new JpaSubQuery<T>(restriction, resultType, metamodel);
	}

}
