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
package org.worklogic.db.querylang.jpql;

import static org.worklogic.logging.LoggerConstants.SYNTAX_ERROR;

import java.io.StringReader;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;

import org.worklogic.RuntimeError;
import org.worklogic.db.jpa.criteria.JpaTreeNode;
import org.worklogic.db.querylang.SymbolTable;
import org.worklogic.logging.LoggerUtils;

/**
 * Compiler to compile from the Query Language to Prolog Native Query Language
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class JpqlCompiler {

	private final CriteriaBuilder builder;

	public JpqlCompiler(CriteriaBuilder builder) {
		this.builder = builder;
	}

	/**
	 * Compile the Query Language to Prolog Query Language
	 * 
	 * @param jpqlStatementQuery Query Language string
	 * @return Native Query Language string
	 * @since 1.0
	 */
	public String compile(Set<String> builtins, String jpqlStatementQuery) {
		return treeNode(builtins, jpqlStatementQuery).getQueryString();
	}

	/**
	 * Compile the Query Language and return the AST for given query
	 * 
	 * @param jpqlStatementQuery Query Language string
	 * @return Tree node query representation
	 * @since 1.0
	 */
	public JpaTreeNode treeNode(Set<String> builtins, String jpqlStatementQuery) {
		JpqlFactory jpqlFactory = new JpqlFactory(builder);
		SymbolTable symbolTable = new SymbolTable(builtins);
		StringReader jpqlReader = new StringReader(jpqlStatementQuery);
		JpqlScanner scanner = new JpqlScanner(jpqlReader, symbolTable);
		JpqlParser parser = new JpqlParser(scanner, jpqlFactory);
		try {
			return parser.parseQuery();
		} catch (RuntimeError e) {
			LoggerUtils.error(JpqlCompiler.class, SYNTAX_ERROR, e);
		} catch (Exception e) {
			LoggerUtils.error(JpqlCompiler.class, SYNTAX_ERROR, e);
		}
		throw new RuntimeError("Syntax error");
	}

}
