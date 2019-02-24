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

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface ProcedureQuery extends Iterator<Object>, Serializable {

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
	 * @return current instance of procedure query with the restriction appended.
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
	 * @param firstSolution position of the first solution
	 * @return current instance of procedure query with the restriction appended.
	 * @since 1.0
	 */
	public ProcedureQuery setFirstSolution(int firstSolution);

	public Object getArgumentValue(int position);

	public Object getArgumentValue(String name);

	public ProcedureQuery setArgumentValue(int position, Object value);

	public ProcedureQuery setArgumentValue(String name, Object value);

	public ProcedureQuery execute();

	public List<Object> getSolutions();

	public Object getSolution();

	public void dispose();

}
