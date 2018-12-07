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
import org.logicware.database.querylang.Parser;
import org.logicware.database.querylang.Scanner;
import org.logicware.database.querylang.SymbolEntry;
import org.logicware.database.querylang.TreeNode;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JpqlParser extends JpqlSymbols implements Parser {

	private SymbolEntry current;
	private final Scanner scanner;

	public JpqlParser(Scanner scanner) {
		this.scanner = scanner;
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

	private TreeNode orderByClause() {
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

	private TreeNode subQueryFromItem() {
		// TODO Auto-generated method stub
		return null;
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

	private TreeNode whereClause() {
		// TODO Auto-generated method stub
		return null;
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

	private TreeNode arithmeticPrimary() {
		// TODO Auto-generated method stub
		return null;
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

	private TreeNode path() {
		TreeNode id = identificationVariable();
		TreeNode rest = pathComponent();
		return JpqlFactory.newPath(id, rest);
	}

	private TreeNode identificationVariableDeclaration() {
		TreeNode id = fromItem();
//		(LOOKAHEAD(fetch_join()) fetch_join() | LOOKAHEAD(inner_join()) inner_join() | LOOKAHEAD(outer_join()) outer_join())*
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

	private TreeNode identificationVariable() {
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newIdentifier(id);
		} else {
			throw syntaxError();
		}
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

	private TreeNode aggregatePath() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode distinctPath() {
		// TODO Auto-generated method stub
		return null;
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

	private TreeNode count() {
		current = scanner.next();
		TreeNode path = aggregatePath();
		return JpqlFactory.newCOUNT(path);
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

	private TreeNode abstractSchemaName() {
		List<TreeNode> l = newList();
		l.add(pathComponent());
		while (current.sym == DOT) {
			current = scanner.next();
			l.add(pathComponent());
		}
		return JpqlFactory.newAbstractSchema(l);
	}

	private TreeNode pathComponent() {
		current = scanner.next();
//		t = <NEW>
//		| t = <ALL>
//		| t = <ANY>
//		| t = <EXISTS>
//		| t = <SOME>
//		| t = <EMPTY>
//		| t = <ASC>
//		| t = <DESC>
//		| t = <ORDER>
//		| t = <IS>
//		| t = <MEMBER>
//		| t = <OF>
//		| t = <LIKE>
//		| t = <ESCAPE>
//		| t = <BETWEEN>
//		| t = <NULL>
//		| t = <AVG>
//		| t = <MIN>
//		| t = <MAX>
//		| t = <SUM>
//		| t = <COUNT>
//		| t = <OR>
//		| t = <AND>
//		| t = <NOT>
//		| t = <CONCAT>
//		| t = <SUBSTRING>
//		| t = <TRIM>
//		| t = <LOWER>
//		| t = <UPPER>
//		| t = <LEADING>
//		| t = <TRAILING>
//		| t = <BOTH>
//		| t = <LENGTH>
//		| t = <LOCATE>
//		| t = <ABS>
//		| t = <SQRT>
//		| t = <MOD>
//		| t = <SIZE>
//		| t = <CURRENT_DATE>
//		| t = <CURRENT_TIME>
//		| t = <CURRENT_TIMESTAMP>
//		| t = <SELECT>
//		| t = <DISTINCT>
//		| t = <FROM>
//		| t = <UPDATE>
//		| t = <DELETE>
//		| t = <WHERE>
//		| t = <GROUP>
//		| t = <BY>
//		| t = <HAVING>
//		| t = <AS>
//		| t = <LEFT>
//		| t = <OUTER>
//		| t = <INNER>
//		| t = <JOIN>
//		| t = <FETCH>
//		| t = <IN>
//		| t = <SET>
//		| t = <OBJECT>

//		| t = <IDENTIFIER>
		if (current.sym == IDENTIFIER) {
			String id = "" + current.value + "";
			current = scanner.next();
			return JpqlFactory.newIdentifier(id);
		}

//		| t = <CASE>
//		| t = <COALESCE>
//		| t = <NULLIF>
//		| t = <WHEN>
//		| t = <THEN>
//		| t = <ELSE>
//		| t = <END>
//		| t = <KEY>
//		| t = <VALUE>
//		| t = <ENTRY>
//		| t = <INDEX>
//		| t = <TYPE>
//		| t = <CLASS>
		else {
			throw JpqlFactory.syntaxError(getClass(), current);
		}
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

	private TreeNode updateStatement() {
		TreeNode update = updateClause();
		if (current.sym == WHERE) {
			TreeNode where = whereClause();
			return JpqlFactory.newUpdate(update, where);
		}
		return update;
	}

	private TreeNode updateClause() {
		current = scanner.next();
		TreeNode from = fromItem();
		// TODO subquery_from_clause
		if (current.sym == SET) {
			TreeNode set = setClause();
		}
		return null;
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

	private TreeNode deleteStatement() {
		TreeNode delete = deleteClause();
		return null;
	}

	private TreeNode deleteClause() {
		// TODO Auto-generated method stub
		return null;
	}

	private TreeNode simpleEntityExpression() {
		if (current.sym == QUESTION || current.sym == COLON) {
			return inputParameter();
		}
		return identificationVariable();
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

	///////////////////////////////////////////////////////////////

	private List<TreeNode> newList() {
		return new LinkedList<TreeNode>();
	}

	private RuntimeError syntaxError() {
		return JpqlFactory.syntaxError(getClass(), current);
	}

}
