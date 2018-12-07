/*
 * #%L
 * prolobjectlink-jcq-jpql
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
package org.logicware.database.querylang.jpql;

import java.util.LinkedList;
import java.util.List;

import org.logicware.RuntimeError;
import org.logicware.database.querylang.SymbolEntry;
import org.logicware.database.querylang.TreeNode;
import org.logicware.database.querylang.ast.QueryFrom;
import org.logicware.database.querylang.ast.QueryIdent;
import org.logicware.database.querylang.ast.QueryNumber;
import org.logicware.database.querylang.ast.QueryParList;

public class JpqlFactory {

	private JpqlFactory() {
	}

	public static TreeNode newNumber(String value) {
		return new QueryNumber(value);
	}

	public static TreeNode newFromClause(List<TreeNode> declarations) {
		return new QueryFrom(declarations);
	}

	public static TreeNode newIdentifier(String id) {
		return new QueryIdent(id);
	}

	public static RuntimeError syntaxError(Class<? extends JpqlParser> cls, SymbolEntry current) {
		return new RuntimeError(cls, "Syntax Error at " + current.getLine() + ":" + current.getColumn());
	}

	public static TreeNode newAbstractSchema(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newExpressions(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newUpdate(TreeNode update, TreeNode where) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newUpdate(TreeNode update) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newSet(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newUpdateItem(TreeNode path, TreeNode value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newSelect(TreeNode select, TreeNode from, TreeNode where, TreeNode group, TreeNode having) {
		return newSelect(select, from, where, group, having, null);
	}

	public static TreeNode newSelect(TreeNode select, TreeNode from, TreeNode where, TreeNode group, TreeNode having,
			TreeNode order) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newClassName(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newParameters(List<TreeNode> l) {
		return new QueryParList(l);
	}

	public static TreeNode newAVG(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newMAX(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newMIN(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newSUM(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCOUNT(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newGroupBy(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newHaving(TreeNode conditional) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondExp(TreeNode term, TreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondExp(TreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondTerm(TreeNode factor, TreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondTerm(TreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondFactor(boolean not, TreeNode primary) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCondPrimary(TreeNode expression) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newSubQueryFrom(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newFromItem(TreeNode var) {
		return newFromItem(null, var);
	}

	public static TreeNode newFromItem(TreeNode type, TreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newCollMemberDekl(TreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newQualifiedId(TreeNode id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newQualifiedPath(TreeNode id) {
		List<TreeNode> l = new LinkedList<TreeNode>();
		l.add(id);
		return newQualifiedPath(l);
	}

	public static TreeNode newQualifiedPath(List<TreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newPath(TreeNode id, TreeNode rest) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newANY(TreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newSOME(TreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newALL(TreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newArithExp(TreeNode term, TreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newArithExp(TreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newArithTerm(TreeNode factor, TreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newArithTerm(TreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newArithFactor(TreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TreeNode newAllOrAny(TreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

}
