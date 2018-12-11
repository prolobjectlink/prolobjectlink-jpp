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

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

import org.logicware.AbstractWrapper;

public class JpaAbstractCriteria extends AbstractWrapper implements CommonAbstractCriteria {

	protected Expression<Boolean> restriction;

	public JpaAbstractCriteria(Predicate restriction) {
		this.restriction = restriction;
	}

	public JpaAbstractCriteria(Expression<Boolean> restriction) {
		this.restriction = restriction;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		return new JpaSubQuery<U>(restriction, type);
	}

	public Predicate getRestriction() {
		if (!(restriction instanceof Predicate)) {
			throw new PersistenceException("Imposible unwrap " + restriction.getClass() + "to" + Predicate.class);
		}
		return (Predicate) restriction;
	}

}
