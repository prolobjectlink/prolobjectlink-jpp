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
package org.logicware.pdb.container;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.logicware.pdb.ProcedureQuery;

public final class DummyProcedureQuery extends AbstractProcedureQuery implements ProcedureQuery {

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
		// TODO Auto-generated method stub
		return null;
	}

	public Object getArgumentValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcedureQuery setArgumentValue(int position, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcedureQuery setArgumentValue(String name, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcedureQuery execute() {
		return this;
	}

	public List<Object> getSolutions() {
		return new ArrayList<Object>();
	}

	public Object getSolution() {
		return null;
	}

	public void dispose() {
		// do nothing
	}

	@Override
	public void remove() {
		// do nothing
	}

}
