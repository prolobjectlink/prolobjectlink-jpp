package org.logicware.database.querylang.ast;

import java.util.Date;

import org.logicware.database.querylang.AbstractDate;
import org.logicware.database.querylang.SymbolTable;
import org.logicware.database.querylang.TreeNode;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryDate extends AbstractDate implements TreeNode {

	public QueryDate(Date date) {
		super(date);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "'" + date + "'";
	}

}
