package org.logicware.database.jpa.criteria;

import java.util.Map;
import java.util.Set;

import javax.persistence.criteria.Expression;
import javax.persistence.metamodel.Metamodel;

public class JpaKeys<K> extends JpaExpression<Set<K>> implements Expression<Set<K>> {

	private final Map<K, ?> map;

	public JpaKeys(String alias, Class<? extends Set<K>> javaType, Expression<?> expression, Metamodel metamodel,
			Map<K, ?> map) {
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
		JpaKeys<?> other = (JpaKeys<?>) obj;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map)) {
			return false;
		}
		return true;
	}

}
