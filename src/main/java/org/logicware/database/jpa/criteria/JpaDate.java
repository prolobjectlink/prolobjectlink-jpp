package org.logicware.database.jpa.criteria;

import java.util.Date;

import javax.persistence.criteria.Expression;

public class JpaDate extends JpaExpression<Date> implements Expression<Date> {

	protected final Date date;

	public JpaDate(Date date) {
		super("", Date.class, null, null);
		this.date = date;
	}

	public JpaDate(long currentTimeMillis) {
		this(new Date(currentTimeMillis));
	}

	@Override
	public String toString() {
		return "" + date + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		JpaDate other = (JpaDate) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date)) {
			return false;
		}
		return true;
	}

}
