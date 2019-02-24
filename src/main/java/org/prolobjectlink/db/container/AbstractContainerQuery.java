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
package org.prolobjectlink.db.container;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractContainerQuery {

	/** Greatest possible solutions number to achieve */
	protected static final int MAX = Integer.MAX_VALUE;

	/** Fix maximum of result number */
	protected int maxSolution = MAX;

	/** Fix the first position of result */
	protected int firstSolution = 0;

	public final int getFirstSolution() {
		return firstSolution;
	}

	public final int getMaxSolution() {
		return maxSolution;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + firstSolution;
		result = prime * result + maxSolution;
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
		AbstractContainerQuery other = (AbstractContainerQuery) obj;
		if (firstSolution != other.firstSolution)
			return false;
		return maxSolution == other.maxSolution;
	}

	protected final void checkSolutionAt(int position, int size) {
		if (position < 0 || position > size) {
			throw new ArrayIndexOutOfBoundsException(position);
		}
	}

	public abstract void remove();

}
