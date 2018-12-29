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

import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaTrim<X> extends JpaFunctionExpression<X> implements Expression<X> {

	protected final Expression<?> character;

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Metamodel metamodel) {
		this(alias, javaType, x, null, null, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Trimspec trimSpec, Metamodel metamodel) {
		this(alias, javaType, x, trimSpec, null, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<String> x, Expression<Character> claracter,
			Metamodel metamodel) {
		this(alias, javaType, x, Trimspec.BOTH, claracter, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Trimspec trimSpec,
			Expression<?> claracter, Metamodel metamodel) {
		super(alias, javaType, x, "SUBSTRING", new JpaTrimSpec(trimSpec), metamodel);
		this.character = claracter;
	}

	@Override
	public String toString() {
		return "JpaTrim [character=" + character + ", operator=" + operator + ", right=" + right + ", metamodel="
				+ metamodel + ", distinct=" + distinct + ", expression=" + expression + ", roots=" + roots + ", alias="
				+ alias + ", value=" + value + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((character == null) ? 0 : character.hashCode());
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
		JpaTrim<?> other = (JpaTrim<?>) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character)) {
			return false;
		}
		return true;
	}

}
