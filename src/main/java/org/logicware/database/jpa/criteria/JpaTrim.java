package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaTrim<X> extends JpaFunctionExpression<X> implements Expression<X> {

	protected final Expression<?> character;

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Metamodel metamodel) {
		this(alias, javaType, x, null, null, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Trimspec trimSpec, Metamodel metamodel) {
		this(alias, javaType, x, trimSpec, null, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<String> x, Expression<Character> claracter,
			Metamodel metamodel) {
		this(alias, javaType, x, Trimspec.BOTH, claracter, metamodel);
	}

	public JpaTrim(String alias, Class<? extends X> javaType, Expression<?> x, Trimspec trimSpec,
			Expression<?> claracter, Metamodel metamodel) {
		super(alias, javaType, x, "SUBSTRING", new JpaTrimSpec(trimSpec), metamodel);
		this.character = claracter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((character == null) ? 0 : character.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JpaTrim<X> other = (JpaTrim<X>) obj;
		if (character == null) {
			if (other.character != null)
				return false;
		} else if (!character.equals(other.character)) {
			return false;
		}
		return true;
	}

}
