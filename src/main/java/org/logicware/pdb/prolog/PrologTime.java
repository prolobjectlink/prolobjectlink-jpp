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
import java.sql.Time;

public class PrologTime implements Serializable, Comparable<PrologTime> {

	private static final long serialVersionUID = 6887337378900675097L;
	private long time = System.currentTimeMillis();

	public PrologTime() {
	}

	PrologTime(long time) {
		this.time = time;
	}

	long getTime() {
		return time;
	}

	Time getJavaSqlTime() {
		return new Time(time);
	}

	boolean before(PrologTime time) {
		return compareTo(time) < 0;
	}

	boolean after(PrologTime dateTime) {
		return compareTo(dateTime) > 0;
	}

	public int compareTo(PrologTime o) {
		int k = time > o.time ? 1 : 0;
		return time < o.time ? -1 : k;
	}

	@Override
	public final String toString() {
		return "" + time + "";
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (time ^ (time >>> 32));
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
		PrologTime other = (PrologTime) obj;
		return time == other.time;
	}

}
