/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.prolog;

import java.io.Serializable;
import java.util.Date;

class PrologDate implements Serializable, Comparable<PrologDate> {

	private static final long serialVersionUID = 3615154738795726113L;
	private long time = System.currentTimeMillis();

	public PrologDate() {
	}

	PrologDate(long time) {
		this.time = time;
	}

	long getTime() {
		return time;
	}

	Date getJavaUtilDate() {
		return new Date(time);
	}

	boolean before(PrologDate dateTime) {
		return compareTo(dateTime) < 0;
	}

	boolean after(PrologDate dateTime) {
		return compareTo(dateTime) > 0;
	}

	public int compareTo(PrologDate o) {
		int t = time > o.time ? 1 : 0;
		return time < o.time ? -1 : t;
	}

	@Override
	public String toString() {
		return "" + getJavaUtilDate() + "";
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
		PrologDate other = (PrologDate) obj;
		return time == other.time;
	}

}
