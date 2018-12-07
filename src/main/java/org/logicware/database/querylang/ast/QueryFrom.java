/*
 * #%L
 * prolobjectlink-jcq-pl
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
package org.logicware.database.querylang.ast;

import java.util.List;

import org.logicware.database.querylang.AbstractFrom;
import org.logicware.database.querylang.FromNode;
import org.logicware.database.querylang.SymbolTable;
import org.logicware.database.querylang.TreeNode;

public class QueryFrom extends AbstractFrom implements FromNode {

	public QueryFrom(List<TreeNode> declarations) {
		super(declarations);
	}

	public QueryFrom(TreeNode declarations) {
		super(declarations);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "FROM";
	}

}
