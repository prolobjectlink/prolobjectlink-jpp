package org.logicware.database.querylang.ast;

import org.logicware.database.querylang.AbstractStr;
import org.logicware.database.querylang.Str;
import org.logicware.database.querylang.SymbolTable;

public class QueryStr extends AbstractStr implements Str {

	public QueryStr(String str) {
		super(str);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "'" + str + "'";
	}

}
