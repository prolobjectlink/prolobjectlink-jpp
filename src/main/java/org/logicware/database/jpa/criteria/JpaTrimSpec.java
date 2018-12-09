package org.logicware.database.jpa.criteria;

import javax.persistence.criteria.CriteriaBuilder.Trimspec;
import javax.persistence.criteria.Expression;

public class JpaTrimSpec extends JpaExpression<Trimspec> implements Expression<Trimspec> {

	protected final Trimspec specification;

	public JpaTrimSpec(Trimspec specification) {
		super("", Trimspec.class, null, null);
		this.specification = specification;
	}

	@Override
	public String toString() {
		return "" + specification + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((specification == null) ? 0 : specification.hashCode());
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
		JpaTrimSpec other = (JpaTrimSpec) obj;
		if (specification == null) {
			if (other.specification != null)
				return false;
		} else if (!specification.equals(other.specification)) {
			return false;
		}
		return true;
	}

}
