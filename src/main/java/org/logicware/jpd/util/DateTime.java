/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd.util;

public final class DateTime implements Comparable<DateTime> {

	private long time;

	public DateTime() {
		this(System.currentTimeMillis());
	}

	public DateTime(long time) {
		this.time = time;
	}

	public long getTime() {
		return time;
	}

	public java.util.Date getJavaUtilDate() {
		return new java.util.Date(time);
	}

	public boolean before(DateTime dateTime) {
		return compareTo(dateTime) < 0;
	}

	public boolean after(DateTime dateTime) {
		return compareTo(dateTime) > 0;
	}

	public int compareTo(DateTime o) {
		int t = time > o.time ? 1 : 0;
		return time < o.time ? -1 : t;
	}

	@Override
	public String toString() {
		return getJavaUtilDate().toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
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
		DateTime other = (DateTime) obj;
		if (time != other.time)
			return false;
		return true;
	}

}
