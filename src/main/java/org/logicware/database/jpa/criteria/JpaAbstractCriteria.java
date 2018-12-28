/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public abstract class JpaAbstractCriteria<T> extends JpaAbstractWrapper implements CommonAbstractCriteria {

	protected final Class<T> resultType;
	protected final Metamodel metamodel;
	protected Expression<Boolean> restriction;

	public JpaAbstractCriteria(Expression<Boolean> restriction, Metamodel metamodel, Class<T> targetEntity) {
		this.resultType = targetEntity;
		this.restriction = restriction;
		this.metamodel = metamodel;
	}

	public Predicate getRestriction() {
		return (Predicate) restriction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((metamodel == null) ? 0 : metamodel.hashCode());
		result = prime * result + ((restriction == null) ? 0 : restriction.hashCode());
		result = prime * result + ((resultType == null) ? 0 : resultType.hashCode());
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
		JpaAbstractCriteria<?> other = (JpaAbstractCriteria<?>) obj;
		if (metamodel == null) {
			if (other.metamodel != null)
				return false;
		} else if (!metamodel.equals(other.metamodel)) {
			return false;
		}
		if (restriction == null) {
			if (other.restriction != null)
				return false;
		} else if (!restriction.equals(other.restriction)) {
			return false;
		}
		if (resultType == null) {
			if (other.resultType != null)
				return false;
		} else if (!resultType.equals(other.resultType)) {
			return false;
		}
		return true;
	}

}
