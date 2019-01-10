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
package org.worklogic.db.jpa.criteria.jpql;

import java.util.List;

import javax.persistence.criteria.Expression;

import org.worklogic.Wrapper;
import org.worklogic.db.jpa.criteria.JpaAbstractWrapper;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpqlClause extends JpaAbstractWrapper implements Wrapper {

	protected List<Expression<?>> expressions = newList();

	public JpqlClause(Expression<?> expression) {
		expressions.add(expression);
	}

	public JpqlClause(Expression<?>... expressions) {
		for (Expression<?> expression : expressions) {
			this.expressions.add(expression);
		}
	}

	public JpqlClause(List<Expression<?>> expressions) {
		this.expressions = expressions;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expressions == null) ? 0 : expressions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpqlClause other = (JpqlClause) obj;
		if (expressions == null) {
			if (other.expressions != null)
				return false;
		} else if (!expressions.equals(other.expressions)) {
			return false;
		}
		return true;
	}

}
