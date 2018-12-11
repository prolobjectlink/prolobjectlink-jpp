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

import javax.persistence.criteria.Predicate;

import org.logicware.database.jpa.criteria.JpaPredicate;

public abstract class JpaBoolean extends JpaPredicate implements Predicate {

	protected final Boolean bool;

	public JpaBoolean(Boolean value) {
		super("", Boolean.class, null, null, null, null);
		this.bool = value;
	}

	@Override
	public String toString() {
		return "" + bool + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bool == null) ? 0 : bool.hashCode());
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
		JpaBoolean other = (JpaBoolean) obj;
		if (bool == null) {
			if (other.bool != null)
				return false;
		} else if (!bool.equals(other.bool)) {
			return false;
		}
		return true;
	}

}
