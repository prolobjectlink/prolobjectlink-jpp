/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.db.prolog;

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
