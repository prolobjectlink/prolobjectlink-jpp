package org.logicware.database.jpa.criteria;

import java.util.List;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.predicate.JpaComparablePredicate;

public class JpaGreaterThan extends JpaComparablePredicate implements Predicate {

	public JpaGreaterThan(String alias, Class<? extends Boolean> javaType,
			Expression<? extends Comparable<?>> expression, Metamodel metamodel, List<Expression<?>> expressions) {
		super(alias, javaType, expression, metamodel, expressions);
	}

}
