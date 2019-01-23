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
package org.worklogic.db.jpa.criteria;

import javax.persistence.criteria.Expression;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaNumber<N extends Number> extends JpaExpression<N> implements Expression<N> {

	protected final N number;

	public JpaNumber(N value, Class<? extends Number> javaType) {
		super("", (Class<? extends N>) javaType, null, null);
		this.number = value;
	}

	@Override
	public String toString() {
		return "" + number + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		JpaNumber<?> other = (JpaNumber<?>) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

}
