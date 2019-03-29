/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
