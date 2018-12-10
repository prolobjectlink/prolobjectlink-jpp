package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.metamodel.Metamodel;

public class JpaParameterExpression<X> extends JpaExpression<X> implements ParameterExpression<X> {

	public JpaParameterExpression(String alias, Class<? extends X> javaType, Expression<?> expression,
			Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

	public String getName() {
		return alias;
	}

	public Integer getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	public Class<X> getParameterType() {
		return (Class<X>) javaType;
	}

}
