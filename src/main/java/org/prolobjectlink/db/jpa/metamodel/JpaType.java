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
package org.prolobjectlink.db.jpa.metamodel;

import javax.persistence.metamodel.Type;

import org.prolobjectlink.db.Schema;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpaType<X> /* extends AbstractWrapper */ implements Type<X> {

	protected final Schema schema;
	protected final Class<X> javaType;

	public JpaType(Schema schema, Class<X> javaType) {
		this.schema = schema;
		this.javaType = javaType;
	}

	public Class<X> getJavaType() {
		return javaType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((javaType == null) ? 0 : javaType.hashCode());
		result = prime * result + ((schema == null) ? 0 : schema.hashCode());
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
		JpaType<?> other = (JpaType<?>) obj;
		if (javaType == null) {
			if (other.javaType != null)
				return false;
		} else if (!javaType.equals(other.javaType)) {
			return false;
		}
		if (schema == null) {
			if (other.schema != null)
				return false;
		} else if (!schema.equals(other.schema)) {
			return false;
		}
		return true;
	}

	@Override
	public abstract String toString();

}
