package org.logicware.database.querylang;

public abstract class AbstractDateTime extends AbstractExp implements TreeNode {

	protected Date date;

	public AbstractDateTime(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "" + date + "";
	}

}
