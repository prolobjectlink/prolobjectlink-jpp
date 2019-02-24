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
package org.prolobjectlink.db.prolog;

/** @author Jose Zalacain @since 1.0 */
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
