package org.logicware.database.jpa.criteria;

import java.util.Arrays;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaFunction<X> extends JpaExpression<X> implements Expression<X> {

	private final Expression<?>[] arguments;

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?>[] arguments, Metamodel metamodel) {
		this(alias, javaType, null, arguments, metamodel);
	}

	public JpaFunction(String alias, Class<? extends X> javaType, Expression<?> expression, Expression<?>[] arguments,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
		this.arguments = arguments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(arguments);
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
		JpaFunction<X> other = (JpaFunction<X>) obj;
		return Arrays.equals(arguments, other.arguments);
	}

}
