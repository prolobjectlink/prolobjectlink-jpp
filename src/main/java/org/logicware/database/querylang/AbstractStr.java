package org.logicware.database.querylang;

public abstract class AbstractStr extends AbstractExp implements Str {

	protected String str;

	public AbstractStr(String str) {
		this.str = str;
	}

	@Override
	public String toString() {
		return str;
	}

}
