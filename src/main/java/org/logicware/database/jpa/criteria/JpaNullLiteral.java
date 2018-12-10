package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaNullLiteral extends JpaExpression<Object> implements Expression<Object> {

	public JpaNullLiteral(String alias, Class<? extends Object> javaType, Expression<?> expression,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

}
