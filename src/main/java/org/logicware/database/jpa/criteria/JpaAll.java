package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaAll<X> extends JpaExpression<X> implements Expression<X> {

	public JpaAll(String alias, Class<? extends X> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

}
