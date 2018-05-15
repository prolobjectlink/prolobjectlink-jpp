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
package org.logicware.pdb.jpa.metamodel;

import java.lang.reflect.Member;

import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.Type;

import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;

public abstract class JPAAttribute<X, Y> implements Attribute<X, Y> {

	protected final String name;
	protected final Type<Y> type;
	protected final ManagedType<X> declaredType;

	protected JPAAttribute(String name, Type<Y> type, ManagedType<X> declaredType) {
		this.name = name;
		this.type = type;
		this.declaredType = declaredType;
	}

	public String getName() {
		return name;
	}

	public ManagedType<X> getDeclaringType() {
		return declaredType;
	}

	public Class<Y> getJavaType() {
		return type.getJavaType();
	}

	public Member getJavaMember() {
		Member member = null;
		try {
			Class<X> clazz = declaredType.getJavaType();
			member = clazz.getDeclaredField(name);
		} catch (NoSuchFieldException e) {
			LoggerUtils.error(getClass(), LoggerConstants.NO_SUCH_FIELD, e);
		} catch (SecurityException e) {
			LoggerUtils.error(getClass(), LoggerConstants.SECURITY, e);
		}
		return member;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((declaredType == null) ? 0 : declaredType.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		JPAAttribute<?, ?> other = (JPAAttribute<?, ?>) obj;
		if (declaredType == null) {
			if (other.declaredType != null)
				return false;
		} else if (!declaredType.equals(other.declaredType))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JPAAttribute [name=" + name + ", type=" + type + ", declaredType=" + declaredType + "]";
	}

}
