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

public abstract class AbstractTemporal implements PrologTemporal {

	private static final long serialVersionUID = 7214449729023087068L;
	private long time = System.currentTimeMillis();

	protected AbstractTemporal() {
	}

	protected AbstractTemporal(long time) {
		this.time = time;
	}

	public final long getTime() {
		return time;
	}

	final boolean before(PrologTemporal time) {
		return compareTo(time) < 0;
	}

	final boolean after(PrologTemporal dateTime) {
		return compareTo(dateTime) > 0;
	}

	public final int compareTo(PrologTemporal o) {
		int k = getTime() > o.getTime() ? 1 : 0;
		return getTime() < o.getTime() ? -1 : k;
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
		AbstractTemporal other = (AbstractTemporal) obj;
		return time == other.time;
	}

}
