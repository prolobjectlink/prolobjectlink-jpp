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

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaCase<R> extends JpaExpression<R> implements Case<R> {

	public JpaCase(String alias, Class<? extends R> javaType, Metamodel metamodel) {
		super(alias, javaType, null, metamodel);
	}

	public Case<R> when(Expression<Boolean> condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Case<R> when(Expression<Boolean> condition, Expression<? extends R> result) {
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
		return "JpaCase [metamodel=" + metamodel + ", distinct=" + distinct + ", expression=" + expression + ", roots="
				+ roots + ", alias=" + alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

}
