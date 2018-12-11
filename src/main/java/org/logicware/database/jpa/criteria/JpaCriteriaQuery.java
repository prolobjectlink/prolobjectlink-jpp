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

import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.Metamodel;

public final class JpaCriteriaQuery<T> extends JpaAbstractQuery<T> implements CriteriaQuery<T> {

	private final Metamodel metamodel;
	private final Class<T> resultClass;

	public JpaCriteriaQuery(Metamodel metamodel) {
		this(null, null, metamodel);
	}

	public JpaCriteriaQuery(Predicate restriction) {
		this(restriction, null, null);
	}

	public JpaCriteriaQuery(Predicate restriction, Class<T> resultClass, Metamodel metamodel) {
		super(restriction);
		this.metamodel = metamodel;
		this.resultClass = resultClass;
	}

	public JpaCriteriaQuery(Class<T> resultClass, Metamodel metamodel) {
		this(null, resultClass, metamodel);
	}

	public CriteriaQuery<T> select(Selection<? extends T> selection) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> multiselect(Selection<?>... selections) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> multiselect(List<Selection<?>> selectionList) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> groupBy(Expression<?>... grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> groupBy(List<Expression<?>> grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> having(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> having(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> orderBy(Order... o) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> orderBy(List<Order> o) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaQuery<T> distinct(boolean distinct) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Order> getOrderList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<ParameterExpression<?>> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

}
