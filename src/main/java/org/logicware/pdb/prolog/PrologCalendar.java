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
package org.logicware.pdb.prolog;

import java.io.Serializable;
import java.util.Arrays;
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
public class PrologCalendar implements Serializable {

	private final long timeInMillis;
	private final int[] fields;
	private final String calendarType;
	private final boolean lenient;
	private final int firstDayOfWeek;
	private final int minimalDaysInFirstWeek;

	private static final Builder builder = new Calendar.Builder();
	private static final long serialVersionUID = 2227564537660293384L;

	PrologCalendar() {
		Calendar c = Calendar.getInstance();
		minimalDaysInFirstWeek = c.getMinimalDaysInFirstWeek();
		firstDayOfWeek = c.getFirstDayOfWeek();
		fields = new int[Calendar.FIELD_COUNT];
		for (int i = 0; i < fields.length; i++) {
			fields[i] = c.get(i);
		}
		calendarType = c.getCalendarType();
		timeInMillis = c.getTimeInMillis();
		lenient = c.isLenient();
	}

	PrologCalendar(long timeInMillis, int[] fields, String calendarType, boolean lenient, int firstDayOfWeek,
			int minimalDaysInFirstWeek) {
		this.timeInMillis = timeInMillis;
		this.fields = fields;
		this.calendarType = calendarType;
		this.lenient = lenient;
		this.firstDayOfWeek = firstDayOfWeek;
		this.minimalDaysInFirstWeek = minimalDaysInFirstWeek;
	}

	final Calendar getJavaUtilCalendar() {
		builder.setWeekDefinition(firstDayOfWeek, minimalDaysInFirstWeek);
		builder.setCalendarType(calendarType);
		builder.setInstant(timeInMillis);
		builder.setLenient(lenient);
		builder.setFields(fields);
		return builder.build();
	}

	final long getTimeInMillis() {
		return timeInMillis;
	}

	final int[] getFields() {
		return fields;
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
		result = prime * result + Arrays.hashCode(fields);
		result = prime * result + firstDayOfWeek;
		result = prime * result + (lenient ? 1231 : 1237);
		result = prime * result + minimalDaysInFirstWeek;
		result = prime * result + (int) (timeInMillis ^ (timeInMillis >>> 32));
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
		if (!Arrays.equals(fields, other.fields))
			return false;
		if (firstDayOfWeek != other.firstDayOfWeek)
			return false;
		if (lenient != other.lenient)
			return false;
		if (minimalDaysInFirstWeek != other.minimalDaysInFirstWeek)
			return false;
		return timeInMillis == other.timeInMillis;
	}

}
