package org.logicware.database.querylang.ast;

import org.logicware.database.querylang.AbstractDate;
import org.logicware.database.querylang.Date;
import org.logicware.database.querylang.SymbolTable;

/**
*
* @author Jose Zalacain
* @since 1.0
*/
public class QueryDate extends AbstractDate implements Date {

	public QueryDate(Date date) {
		super(date);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "'" + date + "'";
	}

}
