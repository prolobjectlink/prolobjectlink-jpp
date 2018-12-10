package org.logicware.database.jpa.criteria;

import java.util.Collection;
import java.util.Map;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaValues<V> extends JpaExpression<Collection<V>> implements Expression<Collection<V>> {

	private final Map<?, V> map;

	public JpaValues(String alias, Class<? extends Collection<V>> javaType, Expression<?> expression,
			Metamodel metamodel, Map<?, V> map) {
		super(alias, javaType, expression, metamodel);
		this.map = map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		JpaValues<V> other = (JpaValues<V>) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map)) {
			return false;
		}
		return true;
	}

}
