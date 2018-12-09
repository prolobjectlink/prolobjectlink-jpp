package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaDiv<X> extends JpaArithmeticExpression<X> implements Expression<X> {

	public JpaDiv(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, "/", right, metamodel);
	}

}
