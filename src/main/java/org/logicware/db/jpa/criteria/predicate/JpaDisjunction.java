/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.db.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.db.jpa.criteria.JpaPredicate;

public class JpaDisjunction extends JpaPredicate {

	public JpaDisjunction(String alias, Class<? extends Boolean> javaType, Expression<?> expression,
			Metamodel metamodel, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, BooleanOperator.OR, expressions);
	}

	@Override
	public String toString() {
		return "JpaDisjunction [expressions=" + expressions + ", operator=" + operator + ", metamodel=" + metamodel
				+ ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias=" + alias
				+ ", value=" + value + ", javaType=" + javaType + "]";
	}

}