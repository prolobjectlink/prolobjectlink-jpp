/*
 * #%L
 * prolobjectlink-jpi
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.logicware.pdb.prolog;

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
