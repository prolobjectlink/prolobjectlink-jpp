package org.logicware.database.jpa.criteria;

public final class JpaFloat extends JpaNumber<Double> {

	public JpaFloat(Double value) {
		super(value, Double.class);
	}

}
