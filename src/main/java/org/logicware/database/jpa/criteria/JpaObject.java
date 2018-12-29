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

public class JpaObject<X> extends JpaExpression<X> implements Expression<X> {

	protected final X object;

	public JpaObject(X x, Class<? extends X> javaType) {
		super("", javaType, null, null);
		this.object = x;
	}

	@Override
	public String toString() {
		if (object instanceof String) {
			return "'" + object + "'";
		}
		return "" + object + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		JpaObject<?> other = (JpaObject<?>) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object)) {
			return false;
		}
		return true;
	}

}
