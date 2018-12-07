package org.logicware.database.querylang;

public abstract class AbstractDate extends AbstractExp implements TreeNode {

	protected Date date;

	public AbstractDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "" + date + "";
	}

}
