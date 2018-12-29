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
package org.logicware.database.querylang.ast;

import java.util.Iterator;
import java.util.List;

import org.logicware.database.jpa.criteria.JpaTreeNode;
import org.logicware.database.querylang.AbstractParList;
import org.logicware.database.querylang.ParList;

public class QueryParList extends AbstractParList implements ParList {

	public QueryParList(List<JpaTreeNode> parameters) {
		super(parameters);
	}

	public Iterator<JpaTreeNode> iterator() {
		return parameters.iterator();
	}

	public int arity() {
		return parameters.size();
	}

}
