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
package org.logicware.jpa.criteria;

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.metamodel.Bindable;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.Metamodel;
import javax.persistence.metamodel.PluralAttribute;
import javax.persistence.metamodel.SingularAttribute;

public class JPAPath<X> extends JPAExpression<X> implements Path<X> {

	protected final Path<?> pathParent;
	protected final Bindable<X> model;

	public JPAPath(String alias, Class<? extends X> javaType, Expression<X> expression, Metamodel metamodel,
			Path<?> pathParent, Bindable<X> model) {
		super(alias, javaType, expression, metamodel);
		this.pathParent = pathParent;
		this.model = model;
	}

	public Bindable<X> getModel() {
		return model;
	}

	public Path<?> getParentPath() {
		return pathParent;
	}

	public <Y> Path<Y> get(SingularAttribute<? super X, Y> attribute) {
		// TODO Auto-generated method stub
		return null;
	}

	public <E, C extends Collection<E>> Expression<C> get(PluralAttribute<X, C, E> collection) {
		// TODO Auto-generated method stub
		return null;
	}

	public <K, V, M extends Map<K, V>> Expression<M> get(MapAttribute<X, K, V> map) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<Class<? extends X>> type() {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> Path<Y> get(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}

}
