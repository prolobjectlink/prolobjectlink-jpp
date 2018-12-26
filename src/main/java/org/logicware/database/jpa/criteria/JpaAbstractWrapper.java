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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.Expression;

import org.logicware.AbstractWrapper;

public abstract class JpaAbstractWrapper extends AbstractWrapper implements JpaTreeNode {

	private static final String MESSAGE2 = "Unsupported featured";

	public String getJpqlString() {
		return toString();
	}

	public String getQueryString() {
		throw new UnsupportedOperationException(MESSAGE2);
	}

	protected List<Expression<?>> newList(Object... values) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(values.length);
		for (Object object : values) {
			list.add(new JpaObject<Object>(object, Object.class));
		}
		return list;
	}

	protected List<Expression<?>> newList(Collection<?> values) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(values.size());
		for (Object object : values) {
			list.add(new JpaObject<Object>(object, Object.class));
		}
		return list;
	}

	protected List<Expression<?>> newList(Expression<?>... expressions) {
		ArrayList<Expression<?>> list = new ArrayList<Expression<?>>(expressions.length);
		for (Expression<?> exp : expressions) {
			list.add(exp);
		}
		return list;
	}

//	@Override
//	public abstract int hashCode();
//
//	@Override
//	public abstract boolean equals(Object obj);

}
