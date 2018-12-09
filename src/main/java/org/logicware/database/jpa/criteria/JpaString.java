package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;

public class JpaString extends JpaExpression<String> implements Expression<String> {

	protected final String string;

	public JpaString(String string) {
		super("", String.class, null, null);
		this.string = string;
	}

	@Override
	public String toString() {
		return "" + string + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((string == null) ? 0 : string.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaString other = (JpaString) obj;
		if (string == null) {
			if (other.string != null)
				return false;
		} else if (!string.equals(other.string)) {
			return false;
		}
		return true;
	}

}
