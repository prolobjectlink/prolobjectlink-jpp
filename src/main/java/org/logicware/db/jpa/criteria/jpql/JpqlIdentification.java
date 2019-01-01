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
package org.logicware.db.jpa.criteria.jpql;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.db.jpa.criteria.JpaExpression;
import org.logicware.db.jpa.criteria.JpaTreeNode;

public class JpqlIdentification<X> extends JpaExpression<X> implements JpaTreeNode {

	public JpqlIdentification(String alias, Class<? extends X> javaType, Expression<?> expression,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

	@Override
	public String toString() {
		return alias;
	}

}
