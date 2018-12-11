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

import java.util.Set;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Metamodel;

public class JpaMapJoin<Z, X> extends JpaJoin<Z, X> implements Join<Z, X> {

	public JpaMapJoin(String alias, Class<? extends X> javaType, Expression<X> expression, Metamodel metamodel,
			Path<?> pathParent, Bindable<X> model, ManagedType<X> managedType, Set<Join<X, ?>> joins,
			Set<Fetch<X, ?>> fetches, boolean isJoin, boolean isFetch, From<Z, X> correlatedParent, JoinType joinType) {
		super(alias, javaType, expression, metamodel, pathParent, model, managedType, joins, fetches, isJoin, isFetch,
				correlatedParent, joinType);
	}

}
