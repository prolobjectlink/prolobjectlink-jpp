package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

public final class JpaCriteriaDelete<T> extends JpaAbstractCriteria implements CriteriaDelete<T> {

	public JpaCriteriaDelete(Predicate restriction) {
		super(restriction);
	}

	public Root<T> from(Class<T> entityClass) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> from(EntityType<T> entity) {
		// TODO Auto-generated method stub
		return null;
	}

	public Root<T> getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaDelete<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaDelete<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

}
