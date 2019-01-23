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

import java.util.Arrays;
import java.util.Iterator;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.worklogic.ArrayIterator;

public class JpaFunction<X> extends JpaExpression<X> implements Expression<X> {

	private final Expression<?>[] arguments;

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?>[] arguments, Metamodel metamodel) {
		this(alias, javaType, null, arguments, metamodel);
	}

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?> expression, Expression<?>[] arguments,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
		this.arguments = arguments;
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder(alias);
		Iterator<?> i = new ArrayIterator<Object>(arguments);
		if (i.hasNext()) {
			b.append('(');
			while (i.hasNext()) {
				b.append(i.next());
				if (i.hasNext()) {
					b.append(',');
					b.append(' ');
				}
			}
			b.append(')');
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(arguments);
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
		JpaFunction<X> other = (JpaFunction<X>) obj;
		return Arrays.equals(arguments, other.arguments);
	}

}
