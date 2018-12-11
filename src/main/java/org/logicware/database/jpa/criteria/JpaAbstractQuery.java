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

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.metamodel.EntityType;

public class JpaAbstractQuery<T> extends JpaAbstractCriteria implements AbstractQuery<T> {

	public JpaAbstractQuery(Expression<Boolean> restriction) {
		super(restriction);
	}

	public <X> Root<X> from(Class<X> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public <X> Root<X> from(EntityType<X> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> groupBy(Expression<?>... grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> groupBy(List<Expression<?>> grouping) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> having(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> having(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

	public AbstractQuery<T> distinct(boolean distinct) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<Root<?>> getRoots() {
		// TODO Auto-generated method stub
		return null;
	}

	public Selection<T> getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Expression<?>> getGroupList() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate getGroupRestriction() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	public Class<T> getResultType() {
		// TODO Auto-generated method stub
		return null;
	}

}
