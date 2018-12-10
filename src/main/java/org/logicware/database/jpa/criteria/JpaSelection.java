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

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Selection;

import org.logicware.database.jpa.JPATupleElement;

public abstract class JpaSelection<X> extends JPATupleElement<X> implements Selection<X> {

	protected Expression<?> expression;

	public JpaSelection(String alias, Class<? extends X> javaType, Expression<?> expression) {
		super(alias, javaType);
		this.expression = expression;
	}

	public boolean isCompoundSelection() {
		return false;
	}

	public List<Selection<?>> getCompoundSelectionItems() {
		return Collections.emptyList();
	}

	public Selection<X> alias(String name) {
		this.alias = name;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((expression == null) ? 0 : expression.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaSelection<?> other = (JpaSelection<?>) obj;
		if (expression == null) {
			if (other.expression != null)
				return false;
		} else if (!expression.equals(other.expression))
			return false;
		return true;
	}

}