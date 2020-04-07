/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.prolog;

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
