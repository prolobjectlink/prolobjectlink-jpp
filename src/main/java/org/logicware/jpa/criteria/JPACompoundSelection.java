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
package org.logicware.jpa.criteria;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Selection;

public class JPACompoundSelection<X> extends JPASelection<X> implements CompoundSelection<X> {

	protected ArrayList<Selection<?>> subSelections;

	public JPACompoundSelection(String alias, Class<? extends X> javaType, Expression<X> expression,
			ArrayList<Selection<?>> subSelections) {
		super(alias, javaType, expression);
		this.subSelections = subSelections;
	}

	@Override
	public boolean isCompoundSelection() {
		return true;
	}

	@Override
	public List<Selection<?>> getCompoundSelectionItems() {
		return subSelections;
	}

}
