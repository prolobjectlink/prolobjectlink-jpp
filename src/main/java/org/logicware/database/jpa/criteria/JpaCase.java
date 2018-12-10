package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Case;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaCase<R> extends JpaExpression<R> implements Case<R> {

	public JpaCase(String alias, Class<? extends R> javaType, Metamodel metamodel) {
		super(alias, javaType, null, metamodel);
	}

	public Case<R> when(Expression<Boolean> condition, R result) {
		// TODO Auto-generated method stub
		return null;
	}

	public Case<R> when(Expression<Boolean> condition, Expression<? extends R> result) {
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
