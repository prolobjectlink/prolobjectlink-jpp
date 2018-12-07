/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.database.prolog;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Persistent Calendar class implementation based on the {@link Builder} fields
 * for persistence. {@link Locale} and {@link TimeZone} are not persistent. By
 * this reason the {@link Calendar} obtained from this class use the default
 * locale and time zone.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public class PrologCalendar extends AbstractTemporal implements Serializable {

	private final boolean lenient;
	private final String calendarType;
	private final int firstDayOfWeek;
	private final int minimalDaysInFirstWeek;

	private static final Builder builder = new Calendar.Builder();
	private static final long serialVersionUID = -1810532253972577393L;

	PrologCalendar() {
		super(Calendar.getInstance().getTimeInMillis());
		Calendar c = Calendar.getInstance();
		minimalDaysInFirstWeek = c.getMinimalDaysInFirstWeek();
		firstDayOfWeek = c.getFirstDayOfWeek();
		calendarType = c.getCalendarType();
		lenient = c.isLenient();
	}

	PrologCalendar(boolean lenient, long timeInMillis, String calendarType, int firstDayOfWeek,
			int minimalDaysInFirstWeek) {
		super(timeInMillis);
		this.lenient = lenient;
		this.calendarType = calendarType;
		this.firstDayOfWeek = firstDayOfWeek;
		this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
	}

	final long getTimeInMillis() {
		return getTime();
	}

	final String getCalendarType() {
		return calendarType;
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
		result = prime * result + ((calendarType == null) ? 0 : calendarType.hashCode());
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
		if (calendarType == null) {
			if (other.calendarType != null)
				return false;
		} else if (!calendarType.equals(other.calendarType))
			return false;
		if (firstDayOfWeek != other.firstDayOfWeek)
			return false;
		if (lenient != other.lenient)
			return false;
		return minimalDaysInFirstWeek == other.minimalDaysInFirstWeek;
	}

	public Calendar getJavaUtilCalendar() {
		builder.setWeekDefinition(firstDayOfWeek, minimalDaysInFirstWeek);
		builder.setCalendarType(calendarType);
		builder.setInstant(getTime());
		builder.setLenient(lenient);
		return builder.build();
	}

}
