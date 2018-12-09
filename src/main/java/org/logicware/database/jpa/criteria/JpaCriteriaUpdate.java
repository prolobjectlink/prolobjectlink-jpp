package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;

public final class JpaCriteriaUpdate<T> extends JpaAbstractCriteria implements CriteriaUpdate<T> {

	public JpaCriteriaUpdate(Predicate restriction) {
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

	public <Y, X extends Y> CriteriaUpdate<T> set(SingularAttribute<? super T, Y> attribute, X value) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> CriteriaUpdate<T> set(SingularAttribute<? super T, Y> attribute, Expression<? extends Y> value) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y, X extends Y> CriteriaUpdate<T> set(Path<Y> attribute, X value) {
		// TODO Auto-generated method stub
		return null;
	}

	public <Y> CriteriaUpdate<T> set(Path<Y> attribute, Expression<? extends Y> value) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaUpdate<T> set(String attributeName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaUpdate<T> where(Expression<Boolean> restriction) {
		// TODO Auto-generated method stub
		return null;
	}

	public CriteriaUpdate<T> where(Predicate... restrictions) {
		// TODO Auto-generated method stub
		return null;
	}

}
