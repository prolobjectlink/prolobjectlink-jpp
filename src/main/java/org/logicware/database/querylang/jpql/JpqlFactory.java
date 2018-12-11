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
package org.logicware.database.querylang.jpql;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.logicware.RuntimeError;
import org.logicware.database.jpa.criteria.JpaTreeNode;
import org.logicware.database.querylang.Parser;
import org.logicware.database.querylang.SymbolEntry;
import org.logicware.database.querylang.ast.QueryDate;
import org.logicware.database.querylang.ast.QueryFrom;
import org.logicware.database.querylang.ast.QueryIdent;
import org.logicware.database.querylang.ast.QueryNumber;
import org.logicware.database.querylang.ast.QueryParList;
import org.logicware.database.querylang.ast.QueryTimestamp;

public class JpqlFactory {

	private JpqlFactory() {
	}

	public static JpaTreeNode newNumber(String value) {
		return new QueryNumber(value);
	}

	public static JpaTreeNode newFromClause(List<JpaTreeNode> declarations) {
		return new QueryFrom(declarations);
	}

	public static JpaTreeNode newIdentifier(String id) {
		return new QueryIdent(id);
	}

	public static RuntimeError syntaxError(Class<? extends Parser> class1, SymbolEntry current) {
		return new RuntimeError(class1, "Syntax Error at " + current.getLine() + ":" + current.getColumn());
	}

	public static JpaTreeNode newAbstractSchema(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newExpressions(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newUpdate(JpaTreeNode update, JpaTreeNode where) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newUpdate(JpaTreeNode update) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSet(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newUpdateItem(JpaTreeNode path, JpaTreeNode value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSelect(JpaTreeNode select, JpaTreeNode from, JpaTreeNode where, JpaTreeNode group, JpaTreeNode having) {
		return newSelect(select, from, where, group, having, null);
	}

	public static JpaTreeNode newSelect(JpaTreeNode select, JpaTreeNode from, JpaTreeNode where, JpaTreeNode group, JpaTreeNode having,
			JpaTreeNode order) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newClassName(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newParameters(List<JpaTreeNode> l) {
		return new QueryParList(l);
	}

	public static JpaTreeNode newAVG(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newMAX(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newMIN(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSUM(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCOUNT(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newGroupBy(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newHaving(JpaTreeNode conditional) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondExp(JpaTreeNode term, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondExp(JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondTerm(JpaTreeNode factor, JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondTerm(JpaTreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondFactor(boolean not, JpaTreeNode primary) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCondPrimary(JpaTreeNode expression) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSubQueryFrom(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newFromItem(JpaTreeNode var) {
		return newFromItem(null, var);
	}

	public static JpaTreeNode newFromItem(JpaTreeNode type, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCollMemberDekl(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newQualifiedId(JpaTreeNode id) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newQualifiedPath(JpaTreeNode id) {
		List<JpaTreeNode> l = new LinkedList<JpaTreeNode>();
		l.add(id);
		return newQualifiedPath(l);
	}

	public static JpaTreeNode newQualifiedPath(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newPath(JpaTreeNode id, JpaTreeNode rest) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newANY(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSOME(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newALL(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newArithExp(JpaTreeNode term, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newArithExp(JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newArithTerm(JpaTreeNode factor, JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newArithTerm(JpaTreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newArithFactor(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newAllOrAny(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSelectExtension(JpaTreeNode scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newConstructorExpression(JpaTreeNode className, JpaTreeNode parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newEntityTypeLiteral(JpaTreeNode variable) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newExistsExpression(boolean not, JpaTreeNode subquery) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newDelete(JpaTreeNode from) {
		return newDelete(from, null);
	}

	public static JpaTreeNode newDateTimeFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newDelete(JpaTreeNode from, JpaTreeNode where) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCurrentTimestamp(long time) {
		return new QueryTimestamp(time);
	}

	public static JpaTreeNode newCurrentTime(long time) {
		return newCurrentTimestamp(time);
	}

	public static JpaTreeNode newCurrentDate(long time) {
		return new QueryDate(new Date(time));
	}

	public static JpaTreeNode newNumericFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newLENGTH(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newLOCATE(JpaTreeNode s1, JpaTreeNode s2) {
		return newLOCATE(s1, s2, null);
	}

	public static JpaTreeNode newLOCATE(JpaTreeNode s1, JpaTreeNode s2, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newABS(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSQRT(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newMOD(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSIZE(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newINDEX(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newStringFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newCONCAT(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newSUBSTRING(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newTRIM(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newLOWER(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newUPPER(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newTrimSpecification(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newLeadingSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newTrailingSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newBothSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newNegative(boolean negative) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newLiteral(JpaTreeNode l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newNumeric(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newInteger(JpaTreeNode negative, Number number) {
		return null;
	}

	public static JpaTreeNode newDecimal(JpaTreeNode negative, Number number) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newString(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newDate(Object date) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newBoolean(boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newPattern(JpaTreeNode value) {
		return newPattern(value, null);
	}

	public static JpaTreeNode newPattern(JpaTreeNode value, JpaTreeNode esc) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newEscapeCharacter(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newTrimCharacter(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newOrderByExtension(JpaTreeNode agg) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newOrderBy(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newEntityBeanExpression(JpaTreeNode value) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newDatetimeExpression(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newBooleanValue(JpaTreeNode v) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newBooleanExpression(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newInnerJoin(JpaTreeNode path, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newOuterJoin(JpaTreeNode path, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newFetchJoin(JpaTreeNode fetch) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newOuterFetchJoin(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newInnerFetchJoin(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public static JpaTreeNode newUpdateClause(JpaTreeNode from, JpaTreeNode set) {
		// TODO Auto-generated method stub
		return null;
	}

}
