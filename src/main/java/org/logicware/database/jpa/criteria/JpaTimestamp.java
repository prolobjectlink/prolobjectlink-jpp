package org.logicware.database.jpa.criteria;

import java.sql.Timestamp;

import javax.persistence.criteria.Expression;

public class JpaTimestamp extends JpaExpression<Timestamp> implements Expression<Timestamp> {

	protected final Timestamp timestamp;

	public JpaTimestamp(Timestamp timestamp) {
		super("", Timestamp.class, null, null);
		this.timestamp = timestamp;
	}

	public JpaTimestamp(long currentTimeMillis) {
		this(new Timestamp(currentTimeMillis));
	}

	@Override
	public String toString() {
		return "" + timestamp + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		JpaTimestamp other = (JpaTimestamp) obj;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp)) {
			return false;
		}
		return true;
	}

}
