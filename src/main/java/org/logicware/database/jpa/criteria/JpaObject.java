package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;

public class JpaObject<X> extends JpaExpression<X> implements Expression<X> {

	protected final X object;

	public JpaObject(X x, Class<? extends X> javaType) {
		super("", javaType, null, null);
		this.object = x;
	}

	@Override
	public String toString() {
		return "" + object + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((object == null) ? 0 : object.hashCode());
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
		JpaObject other = (JpaObject) obj;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object)) {
			return false;
		}
		return true;
	}

}
