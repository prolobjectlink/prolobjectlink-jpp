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
