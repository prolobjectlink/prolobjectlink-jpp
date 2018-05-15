/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.jpa.criteria;

import java.util.Collection;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public class JPAExpression<X> extends JPASelection<X> implements Expression<X> {

	protected final Metamodel metamodel;

	public JPAExpression(String alias, Class<? extends X> javaType, Expression<X> expression, Metamodel metamodel) {
		super(alias, javaType, expression);
		this.metamodel = metamodel;
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

	public <Y> Expression<Y> as(Class<Y> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
