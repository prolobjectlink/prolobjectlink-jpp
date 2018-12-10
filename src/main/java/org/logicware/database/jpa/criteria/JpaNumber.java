package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;

public abstract class JpaNumber<N extends Number> extends JpaExpression<N> implements Expression<N> {

	protected final N number;

	public JpaNumber(N value, Class<? extends Number> javaType) {
		super("", (Class<? extends N>) javaType, null, null);
		this.number = value;
	}

	@Override
	public String toString() {
		return "" + number + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((number == null) ? 0 : number.hashCode());
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
		JpaNumber<?> other = (JpaNumber<?>) obj;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number)) {
			return false;
		}
		return true;
	}

}
