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
package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;

public class JpaOrder implements Order {

	protected final boolean isAscending;
	protected final Expression<?> expression;

	public JpaOrder(Expression<?> expression) {
		this(expression, true);
	}

	public JpaOrder(Expression<?> expression, boolean isAscending) {
		this.expression = expression;
		this.isAscending = isAscending;
	}

	public Order reverse() {
		return new JpaOrder(expression, !isAscending);
	}

	public boolean isAscending() {
		return isAscending;
	}

	public Expression<?> getExpression() {
		return expression;
	}

}
