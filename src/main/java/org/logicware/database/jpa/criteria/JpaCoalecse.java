package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Coalesce;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.util.JavaReflect;

public class JpaCoalecse<X> extends JpaExpression<X> implements Coalesce<X> {

	protected final Expression<?> right;

	public JpaCoalecse(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, metamodel);
		this.right = right;
	}

	public JpaCoalecse(String alias, Class<? extends X> javaType, Metamodel metamodel) {
		this(alias, javaType, null, null, metamodel);
	}

	public Coalesce<X> value(X value) {
		expression = new JpaObject<X>(value, JavaReflect.classOf(value));
		return this;
	}

	public Coalesce<X> value(Expression<? extends X> value) {
		expression = value;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		JpaCoalecse<?> other = (JpaCoalecse<?>) obj;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right)) {
			return false;
		}
		return true;
	}

}
