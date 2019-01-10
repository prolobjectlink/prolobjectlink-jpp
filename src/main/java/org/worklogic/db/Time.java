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
package org.worklogic.db;

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
