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
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Persistent Calendar class implementation for persistence. {@link Locale} and
 * {@link TimeZone} are not persistent. By this reason the {@link Calendar}
 * obtained from this class use the default locale and time zone.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class PrologCalendar extends AbstractTemporal implements Serializable {

	private final boolean lenient;
	private final int firstDayOfWeek;
	private final int minimalDaysInFirstWeek;

	private static final long serialVersionUID = -1810532253972577393L;

	PrologCalendar() {
		super(Calendar.getInstance().getTimeInMillis());
		Calendar c = Calendar.getInstance();
		minimalDaysInFirstWeek = c.getMinimalDaysInFirstWeek();
		firstDayOfWeek = c.getFirstDayOfWeek();
		lenient = c.isLenient();
	}

	PrologCalendar(boolean lenient, long timeInMillis, int firstDayOfWeek, int minimalDaysInFirstWeek) {
		super(timeInMillis);
		this.lenient = lenient;
		this.firstDayOfWeek = firstDayOfWeek;
		this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
	}

	final long getTimeInMillis() {
		return getTime();
	}

	final boolean isLenient() {
		return lenient;
	}

	final int getFirstDayOfWeek() {
		return firstDayOfWeek;
	}

	final int getMinimalDaysInFirstWeek() {
		return minimalDaysInFirstWeek;
	}

	@Override
	public String toString() {
		return "" + getJavaUtilCalendar() + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstDayOfWeek;
		result = prime * result + (lenient ? 1231 : 1237);
		result = prime * result + minimalDaysInFirstWeek;
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
		PrologCalendar other = (PrologCalendar) obj;
		if (firstDayOfWeek != other.firstDayOfWeek)
			return false;
		if (lenient != other.lenient)
			return false;
		return minimalDaysInFirstWeek == other.minimalDaysInFirstWeek;
	}

	public Calendar getJavaUtilCalendar() {
		Calendar c = Calendar.getInstance();
		c.setMinimalDaysInFirstWeek(getMinimalDaysInFirstWeek());
		c.setFirstDayOfWeek(getFirstDayOfWeek());
		c.setTimeInMillis(getTime());
		c.setLenient(isLenient());
		return c;
	}

}
