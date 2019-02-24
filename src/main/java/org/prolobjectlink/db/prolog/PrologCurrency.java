/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.prolog;

import java.io.Serializable;
import java.util.Currency;
import java.util.Locale;

final class PrologCurrency implements Serializable {

	private final String code;

	private static final long serialVersionUID = 5609314716803501225L;
	private static final transient Locale locale = Locale.getDefault();
	private static final transient Currency currency = Currency.getInstance(locale);

	PrologCurrency() {
		this.code = currency.getCurrencyCode();
	}

	PrologCurrency(String code) {
		this.code = code;
	}

	final String getCurrencyCode() {
		return code;
	}

	final Currency getJavaUtilCurrency() {
		return Currency.getInstance(code);
	}

	@Override
	public String toString() {
		return "" + getJavaUtilCurrency() + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PrologCurrency other = (PrologCurrency) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
