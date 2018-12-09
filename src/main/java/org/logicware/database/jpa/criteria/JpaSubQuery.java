package org.logicware.database.jpa.criteria;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.CollectionJoin;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.MapJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.SetJoin;
import javax.persistence.criteria.Subquery;

public class JpaSubQuery<T> extends JpaAbstractQuery<T> implements Subquery<T>, Selection<T>, Expression<T> {

	private Class<T> javaType;

	public JpaSubQuery(Predicate restriction, Class<T> type) {
		super(restriction);
		this.javaType = type;
	}

	public Selection<T> alias(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isCompoundSelection() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<? extends T> getJavaType() {
		return javaType;
	}

	public String getAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate isNull() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate isNotNull() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Object... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Expression<?>... values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Collection<?> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate in(Expression<Collection<?>> values) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Expression<X> as(Class<X> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> select(Expression<T> expression) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> groupBy(Expression<?>... grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> groupBy(List<Expression<?>> grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> having(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> having(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public Subquery<T> distinct(boolean distinct) {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

	public CommonAbstractCriteria getContainingQuery() {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<T> getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Join<?, ?>> getCorrelatedJoins() {
		// TODO Auto-generated method stub
		return null;
	}

}
