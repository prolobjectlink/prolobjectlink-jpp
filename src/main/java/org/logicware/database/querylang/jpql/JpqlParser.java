/*
 * #%L
 * prolobjectlink-db
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

import org.logicware.database.querylang.Parser;
import org.logicware.database.querylang.Scanner;
import org.logicware.database.querylang.TreeNode;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JpqlParser extends JpqlChecker implements Parser {

	public JpqlParser(Scanner scanner) {
		super(scanner);
	}

	public TreeNode parseQuery() {
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

	private TreeNode selectStatement() {
		TreeNode where = null;
		TreeNode group = null;
		TreeNode having = null;
		TreeNode order = null;
		TreeNode select = selectClause();
		TreeNode from = fromClause();
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
		return JpqlFactory.newSelect(

				select, from, where, group, having, order

		);
	}

	private TreeNode updateStatement() {
		TreeNode update = updateClause();
		if (current.sym == WHERE) {
			TreeNode where = whereClause();
			return JpqlFactory.newUpdate(update, where);
		}
		return update;
	}

	private TreeNode deleteStatement() {
		if (current.sym == DELETE) {
			current = scanner.next();
			if (current.sym == FROM) {
				current = scanner.next();
				TreeNode f = fromItem();
				if (current.sym == WHERE) {
					current = scanner.next();
					TreeNode w = whereClause();
					return JpqlFactory.newDelete(f, w);
				}
				return JpqlFactory.newDelete(f);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode fromClause() {
		if (current.sym == FROM) {
			current = scanner.next();

			List<TreeNode> vars = new LinkedList<TreeNode>();
			TreeNode var = identificationVariableDeclaration();
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
			return JpqlFactory.newFromClause(vars);

		} else {
			throw JpqlFactory.syntaxError(getClass(), current);
		}
	}

	private TreeNode identificationVariableDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode fromItem() {
		TreeNode type = abstractSchemaName();
		if (current.sym == AS) {
			current = scanner.next();
			TreeNode var = identificationVariable();
			return JpqlFactory.newFromItem(type, var);
		}
		TreeNode var = identificationVariable();
		return JpqlFactory.newFromItem(var);
	}

	private TreeNode subQueryFromClause() {
		current = scanner.next();
		List<TreeNode> l = newList();
		l.add(subQueryFromItem());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(subQueryFromItem());
		}
		return JpqlFactory.newSubQueryFrom(l);
	}

	private TreeNode subselectIdentificationVariableDeclaration() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode subQueryFromItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode innerJoin() {
		if (current.sym == INNER) {
			current = scanner.next();
		}
		if (current.sym == JOIN) {
			current = scanner.next();
			TreeNode path = path();
			if (current.sym == AS) {
				current = scanner.next();
			}
			TreeNode var = identificationVariable();
			return JpqlFactory.newInnerJoin(path, var);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode collectionMemberDeclaration() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			if (current.sym == KEY || current.sym == VALUE) {
				TreeNode path = qualifiedPath();
				return JpqlFactory.newCollMemberDekl(path);
			} else {
				TreeNode path = path();
				return JpqlFactory.newCollMemberDekl(path);
			}
		}
		if (current.sym == RPAR) {
			current = scanner.next();
		}
		if (current.sym == AS) {
			current = scanner.next();
		}
		TreeNode var = identificationVariable();
		return JpqlFactory.newCollMemberDekl(var);
	}

	private TreeNode outerJoin() {
		if (current.sym == LEFT) {
			current = scanner.next();
			if (current.sym == OUTER) {
				current = scanner.next();
			}
			if (current.sym == JOIN) {
				current = scanner.next();
				TreeNode path = path();
				if (current.sym == AS) {
					current = scanner.next();
				}
				TreeNode var = identificationVariable();
				return JpqlFactory.newOuterJoin(path, var);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode fetchJoin() {
		TreeNode f = null;
		if (current.sym == LEFT) {
			f = outerFetchJoin();
		} else {
			f = innerFetchJoin();
		}
		return JpqlFactory.newFetchJoin(f);
	}

	private TreeNode outerFetchJoin() {
		if (current.sym == LEFT) {
			current = scanner.next();
			if (current.sym == OUTER) {
				current = scanner.next();
			}
			if (current.sym == JOIN) {
				current = scanner.next();
				if (current.sym == FETCH) {
					current = scanner.next();
					TreeNode p = path();
					return JpqlFactory.newOuterFetchJoin(p);
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

	private TreeNode innerFetchJoin() {
		if (current.sym == INNER) {
			current = scanner.next();
		}
		if (current.sym == JOIN) {
			current = scanner.next();
			if (current.sym == FETCH) {
				TreeNode path = path();
				return JpqlFactory.newInnerFetchJoin(path);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode path() {
		TreeNode id = identificationVariable();
		TreeNode rest = pathComponent();
		return JpqlFactory.newPath(id, rest);
	}

	private TreeNode updateClause() {
		current = scanner.next();
		TreeNode from = fromItem();
		if (current.sym == SET) {
			current = scanner.next();
			TreeNode set = setClause();
			return JpqlFactory.newUpdateClause(from, set);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode setClause() {
		List<TreeNode> l = newList();
		l.add(updateItem());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(updateItem());
		}
		return JpqlFactory.newSet(l);
	}

	private TreeNode updateItem() {
		TreeNode path = path();
		if (current.sym == EQ) {
			current = scanner.next();
			TreeNode value = newValue();
			return JpqlFactory.newUpdateItem(path, value);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode newValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode simpleEntityExpression() {
		if (current.sym == QUESTION || current.sym == COLON) {
			return inputParameter();
		}
		return identificationVariable();
	}

	private TreeNode selectClause() {
		TreeNode exp = null;
		current = scanner.next();
		if (current.sym == DISTINCT) {
			exp = selectExpressions();
		} else {
			exp = selectExpressions();
		}
		return exp;
	}

	private TreeNode simpleSelectClause() {
		TreeNode exp = null;
		current = scanner.next();
		if (current.sym == DISTINCT) {
			exp = selectExpressions();
		} else {
			exp = selectExpressions();
		}
		return exp;
	}

	private TreeNode selectExpressions() {
		List<TreeNode> l = newList();
		l.add(selectExpression());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(selectExpression());
		}
		return JpqlFactory.newExpressions(l);
	}

	private TreeNode selectExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode selectExtension() {
		TreeNode scalar = scalarFunction();
		return JpqlFactory.newSelectExtension(scalar);
	}

	private TreeNode subSelectExpressions() {
		List<TreeNode> l = newList();
		l.add(subSelectExpression());
		while (current.sym == COMMA) {
			current = scanner.next();
			l.add(subSelectExpression());
		}
		return JpqlFactory.newExpressions(l);
	}

	private TreeNode subSelectExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode constructorExpression() {
		if (current.sym == NEW) {
			current = scanner.next();
			TreeNode cn = className();
			TreeNode ps = constructorParameters();
			return JpqlFactory.newConstructorExpression(cn, ps);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode className() {
		List<TreeNode> l = newList();
		l.add(identificationVariable());
		while (current.sym == DOT) {
			current = scanner.next();
			l.add(identificationVariable());
		}
		return JpqlFactory.newClassName(l);
	}

	private TreeNode constructorParameters() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			List<TreeNode> l = newList();
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
			return JpqlFactory.newParameters(l);
		}
		return JpqlFactory.newParameters(newList());
	}

	private TreeNode constructorParameter() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode aggregateSelectExpression() {
		int key = current.sym;
		TreeNode aggregate = null;
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

	private TreeNode aggregatePath() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode distinctPath() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode count() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newCOUNT(path);
	}

	private TreeNode avg() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newAVG(path);
	}

	private TreeNode max() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newMAX(path);
	}

	private TreeNode min() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newMIN(path);
	}

	private TreeNode sum() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newSUM(path);
	}

	private TreeNode whereClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode groupByClause() {
		current = scanner.next();
		if (current.sym == BY) {
			current = scanner.next();
			List<TreeNode> l = newList();
			l.add(groupByItem());
			while (current.sym == COMMA) {
				current = scanner.next();
				l.add(groupByItem());
			}
			return JpqlFactory.newGroupBy(l);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode groupByItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode groupByExtension() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode havingClause() {
		current = scanner.next();
		TreeNode exp = conditionalExpression();
		return JpqlFactory.newHaving(exp);
	}

	private TreeNode subQuery() {
		TreeNode where = null;
		TreeNode group = null;
		TreeNode having = null;
		TreeNode select = simpleSelectClause();
		TreeNode from = subQueryFromClause();
		if (current.sym == WHERE) {
			where = whereClause();
		}
		if (current.sym == GROUP) {
			group = groupByClause();
		}
		if (current.sym == HAVING) {
			having = havingClause();
		}
		return JpqlFactory.newSelect(

				select, from, where, group, having

		);
	}

	private TreeNode conditionalExpression() {
		TreeNode term = conditionalTerm();
		current = scanner.next();
		if (current.sym == OR) {
			current = scanner.next();
			TreeNode exp = conditionalExpression();
			return JpqlFactory.newCondExp(term, exp);
		}
		return JpqlFactory.newCondExp(term);
	}

	private TreeNode conditionalTerm() {
		TreeNode factor = conditionalFactor();
		current = scanner.next();
		if (current.sym == AND) {
			current = scanner.next();
			TreeNode term = conditionalTerm();
			return JpqlFactory.newCondTerm(factor, term);
		}
		return JpqlFactory.newCondTerm(factor);
	}

	private TreeNode conditionalFactor() {
		current = scanner.next();
		if (current.sym == NOT) {
			current = scanner.next();
			TreeNode p = conditionalPrimary();
			return JpqlFactory.newCondFactor(true, p);
		}
		TreeNode p = conditionalPrimary();
		return JpqlFactory.newCondFactor(false, p);
	}

	private TreeNode conditionalPrimary() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			TreeNode e = conditionalExpression();
			if (current.sym == RPAR) {
				current = scanner.next();
				return JpqlFactory.newCondPrimary(e);
			}
		}
		TreeNode e = simpleCondExpression();
		return JpqlFactory.newCondPrimary(e);
	}

	private TreeNode simpleCondExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode betweenExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode inExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode entityTypeLiteral() {
		TreeNode v = identificationVariable();
		return JpqlFactory.newEntityTypeLiteral(v);
	}

	private TreeNode literalOrParam() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode likeExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode nullComparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode emptyCollectionComparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode collectionMemberExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode existsExpression() {
		if (current.sym == NOT && current.sym == EXISTS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode l = subQuery();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newExistsExpression(true, l);
				} else {
					throw syntaxError();
				}
			}
		} else if (current.sym == EXISTS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode l = subQuery();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newExistsExpression(false, l);
				} else {
					throw syntaxError();
				}
			}
		}
		throw syntaxError();
	}

	private TreeNode allOrAnyExpression() {
		int key = current.sym;
		TreeNode exp = null;
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
		return JpqlFactory.newAllOrAny(exp);
	}

	private TreeNode anyExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			TreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return JpqlFactory.newANY(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode someExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			TreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return JpqlFactory.newSOME(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode allExpression() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			TreeNode s = subQuery();
			if (current.sym == RPAR) {
				current = scanner.next();
				return JpqlFactory.newALL(s);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode comparisonExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode stringComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode booleanComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode enumComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode entityComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode arithmeticComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode datetimeComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode scalarFunction() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode arithmeticPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode arithmeticExpression() {
		TreeNode term = arithmeticTerm();
		current = scanner.next();
		if (current.sym == PLUS) {
			current = scanner.next();
			TreeNode exp = arithmeticExpression();
			return JpqlFactory.newArithExp(term, exp);
		}
		if (current.sym == MINUS) {
			current = scanner.next();
			TreeNode exp = arithmeticExpression();
			return JpqlFactory.newArithExp(term, exp);
		}
		return JpqlFactory.newArithExp(term);
	}

	private TreeNode arithmeticTerm() {
		TreeNode factor = arithmeticFactor();
		current = scanner.next();
		if (current.sym == TIMES) {
			current = scanner.next();
			TreeNode term = arithmeticTerm();
			return JpqlFactory.newArithTerm(factor, term);
		}
		if (current.sym == DIV) {
			current = scanner.next();
			TreeNode term = arithmeticTerm();
			return JpqlFactory.newArithTerm(factor, term);
		}
		return JpqlFactory.newArithTerm(factor);
	}

	private TreeNode arithmeticFactor() {
		current = scanner.next();
		if (current.sym == LPAR) {
			current = scanner.next();
			TreeNode exp = conditionalPrimary();
			return JpqlFactory.newArithFactor(exp);
		}
		TreeNode p = arithmeticPrimary();
		return JpqlFactory.newArithFactor(p);
	}

	private TreeNode qualifiedPath() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE) {
			TreeNode id = identificationVariable();
			return JpqlFactory.newQualifiedPath(id);
		} else if (current.sym == DOT) {
			List<TreeNode> l = newList();
			l.add(pathComponent());
			while (current.sym == DOT) {
				current = scanner.next();
				l.add(pathComponent());
			}
			return JpqlFactory.newQualifiedPath(l);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode qualifiedIdentificationVariable() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE || current.sym == ENTRY) {
			TreeNode id = identificationVariable();
			return JpqlFactory.newQualifiedId(id);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode generalIdentificationVariable() {
		current = scanner.next();
		if (current.sym == KEY || current.sym == VALUE) {
			TreeNode id = identificationVariable();
			return JpqlFactory.newQualifiedId(id);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode entityTypeComp() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode typeDiscriminator() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode entityTypeExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode scalarExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode caseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode generalCaseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode whenClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode simpleCaseExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode simpleWhenClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode coalesceExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode nullifExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode negative() {
		boolean negative = false;
		if (current.sym == MINUS) {
			current = scanner.next();
			negative = true;
		} else if (current.sym == PLUS) {
			current = scanner.next();
		}
		return JpqlFactory.newNegative(negative);
	}

	private TreeNode stringValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode stringExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode stringPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode datetimeExpression() {
		TreeNode s = null;
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
		return JpqlFactory.newDatetimeExpression(s);
	}

	private TreeNode datetimePrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode booleanValue() {
		TreeNode v = null;
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
		return JpqlFactory.newBooleanValue(v);
	}

	private TreeNode booleanExpression() {
		TreeNode s = null;
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
		return JpqlFactory.newBooleanExpression(s);
	}

	private TreeNode booleanPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode enumExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode enumPrimary() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode enumLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode entityBeanValue() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode entityBeanExpression() {
		int key = current.sym;
		TreeNode v = null;
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
		return JpqlFactory.newEntityBeanExpression(v);
	}

	private TreeNode functionsReturningStrings() {
		int key = current.sym;
		TreeNode exp = null;
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
		return JpqlFactory.newStringFunction(exp);
	}

	private TreeNode concat() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode substring() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode trim() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode lower() {
		if (current.sym == LOWER) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newLOWER(e);
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

	private TreeNode upper() {
		if (current.sym == UPPER) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newUPPER(e);
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

	private TreeNode trimSpecification() {
		int key = current.sym;
		TreeNode exp = null;
		switch (key) {
		case LEADING:
			exp = JpqlFactory.newLeadingSpec(exp);
			break;
		case TRAILING:
			exp = JpqlFactory.newTrailingSpec(exp);
			break;
		default:
			exp = JpqlFactory.newBothSpec(exp);
			break;
		}
		return JpqlFactory.newTrimSpecification(exp);
	}

	private TreeNode functionsReturningNumerics() {
		int key = current.sym;
		TreeNode exp = null;
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
		return JpqlFactory.newNumericFunction(exp);
	}

	private TreeNode length() {
		if (current.sym == LENGTH) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = stringExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newLENGTH(e);
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

	private TreeNode locate() {
		if (current.sym == LOCATE) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode s1 = stringExpression();
				if (current.sym == COMMA) {
					current = scanner.next();
					TreeNode s2 = stringExpression();
					if (current.sym == COMMA) {
						current = scanner.next();
						TreeNode exp = arithmeticExpression();
						if (current.sym == RPAR) {
							current = scanner.next();
						} else {
							throw syntaxError();
						}
						return JpqlFactory.newLOCATE(s1, s2, exp);
					}
					if (current.sym == RPAR) {
						current = scanner.next();
					} else {
						throw syntaxError();
					}
					return JpqlFactory.newLOCATE(s1, s2);
				} else {
					throw syntaxError();
				}
			} else {
				throw syntaxError();
			}
		}
		return null;
	}

	private TreeNode abs() {
		if (current.sym == ABS) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = arithmeticExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newABS(e);
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

	private TreeNode sqrt() {
		if (current.sym == SQRT) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = arithmeticExpression();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newSQRT(e);
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

	private TreeNode mod() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode size() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode index() {
		if (current.sym == INDEX) {
			current = scanner.next();
			if (current.sym == LPAR) {
				current = scanner.next();
				TreeNode e = identificationVariable();
				if (current.sym == RPAR) {
					current = scanner.next();
					return JpqlFactory.newINDEX(e);
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

	private TreeNode functionsReturningDatetime() {
		long time = System.currentTimeMillis();
		int key = current.sym;
		TreeNode exp = null;
		switch (key) {
		case CURRENT_TIMESTAMP:
			current = scanner.next();
			exp = JpqlFactory.newCurrentTimestamp(time);
			break;
		case CURRENT_TIME:
			current = scanner.next();
			exp = JpqlFactory.newCurrentTime(time);
			break;
		default:
			current = scanner.next();
			exp = JpqlFactory.newCurrentDate(time);
			break;
		}
		return JpqlFactory.newDateTimeFunction(exp);
	}

	private TreeNode orderByClause() {
		if (current.sym == ORDER) {
			current = scanner.next();
			if (current.sym == BY) {
				current = scanner.next();
				List<TreeNode> l = newList();
				l.add(orderByItem());
				while (current.sym == COMMA) {
					current = scanner.next();
					l.add(orderByItem());
				}
				return JpqlFactory.newOrderBy(l);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode orderByItem() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode orderbyExtension() {
		TreeNode agg = aggregateSelectExpression();
		return JpqlFactory.newOrderByExtension(agg);
	}

	private TreeNode abstractSchemaName() {
		List<TreeNode> l = newList();
		l.add(pathComponent());
		while (current.sym == DOT) {
			current = scanner.next();
			l.add(pathComponent());
		}
		return JpqlFactory.newAbstractSchema(l);
	}

	private TreeNode tok() {
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newIdentifier(id);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode identificationVariable() {
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newIdentifier(id);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode pathComponent() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode literal() {
		TreeNode l = null;
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
		return JpqlFactory.newLiteral(l);
	}

	private TreeNode numericLiteral() {
		TreeNode exp = null;
		if (current.sym == INTEGER_LITERAL) {
			current = scanner.next();
			exp = integerLiteral();
		} else {
			current = scanner.next();
			exp = decimalLiteral();
		}
		return JpqlFactory.newNumeric(exp);
	}

	private TreeNode integerLiteral() {
		TreeNode negative = negative();
		if (current.sym == INTEGER_LITERAL) {
			Integer n = (Integer) current.value;
			current = scanner.next();
			return JpqlFactory.newInteger(negative, n);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode decimalLiteral() {
		TreeNode negative = negative();
		if (current.sym == FLOATING_POINT_LITERAL) {
			Double n = (Double) current.value;
			current = scanner.next();
			return JpqlFactory.newDecimal(negative, n);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode booleanLiteral() {
		if (current.sym == FALSE) {
			current = scanner.next();
			return JpqlFactory.newBoolean(false);
		} else if (current.sym == TRUE) {
			current = scanner.next();
			return JpqlFactory.newBoolean(true);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode stringLiteral() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newString(s);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode stringLiteral2() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode dateLiteral() {
		if (current.sym == DATE) {
			Object d = current.value;
			current = scanner.next();
			return JpqlFactory.newDate(d);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode timeLiteral() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode timestampLiteral() {
		if (current.sym == TIMESTAMP) {
			Object d = current.value;
			current = scanner.next();
			return JpqlFactory.newDate(d);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode inputParameter() {
		if (current.sym == QUESTION) {
			return positionalInputParameter();
		}
		return namedInputParameter();
	}

	private TreeNode namedInputParameter() {
		if (current.sym == COLON) {
			current = scanner.next();
			return pathComponent();
		} else {
			throw syntaxError();
		}
	}

	private TreeNode collectionValuedInputParameter() {
		if (current.sym == QUESTION) {
			return positionalInputParameter();
		}
		return namedInputParameter();
	}

	private TreeNode positionalInputParameter() {
		if (current.sym == QUESTION) {
			current = scanner.next();
			if (current.sym == INTEGER_LITERAL) {
				String n = "" + current.value + "";
				return JpqlFactory.newNumber(n);
			} else {
				throw syntaxError();
			}
		} else {
			throw syntaxError();
		}
	}

	private TreeNode patternValue() {
		TreeNode value = null;
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
			TreeNode esc = escapeCharacter();
			return JpqlFactory.newPattern(value, esc);
		}
		return JpqlFactory.newPattern(value);
	}

	private TreeNode escapeCharacter() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newEscapeCharacter(s);
		} else {
			throw syntaxError();
		}
	}

	private TreeNode trimCharacter() {
		if (current.sym == STRING_LITERAL) {
			String s = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newTrimCharacter(s);
		} else {
			throw syntaxError();
		}
	}

}
