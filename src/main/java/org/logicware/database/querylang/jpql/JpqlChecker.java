package org.logicware.database.querylang.jpql;

import org.logicware.database.querylang.Parser;
import org.logicware.database.querylang.Scanner;
import org.logicware.database.querylang.SymbolEntry;

public abstract class JpqlChecker extends JpqlSymbols implements Parser {

	protected SymbolEntry current;
	protected final Scanner scanner;

	protected JpqlChecker(Scanner scanner) {
		this.scanner = scanner;
	}

	protected void advance() {
		current = scanner.next();
	}

}
