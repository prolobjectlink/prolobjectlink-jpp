package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public abstract class JpaArithmeticExpression<X> extends JpaExpression<X> implements Expression<X> {

	protected final String operator;
	protected final Expression<?> right;

	public JpaArithmeticExpression(String alias, Class<? extends X> javaType, Expression<?> left, String operator,
			Expression<?> right, Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.operator = operator;
		this.right = right;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		JpaArithmeticExpression<?> other = (JpaArithmeticExpression<?>) obj;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator)) {
			return false;
		}
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}
