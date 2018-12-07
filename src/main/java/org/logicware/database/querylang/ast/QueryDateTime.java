package org.logicware.database.querylang.ast;

import org.logicware.database.querylang.AbstractDateTime;
import org.logicware.database.querylang.Date;
import org.logicware.database.querylang.DateTime;
import org.logicware.database.querylang.SymbolTable;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryDateTime extends AbstractDateTime implements DateTime {

	public QueryDateTime(Date date) {
		super(date);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "'" + date + "'";
	}

}
