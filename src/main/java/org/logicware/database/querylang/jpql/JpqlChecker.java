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

import org.logicware.RuntimeError;
import org.logicware.database.querylang.Parser;
import org.logicware.database.querylang.Scanner;
import org.logicware.database.querylang.SymbolEntry;
import org.logicware.database.querylang.TreeNode;

public abstract class JpqlChecker extends JpqlSymbols implements Parser {

	protected SymbolEntry current;
	protected final Scanner scanner;

	protected JpqlChecker(Scanner scanner) {
		this.scanner = scanner;
	}

	protected RuntimeError syntaxError() {
		return JpqlFactory.syntaxError(getClass(), current);
	}

	protected List<TreeNode> newList() {
		return new LinkedList<TreeNode>();
	}

	protected void advance() {
		current = scanner.next();
	}

}