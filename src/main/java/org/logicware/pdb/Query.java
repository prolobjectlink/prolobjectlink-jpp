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
package org.logicware.pdb;

import java.util.Comparator;
import java.util.List;

/**
 * 
 * @see TypedQuery
 * @see ConstraintQuery
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Query extends TypedQuery<Object> {

	/**
	 * Return the maximum number of solutions to retrieve by the formulated
	 * query
	 * 
	 * @return maximum number of solutions
	 * @since 1.0
	 */
	public int getMaxSolution();

	/**
	 * Fix the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @param maxSolution
	 *            maximum number of solutions
	 * @return current instance of {@code TypedQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public Query setMaxSolution(int maxSolution);

	/**
	 * Return the fixed position for the first solution to retrieve
	 * 
	 * @return position for the first solution
	 * @since 1.0
	 */
	public int getFirstSolution();

	/**
	 * Fix the position of the first solution to retrieve.
	 * 
	 * @param firstSolution
	 *            position of the first solution
	 * @return current instance of {@code TypedQuery} with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public Query setFirstSolution(int firstSolution);

	/**
	 * @throws NonSolutionError
	 * 
	 */
	public Object getSolution() throws NonSolutionError;

	/**
	 * 
	 */
	public List<Object> getSolutions();

	/**
	 * 
	 */
	public Query orderAscending();

	/**
	 * 
	 */
	public Query orderDescending();

	/**
	 * 
	 */
	public Query orderBy(Comparator<Object> comparator);

	/**
	 * 
	 */
	public Query descend(String name);

	/**
	 * Release all allocations and references for the current object
	 * 
	 * @since 1.0
	 */
	public void dispose();
}
