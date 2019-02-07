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
package org.prolobjectlink.db.jpa.criteria;

import javax.persistence.criteria.Expression;

public class JpaString extends JpaExpression<String> implements Expression<String> {

	protected final String string;

	public JpaString(String string) {
		super("", String.class, null, null);
		this.string = string;
	}

	@Override
	public String toString() {
		return "" + string + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((string == null) ? 0 : string.hashCode());
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
		JpaString other = (JpaString) obj;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string)) {
			return false;
		}
		return true;
	}

}
