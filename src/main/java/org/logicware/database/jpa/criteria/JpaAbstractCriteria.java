package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

public class JpaAbstractCriteria implements CommonAbstractCriteria {

	private final Predicate restriction;

	public JpaAbstractCriteria(Predicate restriction) {
		this.restriction = restriction;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		return new JpaSubQuery<U>(restriction, type);
	}

	public Predicate getRestriction() {
		return restriction;
	}

}
