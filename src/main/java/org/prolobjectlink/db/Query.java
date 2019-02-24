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

import java.util.Comparator;
import java.util.List;

/**
 * 
 * @see TypedQuery
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Query extends TypedQuery<Object> {

	/**
	 * Return the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @return maximum number of solutions
	 * @since 1.0
	 */
	public int getMaxSolution();

	/**
	 * Fix the maximum number of solutions to retrieve by the formulated query
	 * 
	 * @param maxSolution maximum number of solutions
	 * @return current instance of {@code TypedQuery} with the restriction appended.
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
	 * @param firstSolution position of the first solution
	 * @return current instance of {@code TypedQuery} with the restriction appended.
	 * @since 1.0
	 */
	public Query setFirstSolution(int firstSolution);

	public Object getSolution();

	public List<Object> getSolutions();

	public Query orderAscending();

	public Query orderDescending();

	public Query orderBy(Comparator<Object> comparator);

	public Query descend(String name);

	/**
	 * Release all allocations and references for the current object
	 * 
	 * @since 1.0
	 */
	public void dispose();
}
