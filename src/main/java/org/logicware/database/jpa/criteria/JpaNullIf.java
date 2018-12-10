package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaNullIf<X> extends JpaFunctionExpression<X> implements Expression<X> {

	public JpaNullIf(String alias, Class<? extends X> javaType, Expression<?> left, Expression<?> right,
			Metamodel metamodel) {
		super(alias, javaType, left, "CONCAT", right, metamodel);
	}

}
