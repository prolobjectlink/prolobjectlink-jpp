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
package org.worklogic.db.querylang;

import java.util.LinkedList;
import java.util.List;

import org.worklogic.db.jpa.criteria.JpaAbstractWrapper;
import org.worklogic.db.jpa.criteria.JpaTreeNode;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractFrom extends JpaAbstractWrapper implements JpaTreeNode {

	List<JpaTreeNode> declarations = new LinkedList<JpaTreeNode>();

	public AbstractFrom(List<JpaTreeNode> declarations) {
		this.declarations = declarations;
	}

	public AbstractFrom(JpaTreeNode declaration) {
		this.declarations.add(declaration);
	}

	@Override
	public String toString() {
		return "FROM " + declarations;
	}

}
