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
package org.logicware.database.container;

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
