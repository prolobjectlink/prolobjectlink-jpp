package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.SimpleCase;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaSimpleCase<C, R> extends JpaExpression<R> implements SimpleCase<C, R> {

	public JpaSimpleCase(String alias, Class<? extends R> javaType, Expression<?> expression, Metamodel metamodel) {
		super(alias, javaType, expression, metamodel);
	}

	public Expression<C> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public SimpleCase<C, R> when(C condition, Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<R> otherwise(Expression<? extends R> result) {
		// TODO Auto-generated method stub
		return null;
	}

}
