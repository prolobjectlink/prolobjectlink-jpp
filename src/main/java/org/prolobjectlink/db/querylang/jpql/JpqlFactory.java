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
package org.prolobjectlink.db.querylang.jpql;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;

import org.prolobjectlink.RuntimeError;
import org.prolobjectlink.db.jpa.criteria.JpaTreeNode;
import org.prolobjectlink.db.querylang.SymbolEntry;
import org.prolobjectlink.db.querylang.ast.QueryDate;
import org.prolobjectlink.db.querylang.ast.QueryFrom;
import org.prolobjectlink.db.querylang.ast.QueryIdent;
import org.prolobjectlink.db.querylang.ast.QueryNumber;
import org.prolobjectlink.db.querylang.ast.QueryParList;
import org.prolobjectlink.db.querylang.ast.QueryTimestamp;

public class JpqlFactory {

	private final CriteriaBuilder builder;

	public JpqlFactory(CriteriaBuilder builder) {
		this.builder = builder;
	}

	public JpaTreeNode newNumber(String value) {
		return new QueryNumber(value);
	}

	public JpaTreeNode newFromClause(List<JpaTreeNode> declarations) {
		return new QueryFrom(declarations);
	}

	public JpaTreeNode newIdentifier(String id) {
		return new QueryIdent(id);
	}

	public RuntimeError syntaxError(SymbolEntry current) {
		return new RuntimeError("Syntax Error at " + current.getLine() + ":" + current.getColumn());
	}

	public JpaTreeNode newAbstractSchema(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newExpressions(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newUpdate(JpaTreeNode update, JpaTreeNode where) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newUpdate(JpaTreeNode update) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSet(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newUpdateItem(JpaTreeNode path, JpaTreeNode value) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSelect(JpaTreeNode select, JpaTreeNode from, JpaTreeNode where, JpaTreeNode group,
			JpaTreeNode having) {
		return newSelect(select, from, where, group, having, null);
	}

	public JpaTreeNode newSelect(JpaTreeNode select, JpaTreeNode from, JpaTreeNode where, JpaTreeNode group,
			JpaTreeNode having, JpaTreeNode order) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newClassName(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newParameters(List<JpaTreeNode> l) {
		return new QueryParList(l);
	}

	public JpaTreeNode newAVG(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newMAX(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newMIN(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSUM(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCOUNT(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newGroupBy(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newHaving(JpaTreeNode conditional) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondExp(JpaTreeNode term, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondExp(JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondTerm(JpaTreeNode factor, JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondTerm(JpaTreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondFactor(boolean not, JpaTreeNode primary) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCondPrimary(JpaTreeNode expression) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSubQueryFrom(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newFromItem(JpaTreeNode var) {
		return newFromItem(null, var);
	}

	public JpaTreeNode newFromItem(JpaTreeNode type, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCollMemberDekl(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newQualifiedId(JpaTreeNode id) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newQualifiedPath(JpaTreeNode id) {
		List<JpaTreeNode> l = new LinkedList<JpaTreeNode>();
		l.add(id);
		return newQualifiedPath(l);
	}

	public JpaTreeNode newQualifiedPath(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newPath(JpaTreeNode id, JpaTreeNode rest) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newANY(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSOME(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newALL(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newArithExp(JpaTreeNode term, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newArithExp(JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newArithTerm(JpaTreeNode factor, JpaTreeNode term) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newArithTerm(JpaTreeNode factor) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newArithFactor(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newAllOrAny(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSelectExtension(JpaTreeNode scalar) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newConstructorExpression(JpaTreeNode className, JpaTreeNode parameters) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newEntityTypeLiteral(JpaTreeNode variable) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newExistsExpression(boolean not, JpaTreeNode subquery) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newDelete(JpaTreeNode from) {
		return newDelete(from, null);
	}

	public JpaTreeNode newDateTimeFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newDelete(JpaTreeNode from, JpaTreeNode where) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCurrentTimestamp(long time) {
		return new QueryTimestamp(time);
	}

	public JpaTreeNode newCurrentTime(long time) {
		return newCurrentTimestamp(time);
	}

	public JpaTreeNode newCurrentDate(long time) {
		return new QueryDate(new Date(time));
	}

	public JpaTreeNode newNumericFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newLENGTH(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newLOCATE(JpaTreeNode s1, JpaTreeNode s2) {
		return newLOCATE(s1, s2, null);
	}

	public JpaTreeNode newLOCATE(JpaTreeNode s1, JpaTreeNode s2, JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newABS(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSQRT(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newMOD(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSIZE(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newINDEX(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newStringFunction(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newCONCAT(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newSUBSTRING(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newTRIM(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newLOWER(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newUPPER(JpaTreeNode e) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newTrimSpecification(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newLeadingSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newTrailingSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newBothSpec(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newNegative(boolean negative) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newLiteral(JpaTreeNode l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newNumeric(JpaTreeNode exp) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newInteger(JpaTreeNode negative, Number number) {
		return null;
	}

	public JpaTreeNode newDecimal(JpaTreeNode negative, Number number) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newString(String value) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newDate(Object date) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newBoolean(boolean value) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newPattern(JpaTreeNode value) {
		return newPattern(value, null);
	}

	public JpaTreeNode newPattern(JpaTreeNode value, JpaTreeNode esc) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newEscapeCharacter(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newTrimCharacter(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newOrderByExtension(JpaTreeNode agg) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newOrderBy(List<JpaTreeNode> l) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newEntityBeanExpression(JpaTreeNode value) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newDatetimeExpression(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newBooleanValue(JpaTreeNode v) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newBooleanExpression(JpaTreeNode s) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newInnerJoin(JpaTreeNode path, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newOuterJoin(JpaTreeNode path, JpaTreeNode var) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newFetchJoin(JpaTreeNode fetch) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newOuterFetchJoin(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newInnerFetchJoin(JpaTreeNode path) {
		// TODO Auto-generated method stub
		return null;
	}

	public JpaTreeNode newUpdateClause(JpaTreeNode from, JpaTreeNode set) {
		// TODO Auto-generated method stub
		return null;
	}

}
