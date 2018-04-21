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
package org.logicware;

import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface ProcedureQuery extends Iterator<Object> {

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
	 * @return current instance of procedure query with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ProcedureQuery setMaxSolution(int maxSolution);

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
	 * @return current instance of procedure query with the restriction
	 *         appended.
	 * @since 1.0
	 */
	public ProcedureQuery setFirstSolution(int firstSolution);

	public Object getArgumentValue(int position);

	public Object getArgumentValue(String name);

	public ProcedureQuery setArgumentValue(int position, Object value);

	public ProcedureQuery setArgumentValue(String name, Object value);

	public ProcedureQuery execute();

	public List<Object> getSolutions();

	public Object getSolution() throws NonSolutionError;

	public void dispose();

}
