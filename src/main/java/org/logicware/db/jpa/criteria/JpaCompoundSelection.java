/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.db.jpa.criteria;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Selection;

import org.logicware.ArrayIterator;

public class JpaCompoundSelection<X> extends JpaSelection<X> implements CompoundSelection<X> {

	protected Selection<?>[] selections;

	public JpaCompoundSelection(String alias, Class<? extends X> javaType, Expression<X> expression,
			Selection<?>[] subSelections) {
		super(alias, javaType, expression);
		this.selections = subSelections;
	}

	public JpaCompoundSelection(String alias, Class<? extends X> javaType, Expression<X> expression,
			List<Selection<?>> selectionList) {
		super(alias, javaType, expression);
		this.selections = selectionList.toArray(new JpaSelection[0]);
	}

	@Override
	public boolean isCompoundSelection() {
		return true;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
		return Arrays.asList(selections);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		if (distinct) {
			b.append("SELECT DISTINCT ");
		} else {
			b.append("SELECT ");
		}
		if (selections != null && selections.length > 0) {
			Iterator<Selection<?>> i = new ArrayIterator<Selection<?>>(selections);
			while (i.hasNext()) {
				b.append(i.next().getAlias());
				if (i.hasNext()) {
					b.append(',');
				}
				b.append(' ');
			}
		}
		if (expression != null) {
			b.append(expression);
		}
		if (value != null) {
			b.append(value);
		}
		return "" + b + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(selections);
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
		JpaCompoundSelection<?> other = (JpaCompoundSelection<?>) obj;
		return Arrays.equals(selections, other.selections);
	}

}
