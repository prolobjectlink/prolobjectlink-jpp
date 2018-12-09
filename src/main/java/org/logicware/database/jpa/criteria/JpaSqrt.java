package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public final class JpaSqrt<X> extends JpaExpression<X> {

	public JpaSqrt(String alias, Class<? extends X> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

}
