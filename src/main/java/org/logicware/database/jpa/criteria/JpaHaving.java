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

import javax.persistence.criteria.AbstractQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaAndPredicate;

public class JpaHaving<T> extends JpaAbstractQuery<T> implements AbstractQuery<T> {

	public JpaHaving(Expression<Boolean> restriction, Metamodel metamodel, Class<T> resultType) {
		super(restriction, metamodel, resultType);
	}

	public JpaHaving(Expression<Boolean>[] restrictions, Metamodel metamodel, Class<T> resultType) {
		super(null, metamodel, resultType);
		for (Expression<Boolean> predicate : restrictions) {
			restriction = new JpaAndPredicate(null, Boolean.class, null, metamodel, newList(restriction, predicate));
		}
	}

}
