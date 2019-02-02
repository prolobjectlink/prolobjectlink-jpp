/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.SimpleCase;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaSimpleCase<C, R> extends JpaExpression<R> implements SimpleCase<C, R> {

	public JpaSimpleCase(String alias, Class<? extends R> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

	public Expression<C> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "JpaSimpleCase [metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression
				+ ", roots=" + roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

}
