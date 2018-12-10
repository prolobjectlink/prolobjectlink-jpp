package org.logicware.database.jpa.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

public final class JpaIn<X> extends JpaPredicate implements In<X> {

	public JpaIn(String alias, Class<? extends Boolean> javaType, Expression<?> expression, Metamodel metamodel,
			BooleanOperator operator, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, operator, expressions);
	}

	public BooleanOperator getOperator() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isNegated() {
		// TODO Auto-generated method stub
		return false;
	}

	public List<Expression<Boolean>> getExpressions() {
		// TODO Auto-generated method stub
		return null;
	}

	public Predicate not() {
		// TODO Auto-generated method stub
		return null;
	}

	public Expression<X> getExpression() {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(X value) {
		// TODO Auto-generated method stub
		return null;
	}

	public In<X> value(Expression<? extends X> value) {
		// TODO Auto-generated method stub
		return null;
	}

}
