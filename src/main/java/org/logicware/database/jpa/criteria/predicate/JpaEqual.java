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
package org.logicware.database.jpa.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Metamodel;

public class JpaEqual extends JpaComparablePredicate {

	public JpaEqual(String alias, Class<? extends Boolean> javaType, Expression<? extends Comparable<?>> expression,
			Metamodel metamodel, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, expressions);
	}

	@Override
	public String toString() {
		Object left = expressions.get(0);
		Object right = expressions.get(1);
		if (left instanceof From) {
			left = ((From<?, ?>) left).getAlias();
		}
		if (right instanceof From) {
			right = ((From<?, ?>) right).getAlias();
		}
		if (left instanceof Path) {
			left = ((Path<?>) left).getParentPath();
		}
		if (right instanceof Path) {
			right = ((Path<?>) right).getParentPath();
		}
		return "" + left + " = " + right + "";
	}

}
