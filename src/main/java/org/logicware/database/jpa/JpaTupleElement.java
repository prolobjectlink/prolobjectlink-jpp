/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.jpa;

import javax.persistence.TupleElement;

import org.logicware.database.jpa.criteria.JpaAbstractWrapper;

public class JpaTupleElement<X> extends JpaAbstractWrapper implements TupleElement<X> {

	protected String alias;
	protected final X value;
	protected final Class<? extends X> javaType;

	public JpaTupleElement(String alias, Class<? extends X> javaType) {
		this(alias, javaType, null);
	}

	public JpaTupleElement(String alias, Class<? extends X> javaType, X value) {
		this.javaType = javaType;
		this.value = value;
		this.alias = alias;
	}

	public Class<? extends X> getJavaType() {
		return javaType;
	}

	public String getAlias() {
		return alias;
	}

	public X getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "LogicTupleElement [value=" + value + ", alias=" + alias + ", javaType=" + javaType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alias == null) ? 0 : alias.hashCode());
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
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
		JpaTupleElement<?> other = (JpaTupleElement<?>) obj;
		if (alias == null) {
			if (other.alias != null)
				return false;
		} else if (!alias.equals(other.alias))
			return false;
		if (javaType == null) {
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType))
			return false;
		return true;
	}

}
