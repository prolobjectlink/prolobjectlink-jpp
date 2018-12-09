package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.Predicate;

import org.logicware.database.jpa.criteria.JPAPredicate;

public abstract class JpaBoolean extends JPAPredicate implements Predicate {

	protected final Boolean bool;

	public JpaBoolean(Boolean value) {
		super("", Boolean.class, null, null, null, null);
		this.bool = value;
	}

	@Override
	public String toString() {
		return "" + bool + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((bool == null) ? 0 : bool.hashCode());
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
		JpaBoolean other = (JpaBoolean) obj;
		if (bool == null) {
			if (other.bool != null)
				return false;
		} else if (!bool.equals(other.bool)) {
			return false;
		}
		return true;
	}

}
