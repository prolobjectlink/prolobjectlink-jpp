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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.prolobjectlink.db.ProcedureQuery;

public final class DummyProcedureQuery extends AbstractProcedureQuery implements ProcedureQuery {

	private static final Object[] emptyArray = new Object[0];
	private static final List<Object> empty = new ArrayList<Object>();
	private static final long serialVersionUID = 2982433009696455329L;

	public DummyProcedureQuery(String functor, String[] arguments) {
		super(functor, arguments);
	}

	public boolean hasNext() {
		return false;
	}

	public Object next() {
		throw new NoSuchElementException();
	}

	public ProcedureQuery setMaxSolution(int maxSolution) {
		this.maxSolution = maxSolution;
		return this;
	}

	public ProcedureQuery setFirstSolution(int firstSolution) {
		this.firstSolution = firstSolution;
		return this;
	}

	public Object getArgumentValue(int position) {
		checkSolutionAt(position, empty.size());
		return getArguments()[position];
	}

	public ProcedureQuery setArgumentValue(int position, Object value) {
		checkSolutionAt(position, empty.size());
		empty.set(position, value);
		return this;
	}

	public ProcedureQuery execute() {
		return this;
	}

	public List<Object> getSolutions() {
		return empty;
	}

	public Object getSolution() {
		return emptyArray;
	}

	public void dispose() {
		// do nothing
	}

	@Override
	public void remove() {
		// do nothing
	}

}
