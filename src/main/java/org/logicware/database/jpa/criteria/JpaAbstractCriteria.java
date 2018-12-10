package org.logicware.database.jpa.criteria;

import javax.persistence.PersistenceException;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;

import org.logicware.AbstractWrapper;

public class JpaAbstractCriteria extends AbstractWrapper implements CommonAbstractCriteria {

	protected Expression<Boolean> restriction;

	public JpaAbstractCriteria(Predicate restriction) {
		this.restriction = restriction;
	}

	public JpaAbstractCriteria(Expression<Boolean> restriction) {
		this.restriction = restriction;
	}

	public <U> Subquery<U> subquery(Class<U> type) {
		return new JpaSubQuery<U>(restriction, type);
	}

	public Predicate getRestriction() {
		if (!(restriction instanceof Predicate)) {
			throw new PersistenceException("Imposible unwrap " + restriction.getClass() + "to" + Predicate.class);
		}
		return (Predicate) restriction;
	}

}
