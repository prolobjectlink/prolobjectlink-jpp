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

import java.util.LinkedList;
import java.util.List;

import org.prolobjectlink.db.jpa.criteria.JpaTreeNode;
import org.prolobjectlink.db.querylang.Parser;
import org.prolobjectlink.db.querylang.Scanner;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JpqlParser extends JpqlChecker implements Parser {

	public JpqlParser(Scanner scanner, JpqlFactory jpqlfactory) {
		super(scanner, jpqlfactory);
	}

	public JpaTreeNode parseQuery() {
		current = scanner.next();
		if (current.sym == SELECT) {
			return selectStatement();
		} else if (current.sym == UPDATE) {
			return updateStatement();
		} else if (current.sym == DELETE) {
			return deleteStatement();
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode selectStatement() {
		JpaTreeNode where = null;
		JpaTreeNode group = null;
		JpaTreeNode having = null;
		JpaTreeNode order = null;
		JpaTreeNode select = selectClause();
		JpaTreeNode from = fromClause();
		if (current.sym == WHERE) {
			where = whereClause();
		}
		if (current.sym == GROUP) {
			group = groupByClause();
		}
		if (current.sym == HAVING) {
			having = havingClause();
		}
		if (current.sym == ORDER) {
			order = orderByClause();
		}
		return jpqlfactory.newSelect(

				select, from, where, group, having, order

		);
	}

	private JpaTreeNode updateStatement() {
		JpaTreeNode update = updateClause();
		if (current.sym == WHERE) {
			JpaTreeNode where = whereClause();
			return jpqlfactory.newUpdate(update, where);
		}
		return update;
	}

	private JpaTreeNode deleteStatement() {
		if (current.sym == DELETE) {
			current = scanner.next();
			if (current.sym == FROM) {
				current = scanner.next();
				JpaTreeNode f = fromItem();
				if (current.sym == WHERE) {
					current = scanner.next();
					JpaTreeNode w = whereClause();
					return jpqlfactory.newDelete(f, w);
				}
				return jpqlfactory.newDelete(f);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode fromClause() {
		if (current.sym == FROM) {
			current = scanner.next();

			List<JpaTreeNode> vars = new LinkedList<JpaTreeNode>();
			JpaTreeNode var = identificationVariableDeclaration();
			vars.add(var);

			// collection_member_declaration
			current = scanner.next();
			if (current.sym == IN) {
				vars.add(collectionMemberDeclaration());
			} else if (current.sym == COMMA) {
				while (current.sym == COMMA) {
					var = identificationVariableDeclaration();
					current = scanner.next();
					vars.add(var);
				}
			}
			return jpqlfactory.newFromClause(vars);

		} else {
			throw jpqlfactory.syntaxError(getClass(), current);
		}
	}

	private JpaTreeNode identificationVariableDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode fromItem() {
		JpaTreeNode type = abstractSchemaName();
		if (current.sym == AS) {
			current = scanner.next();
			JpaTreeNode var = identificationVariable();
			return jpqlfactory.newFromItem(type, var);
		}
		JpaTreeNode var = identificationVariable();
		return jpqlfactory.newFromItem(var);
	}

	private JpaTreeNode subQueryFromClause() {
		current = scanner.next();
		List<JpaTreeNode> l = newList();
		l.add(subQueryFromItem());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(subQueryFromItem());
		}
		return jpqlfactory.newSubQueryFrom(l);
	}

	private JpaTreeNode subselectIdentificationVariableDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode subQueryFromItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode innerJoin() {
		if (current.sym == INNER) {
			current = scanner.next();
		}
		if (current.sym == JOIN) {
			current = scanner.next();
			JpaTreeNode path = path();
			if (current.sym == AS) {
				current = scanner.next();
			}
			JpaTreeNode var = identificationVariable();
			return jpqlfactory.newInnerJoin(path, var);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode collectionMemberDeclaration() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			if (current.sym == KEY || current.sym == VALUE) {
				JpaTreeNode path = qualifiedPath();
				return jpqlfactory.newCollMemberDekl(path);
			} else {
				JpaTreeNode path = path();
				return jpqlfactory.newCollMemberDekl(path);
			}
		}
		if (current.sym == RPAR) {
			current = scanner.next();
		}
		if (current.sym == AS) {
			current = scanner.next();
		}
		JpaTreeNode var = identificationVariable();
		return jpqlfactory.newCollMemberDekl(var);
	}

	private JpaTreeNode outerJoin() {
		if (current.sym == LEFT) {
			current = scanner.next();
			if (current.sym == OUTER) {
				current = scanner.next();
			}
			if (current.sym == JOIN) {
				current = scanner.next();
				JpaTreeNode path = path();
				if (current.sym == AS) {
					current = scanner.next();
				}
				JpaTreeNode var = identificationVariable();
				return jpqlfactory.newOuterJoin(path, var);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode fetchJoin() {
		JpaTreeNode f = null;
		if (current.sym == LEFT) {
			f = outerFetchJoin();
		} else {
			f = innerFetchJoin();
		}
		return jpqlfactory.newFetchJoin(f);
	}

	private JpaTreeNode outerFetchJoin() {
		if (current.sym == LEFT) {
			current = scanner.next();
			if (current.sym == OUTER) {
				current = scanner.next();
			}
			if (current.sym == JOIN) {
				current = scanner.next();
				if (current.sym == FETCH) {
					current = scanner.next();
					JpaTreeNode p = path();
					return jpqlfactory.newOuterFetchJoin(p);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode innerFetchJoin() {
		if (current.sym == INNER) {
			current = scanner.next();
		}
		if (current.sym == JOIN) {
			current = scanner.next();
			if (current.sym == FETCH) {
				JpaTreeNode path = path();
				return jpqlfactory.newInnerFetchJoin(path);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode path() {
		JpaTreeNode id = identificationVariable();
		JpaTreeNode rest = pathComponent();
		return jpqlfactory.newPath(id, rest);
	}

	private JpaTreeNode updateClause() {
		current = scanner.next();
		JpaTreeNode from = fromItem();
		if (current.sym == SET) {
			current = scanner.next();
			JpaTreeNode set = setClause();
			return jpqlfactory.newUpdateClause(from, set);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode setClause() {
		List<JpaTreeNode> l = newList();
		l.add(updateItem());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(updateItem());
		}
		return jpqlfactory.newSet(l);
	}

	private JpaTreeNode updateItem() {
		JpaTreeNode path = path();
		if (current.sym == EQ) {
			current = scanner.next();
			JpaTreeNode value = newValue();
			return jpqlfactory.newUpdateItem(path, value);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode newValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode simpleEntityExpression() {
		if (current.sym == QUESTION || current.sym == COLON) {
			return inputParameter();
		}
		return identificationVariable();
	}

	private JpaTreeNode selectClause() {
		JpaTreeNode exp = null;
		current = scanner.next();
		if (current.sym == DISTINCT) {
			exp = selectExpressions();
		} else {
			exp = selectExpressions();
		}
		return exp;
	}

	private JpaTreeNode simpleSelectClause() {
		JpaTreeNode exp = null;
		current = scanner.next();
		if (current.sym == DISTINCT) {
			exp = selectExpressions();
		} else {
			exp = selectExpressions();
		}
		return exp;
	}

	private JpaTreeNode selectExpressions() {
		List<JpaTreeNode> l = newList();
		l.add(selectExpression());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(selectExpression());
		}
		return jpqlfactory.newExpressions(l);
	}

	private JpaTreeNode selectExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode selectExtension() {
		JpaTreeNode scalar = scalarFunction();
		return jpqlfactory.newSelectExtension(scalar);
	}

	private JpaTreeNode subSelectExpressions() {
		List<JpaTreeNode> l = newList();
		l.add(subSelectExpression());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(subSelectExpression());
		}
		return jpqlfactory.newExpressions(l);
	}

	private JpaTreeNode subSelectExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode constructorExpression() {
		if (current.sym == NEW) {
			current = scanner.next();
			JpaTreeNode cn = className();
			JpaTreeNode ps = constructorParameters();
			return jpqlfactory.newConstructorExpression(cn, ps);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode className() {
		List<JpaTreeNode> l = newList();
		l.add(identificationVariable());
		while (current.sym == DOT) {
			current = scanner.next();
			l.add(identificationVariable());
		}
		return jpqlfactory.newClassName(l);
	}

	private JpaTreeNode constructorParameters() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			List<JpaTreeNode> l = newList();
			l.add(constructorParameter());
			while (current.sym == COMMA) {
				current = scanner.next();
				l.add(constructorParameter());
			}
			if (current.sym == RPAR) {
				current = scanner.next();
			} else {
				throw syntaxError();
			}
			return jpqlfactory.newParameters(l);
		}
		return jpqlfactory.newParameters(newList());
	}

	private JpaTreeNode constructorParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode aggregateSelectExpression() {
		int key = current.sym;
		JpaTreeNode aggregate = null;
		switch (key) {
		case AVG:
			aggregate = avg();
			break;
		case MIN:
			aggregate = max();
			break;
		case MAX:
			aggregate = min();
			break;
		case SUM:
			aggregate = sum();
			break;
		default:
			aggregate = count();
			break;
		}
		return aggregate;
	}

	private void distinct() {
		if (current.sym == DISTINCT) {
			current = scanner.next();
		}
	}

	private JpaTreeNode aggregatePath() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode distinctPath() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode count() {
		current = scanner.next();
		JpaTreeNode path = aggregatePath();
		return jpqlfactory.newCOUNT(path);
	}

	private JpaTreeNode avg() {
		current = scanner.next();
		JpaTreeNode path = aggregatePath();
		return jpqlfactory.newAVG(path);
	}

	private JpaTreeNode max() {
		current = scanner.next();
		JpaTreeNode path = aggregatePath();
		return jpqlfactory.newMAX(path);
	}

	private JpaTreeNode min() {
		current = scanner.next();
		JpaTreeNode path = aggregatePath();
		return jpqlfactory.newMIN(path);
	}

	private JpaTreeNode sum() {
		current = scanner.next();
		JpaTreeNode path = aggregatePath();
		return jpqlfactory.newSUM(path);
	}

	private JpaTreeNode whereClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode groupByClause() {
		current = scanner.next();
		if (current.sym == BY) {
			current = scanner.next();
			List<JpaTreeNode> l = newList();
			l.add(groupByItem());
			while (current.sym == COMMA) {
				current = scanner.next();
				l.add(groupByItem());
			}
			return jpqlfactory.newGroupBy(l);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode groupByItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode groupByExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode havingClause() {
		current = scanner.next();
		JpaTreeNode exp = conditionalExpression();
		return jpqlfactory.newHaving(exp);
	}

	private JpaTreeNode subQuery() {
		JpaTreeNode where = null;
		JpaTreeNode group = null;
		JpaTreeNode having = null;
		JpaTreeNode select = simpleSelectClause();
		JpaTreeNode from = subQueryFromClause();
		if (current.sym == WHERE) {
			where = whereClause();
		}
		if (current.sym == GROUP) {
			group = groupByClause();
		}
		if (current.sym == HAVING) {
			having = havingClause();
		}
		return jpqlfactory.newSelect(

				select, from, where, group, having

		);
	}

	private JpaTreeNode conditionalExpression() {
		JpaTreeNode term = conditionalTerm();
		current = scanner.next();
		if (current.sym == OR) {
			current = scanner.next();
			JpaTreeNode exp = conditionalExpression();
			return jpqlfactory.newCondExp(term, exp);
		}
		return jpqlfactory.newCondExp(term);
	}

	private JpaTreeNode conditionalTerm() {
		JpaTreeNode factor = conditionalFactor();
		current = scanner.next();
		if (current.sym == AND) {
			current = scanner.next();
			JpaTreeNode term = conditionalTerm();
			return jpqlfactory.newCondTerm(factor, term);
		}
		return jpqlfactory.newCondTerm(factor);
	}

	private JpaTreeNode conditionalFactor() {
		current = scanner.next();
		if (current.sym == NOT) {
			current = scanner.next();
			JpaTreeNode p = conditionalPrimary();
			return jpqlfactory.newCondFactor(true, p);
		}
		JpaTreeNode p = conditionalPrimary();
		return jpqlfactory.newCondFactor(false, p);
	}

	private JpaTreeNode conditionalPrimary() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			JpaTreeNode e = conditionalExpression();
			if (current.sym == RPAR) {
				current = scanner.next();
				return jpqlfactory.newCondPrimary(e);
			}
		}
		JpaTreeNode e = simpleCondExpression();
		return jpqlfactory.newCondPrimary(e);
	}

	private JpaTreeNode simpleCondExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode betweenExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode inExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode entityTypeLiteral() {
		JpaTreeNode v = identificationVariable();
		return jpqlfactory.newEntityTypeLiteral(v);
	}

	private JpaTreeNode literalOrParam() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode likeExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode nullComparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode emptyCollectionComparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode collectionMemberExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode existsExpression() {
		if (current.sym == NOT && current.sym == EXISTS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode l = subQuery();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newExistsExpression(true, l);
				} else {
					throw syntaxError();
				}
			}
		} else if (current.sym == EXISTS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode l = subQuery();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newExistsExpression(false, l);
				} else {
					throw syntaxError();
				}
			}
		}
		throw syntaxError();
	}

	private JpaTreeNode allOrAnyExpression() {
		int key = current.sym;
		JpaTreeNode exp = null;
		switch (key) {
		case ANY:
			exp = anyExpression();
			break;
		case SOME:
			exp = someExpression();
			break;
		default:
			exp = allExpression();
			break;
		}
		return jpqlfactory.newAllOrAny(exp);
	}

	private JpaTreeNode anyExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			JpaTreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return jpqlfactory.newANY(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode someExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			JpaTreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return jpqlfactory.newSOME(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode allExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			JpaTreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return jpqlfactory.newALL(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode comparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode stringComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode booleanComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode enumComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode entityComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode arithmeticComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode datetimeComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode scalarFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode arithmeticPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode arithmeticExpression() {
		JpaTreeNode term = arithmeticTerm();
		current = scanner.next();
		if (current.sym == PLUS) {
			current = scanner.next();
			JpaTreeNode exp = arithmeticExpression();
			return jpqlfactory.newArithExp(term, exp);
		}
		if (current.sym == MINUS) {
			current = scanner.next();
			JpaTreeNode exp = arithmeticExpression();
			return jpqlfactory.newArithExp(term, exp);
		}
		return jpqlfactory.newArithExp(term);
	}

	private JpaTreeNode arithmeticTerm() {
		JpaTreeNode factor = arithmeticFactor();
		current = scanner.next();
		if (current.sym == TIMES) {
			current = scanner.next();
			JpaTreeNode term = arithmeticTerm();
			return jpqlfactory.newArithTerm(factor, term);
		}
		if (current.sym == DIV) {
			current = scanner.next();
			JpaTreeNode term = arithmeticTerm();
			return jpqlfactory.newArithTerm(factor, term);
		}
		return jpqlfactory.newArithTerm(factor);
	}

	private JpaTreeNode arithmeticFactor() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			JpaTreeNode exp = conditionalPrimary();
			return jpqlfactory.newArithFactor(exp);
		}
		JpaTreeNode p = arithmeticPrimary();
		return jpqlfactory.newArithFactor(p);
	}

	private JpaTreeNode qualifiedPath() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE) {
			JpaTreeNode id = identificationVariable();
			return jpqlfactory.newQualifiedPath(id);
		} else if (current.sym == DOT) {
			List<JpaTreeNode> l = newList();
			l.add(pathComponent());
			while (current.sym == DOT) {
				current = scanner.next();
				l.add(pathComponent());
			}
			return jpqlfactory.newQualifiedPath(l);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode qualifiedIdentificationVariable() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE || current.sym == ENTRY) {
			JpaTreeNode id = identificationVariable();
			return jpqlfactory.newQualifiedId(id);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode generalIdentificationVariable() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE) {
			JpaTreeNode id = identificationVariable();
			return jpqlfactory.newQualifiedId(id);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode entityTypeComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode typeDiscriminator() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode entityTypeExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode scalarExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode caseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode generalCaseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode whenClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode simpleCaseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode simpleWhenClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode coalesceExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode nullifExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode negative() {
		boolean negative = false;
		if (current.sym == MINUS) {
			current = scanner.next();
			negative = true;
		} else if (current.sym == PLUS) {
			current = scanner.next();
		}
		return jpqlfactory.newNegative(negative);
	}

	private JpaTreeNode stringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode stringExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode stringPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode datetimeExpression() {
		JpaTreeNode s = null;
		if (current.sym == LPAR) {
			current = scanner.next();
			s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
			} else {
				throw syntaxError();
			}
		} else {
			s = datetimePrimary();
		}
		return jpqlfactory.newDatetimeExpression(s);
	}

	private JpaTreeNode datetimePrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode booleanValue() {
		JpaTreeNode v = null;
		if (current.sym == LPAR) {
			current = scanner.next();
			v = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
			} else {
				throw syntaxError();
			}
		} else {
			v = path();
		}
		return jpqlfactory.newBooleanValue(v);
	}

	private JpaTreeNode booleanExpression() {
		JpaTreeNode s = null;
		if (current.sym == LPAR) {
			current = scanner.next();
			s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
			} else {
				throw syntaxError();
			}
		} else {
			s = booleanPrimary();
		}
		return jpqlfactory.newBooleanExpression(s);
	}

	private JpaTreeNode booleanPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode enumExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode enumPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode enumLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode entityBeanValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode entityBeanExpression() {
		int key = current.sym;
		JpaTreeNode v = null;
		switch (key) {
		case QUESTION:
			current = scanner.next();
			v = positionalInputParameter();
			break;
		case COLON:
			current = scanner.next();
			v = namedInputParameter();
			break;
		default:
			current = scanner.next();
			v = entityBeanValue();
			break;
		}
		return jpqlfactory.newEntityBeanExpression(v);
	}

	private JpaTreeNode functionsReturningStrings() {
		int key = current.sym;
		JpaTreeNode exp = null;
		switch (key) {
		case CONCAT:
			exp = concat();
			break;
		case SUBSTRING:
			exp = substring();
			break;
		case TRIM:
			exp = trim();
			break;
		case LOWER:
			exp = lower();
			break;
		default:
			exp = upper();
			break;
		}
		return jpqlfactory.newStringFunction(exp);
	}

	private JpaTreeNode concat() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode substring() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode trim() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode lower() {
		if (current.sym == LOWER) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newLOWER(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode upper() {
		if (current.sym == UPPER) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newUPPER(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode trimSpecification() {
		int key = current.sym;
		JpaTreeNode exp = null;
		switch (key) {
		case LEADING:
			exp = jpqlfactory.newLeadingSpec(exp);
			break;
		case TRAILING:
			exp = jpqlfactory.newTrailingSpec(exp);
			break;
		default:
			exp = jpqlfactory.newBothSpec(exp);
			break;
		}
		return jpqlfactory.newTrimSpecification(exp);
	}

	private JpaTreeNode functionsReturningNumerics() {
		int key = current.sym;
		JpaTreeNode exp = null;
		switch (key) {
		case LENGTH:
			exp = length();
			break;
		case LOCATE:
			exp = locate();
			break;
		case ABS:
			exp = abs();
			break;
		case SQRT:
			exp = sqrt();
			break;
		case MOD:
			exp = mod();
			break;
		case SIZE:
			exp = size();
			break;
		default:
			exp = index();
			break;
		}
		return jpqlfactory.newNumericFunction(exp);
	}

	private JpaTreeNode length() {
		if (current.sym == LENGTH) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newLENGTH(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode locate() {
		if (current.sym == LOCATE) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode s1 = stringExpression();
				if (current.sym == COMMA) {
					current = scanner.next();
					JpaTreeNode s2 = stringExpression();
					if (current.sym == COMMA) {
						current = scanner.next();
						JpaTreeNode exp = arithmeticExpression();
						if (current.sym == RPAR) {
							current = scanner.next();
						} else {
							throw syntaxError();
						}
						return jpqlfactory.newLOCATE(s1, s2, exp);
					}
					if (current.sym == RPAR) {
						current = scanner.next();
					} else {
						throw syntaxError();
					}
					return jpqlfactory.newLOCATE(s1, s2);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		}
		return null;
	}

	private JpaTreeNode abs() {
		if (current.sym == ABS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = arithmeticExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newABS(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode sqrt() {
		if (current.sym == SQRT) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = arithmeticExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newSQRT(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode mod() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode size() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode index() {
		if (current.sym == INDEX) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				JpaTreeNode e = identificationVariable();
				if (current.sym == RPAR) {
					current = scanner.next();
					return jpqlfactory.newINDEX(e);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode functionsReturningDatetime() {
		long time = System.currentTimeMillis();
		int key = current.sym;
		JpaTreeNode exp = null;
		switch (key) {
		case CURRENT_TIMESTAMP:
			current = scanner.next();
			exp = jpqlfactory.newCurrentTimestamp(time);
			break;
		case CURRENT_TIME:
			current = scanner.next();
			exp = jpqlfactory.newCurrentTime(time);
			break;
		default:
			current = scanner.next();
			exp = jpqlfactory.newCurrentDate(time);
			break;
		}
		return jpqlfactory.newDateTimeFunction(exp);
	}

	private JpaTreeNode orderByClause() {
		if (current.sym == ORDER) {
			current = scanner.next();
			if (current.sym == BY) {
				current = scanner.next();
				List<JpaTreeNode> l = newList();
				l.add(orderByItem());
				while (current.sym == COMMA) {
					current = scanner.next();
					l.add(orderByItem());
				}
				return jpqlfactory.newOrderBy(l);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode orderByItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode orderbyExtension() {
		JpaTreeNode agg = aggregateSelectExpression();
		return jpqlfactory.newOrderByExtension(agg);
	}

	private JpaTreeNode abstractSchemaName() {
		List<JpaTreeNode> l = newList();
		l.add(pathComponent());
		while (current.sym == DOT) {
			current = scanner.next();
			l.add(pathComponent());
		}
		return jpqlfactory.newAbstractSchema(l);
	}

	private JpaTreeNode tok() {
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return jpqlfactory.newIdentifier(id);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode identificationVariable() {
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return jpqlfactory.newIdentifier(id);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode pathComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode literal() {
		JpaTreeNode l = null;
		int key = current.sym;
		switch (key) {
		case FLOATING_POINT_LITERAL:
			current = scanner.next();
			l = decimalLiteral();
			break;
		case INTEGER_LITERAL:
			current = scanner.next();
			l = integerLiteral();
			break;
		case FALSE:
		case TRUE:
			current = scanner.next();
			l = booleanLiteral();
			break;
		case STRING_LITERAL:
			current = scanner.next();
			l = stringLiteral();
			break;

		// string literal 2
		// case STRING_LITERAL:
		// current = scanner.next();
		// l = stringLiteral();
		// break;

		// enum literal
		// case STRING_LITERAL:
		// current = scanner.next();
		// l = stringLiteral();
		// break;

		case DATE:
			current = scanner.next();
			l = dateLiteral();
			break;

		case TIMESTAMP:
			current = scanner.next();
			l = timestampLiteral();
			break;

		// time literal
		default:
			current = scanner.next();
			l = timeLiteral();
			break;
		}
		return jpqlfactory.newLiteral(l);
	}

	private JpaTreeNode numericLiteral() {
		JpaTreeNode exp = null;
		if (current.sym == INTEGER_LITERAL) {
			current = scanner.next();
			exp = integerLiteral();
		} else {
			current = scanner.next();
			exp = decimalLiteral();
		}
		return jpqlfactory.newNumeric(exp);
	}

	private JpaTreeNode integerLiteral() {
		JpaTreeNode negative = negative();
		if (current.sym == INTEGER_LITERAL) {
			Integer n = (Integer) current.value;
			current = scanner.next();
			return jpqlfactory.newInteger(negative, n);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode decimalLiteral() {
		JpaTreeNode negative = negative();
		if (current.sym == FLOATING_POINT_LITERAL) {
			Double n = (Double) current.value;
			current = scanner.next();
			return jpqlfactory.newDecimal(negative, n);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode booleanLiteral() {
		if (current.sym == FALSE) {
			current = scanner.next();
			return jpqlfactory.newBoolean(false);
		} else if (current.sym == TRUE) {
			current = scanner.next();
			return jpqlfactory.newBoolean(true);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode stringLiteral() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return jpqlfactory.newString(s);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode stringLiteral2() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode dateLiteral() {
		if (current.sym == DATE) {
			Object d = current.value;
			current = scanner.next();
			return jpqlfactory.newDate(d);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode timeLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	private JpaTreeNode timestampLiteral() {
		if (current.sym == TIMESTAMP) {
			Object d = current.value;
			current = scanner.next();
			return jpqlfactory.newDate(d);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode inputParameter() {
		if (current.sym == QUESTION) {
			return positionalInputParameter();
		}
		return namedInputParameter();
	}

	private JpaTreeNode namedInputParameter() {
		if (current.sym == COLON) {
			current = scanner.next();
			return pathComponent();
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode collectionValuedInputParameter() {
		if (current.sym == QUESTION) {
			return positionalInputParameter();
		}
		return namedInputParameter();
	}

	private JpaTreeNode positionalInputParameter() {
		if (current.sym == QUESTION) {
			current = scanner.next();
			if (current.sym == INTEGER_LITERAL) {
				String n = "" + current.value + "";
				return jpqlfactory.newNumber(n);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode patternValue() {
		JpaTreeNode value = null;
		int key = current.sym;
		switch (key) {
		case QUESTION:
			current = scanner.next();
			value = positionalInputParameter();
			break;
		case COLON:
			current = scanner.next();
			value = namedInputParameter();
			break;
		case STRING_LITERAL:
			current = scanner.next();
			value = stringLiteral();
			break;
		default:
			current = scanner.next();
			value = stringLiteral2();
			break;
		}
		if (current.sym == ESCAPE) {
			current = scanner.next();
			JpaTreeNode esc = escapeCharacter();
			return jpqlfactory.newPattern(value, esc);
		}
		return jpqlfactory.newPattern(value);
	}

	private JpaTreeNode escapeCharacter() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return jpqlfactory.newEscapeCharacter(s);
		} else {
			throw syntaxError();
		}
	}

	private JpaTreeNode trimCharacter() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return jpqlfactory.newTrimCharacter(s);
		} else {
			throw syntaxError();
		}
	}

}
