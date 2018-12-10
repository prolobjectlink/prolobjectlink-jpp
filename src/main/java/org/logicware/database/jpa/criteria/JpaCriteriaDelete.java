package org.logicware.database.jpa.criteria;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

public final class JpaCriteriaDelete<T> extends JpaAbstractCriteria implements CriteriaDelete<T> {

	private Root<T> root;
	private Class<T> targetEntity;
	private final Metamodel metamodel;
	private final List<Predicate> predicates = new LinkedList<Predicate>();

	public JpaCriteriaDelete(Class<T> targetEntity, Metamodel metamodel) {
		this(targetEntity, null, metamodel);
	}

	public JpaCriteriaDelete(Predicate restriction, Metamodel metamodel) {
		this(null, restriction, metamodel);
		predicates.add(restriction);
	}

	public JpaCriteriaDelete(Class<T> targetEntity, Predicate restriction, Metamodel metamodel) {
		super(restriction);
		this.targetEntity = targetEntity;
		this.metamodel = metamodel;
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
		return root;
	}

	public CriteriaDelete<T> where(Expression<Boolean> restriction) {
		this.restriction = restriction;
		return this;
	}

	public CriteriaDelete<T> where(Predicate... restrictions) {
		for (Predicate predicate : restrictions) {
			predicates.add(predicate);
		}
		return this;
	}

}
