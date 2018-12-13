package org.logicware.database.jpa.criteria.jpql;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import org.logicware.database.jpa.criteria.JpaCriteriaBuilder;

public class JpqlFactory extends JpaCriteriaBuilder implements CriteriaBuilder {

	public JpqlFactory(Metamodel metamodel) {
		super(metamodel);
	}

}
