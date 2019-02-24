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
import java.util.Locale;

final class PrologLocale implements Serializable {

	private static final long serialVersionUID = 7975409476769982713L;
	private static final transient Locale locale = Locale.getDefault();

	private final String language;
	private final String country;
	private final String variant;

	PrologLocale() {
		this.language = locale.getLanguage();
		this.country = locale.getCountry();
		this.variant = locale.getVariant();
	}

	PrologLocale(String language) {
		this(language, "", "");
	}

	PrologLocale(String language, String country) {
		this(language, country, "");
	}

	PrologLocale(String language, String country, String variant) {
		this.language = language;
		this.country = country;
		this.variant = variant;
	}

	final Locale getJavaUtilLocale() {
		return new Locale(language, country, variant);
	}

	final String getLanguage() {
		return language;
	}

	final String getCountry() {
		return country;
	}

	final String getVariant() {
		return variant;
	}

	@Override
	public String toString() {
		return "" + getJavaUtilLocale() + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((variant == null) ? 0 : variant.hashCode());
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
		PrologLocale other = (PrologLocale) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (variant == null) {
			if (other.variant != null)
				return false;
		} else if (!variant.equals(other.variant))
			return false;
		return true;
	}

}
