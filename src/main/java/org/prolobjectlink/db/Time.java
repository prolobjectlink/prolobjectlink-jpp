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
package org.prolobjectlink.db;

import java.io.Serializable;

public class Time implements Serializable, Comparable<Time> {

	private long t = System.currentTimeMillis();
	private static final long serialVersionUID = 6887337378900675097L;

	public Time() {
	}

	Time(long time) {
		this.t = time;
	}

	long getTime() {
		return t;
	}

	boolean before(Time time) {
		return compareTo(time) < 0;
	}

	boolean after(Time dateTime) {
		return compareTo(dateTime) > 0;
	}

	public int compareTo(Time o) {
		int k = t > o.t ? 1 : 0;
		return t < o.t ? -1 : k;
	}

	@Override
	public final String toString() {
		return "" + t + "";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (t ^ (t >>> 32));
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		return t == other.t;
	}

}
