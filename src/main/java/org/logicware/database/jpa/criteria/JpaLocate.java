package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaLocate<X> extends JpaFunctionExpression<X> implements Expression<X> {

	protected final Expression<?> length;

	public JpaLocate(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from,
			Metamodel metamodel) {
		this(alias, javaType, x, from, null, metamodel);
	}

	public JpaLocate(String alias, Class<? extends X> javaType, Expression<?> x, Expression<?> from,
			Expression<?> len, Metamodel metamodel) {
		super(alias, javaType, x, "SUBSTRING", from, metamodel);
		this.length = len;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((length == null) ? 0 : length.hashCode());
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
		JpaLocate<X> other = (JpaLocate<X>) obj;
		if (length == null) {
			if (other.length != null)
				return false;
		} else if (!length.equals(other.length)) {
			return false;
		}
		return true;
	}

}
