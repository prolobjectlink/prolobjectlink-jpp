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

import java.util.LinkedList;
import java.util.List;

import org.worklogic.RuntimeError;
import org.worklogic.db.jpa.criteria.JpaTreeNode;
import org.worklogic.db.querylang.Parser;
import org.worklogic.db.querylang.Scanner;
import org.worklogic.db.querylang.SymbolEntry;

/** @author Jose Zalacain @since 1.0 */
public abstract class JpqlChecker extends JpqlSymbols implements Parser {

	protected SymbolEntry current;
	protected final Scanner scanner;
	protected final JpqlFactory jpqlfactory;

	public JpqlChecker(Scanner scanner, JpqlFactory jpqlfactory) {
		this.scanner = scanner;
		this.jpqlfactory = jpqlfactory;
	}

	protected RuntimeError syntaxError() {
		return jpqlfactory.syntaxError(getClass(), current);
	}

	protected List<JpaTreeNode> newList() {
		return new LinkedList<JpaTreeNode>();
	}

	protected void advance() {
		current = scanner.next();
	}

	public boolean hasNext() {
		return scanner.hasNext();
	}

	public SymbolEntry next() {
		return scanner.next();
	}

}
