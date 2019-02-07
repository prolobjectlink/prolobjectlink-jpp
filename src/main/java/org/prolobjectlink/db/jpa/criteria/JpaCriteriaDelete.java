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
package org.prolobjectlink.db.jpa.criteria;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public final class JpaCriteriaDelete<T> extends JpaAbstractCriteria<T> implements CriteriaDelete<T> {

	private Root<T> root;
	private final List<Predicate> predicates = new LinkedList<Predicate>();

	public JpaCriteriaDelete(Class<T> targetEntity, Predicate restriction, Metamodel metamodel) {
		super(restriction, metamodel, targetEntity);
	}

	public Root<T> from(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> from(EntityType<T> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> getRoot() {
		return root;
	}

	public JpaCriteriaDelete<T> where(Expression<Boolean> restriction) {
		this.restriction = restriction;
		return this;
	}

	public JpaCriteriaDelete<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			predicates.add(predicate);
		}
		return this;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		// TODO Auto-generated method stub
		return null;
	}

}
