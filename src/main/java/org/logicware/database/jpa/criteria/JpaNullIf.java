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

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaNullIf<X> extends JpaFunctionExpression<X> implements Expression<X> {

	public JpaNullIf(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, "CONCAT", right, metamodel);
	}

	@Override
	public String toString() {
		return "JpaNullIf [operator=" + operator + ", right=" + right + ", metamodel=" + metamodel + ", distinct="
				+ distinct + ", expression=" + expression + ", roots=" + roots + ", alias=" + alias + ", value=" + value
				+ ", javaType=" + javaType + "]";
	}

}
