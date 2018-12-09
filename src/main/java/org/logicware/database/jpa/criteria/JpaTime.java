package org.logicware.database.jpa.criteria;

import java.sql.Time;

import javax.persistence.criteria.Expression;

public class JpaTime extends JpaExpression<Time> implements Expression<Time> {

	protected final Time time;

	public JpaTime(Time time) {
		super("", Time.class, null, null);
		this.time = time;
	}

	public JpaTime(long currentTimeMillis) {
		this(new Time(currentTimeMillis));
	}

	@Override
	public String toString() {
		return "" + time + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((time == null) ? 0 : time.hashCode());
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
		JpaTime other = (JpaTime) obj;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time)) {
			return false;
		}
		return true;
	}

}
