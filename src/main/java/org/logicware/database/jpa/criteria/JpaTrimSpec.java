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

import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;

public class JpaTrimSpec extends JpaExpression<Trimspec> implements Expression<Trimspec> {

	protected final Trimspec specification;

	public JpaTrimSpec(Trimspec specification) {
		super("", Trimspec.class, null, null);
		this.specification = specification;
	}

	@Override
	public String toString() {
		return "" + specification + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
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
		JpaTrimSpec other = (JpaTrimSpec) obj;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification)) {
			return false;
		}
		return true;
	}

}
