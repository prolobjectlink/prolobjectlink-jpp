/*
 * #%L
 * prolobjectlink
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
package org.logicware.jpd.jpi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import org.logicware.jpd.AbstractProcedureQuery;
import org.logicware.jpd.NoExecutedException;
import org.logicware.jpd.NonSolutionError;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.ProcedureArgumentError;
import org.logicware.jpd.ProcedureQuery;
import org.logicware.jpi.PrologEngine;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologQuery;
import org.logicware.jpi.PrologTerm;

public final class JPIProcedureQuery extends AbstractProcedureQuery<Object> implements ProcedureQuery {

	private boolean executed;

	// location to consult
	private final String path;

	// last returned terms
	private PrologTerm[] returnedTerms;

	// current returned terms
	private PrologTerm[] currentTerms;

	// Prolog query reference
	private PrologQuery query;

	// Prolog engine reference
	private final PrologEngine engine;

	// prolog driver
	private final PrologProvider provider;

	// Factory for object/term creation
	private final ObjectConverter<PrologTerm> converter;

	public JPIProcedureQuery(String path, PrologProvider provider, String functor, String... arguments) {
		super(functor, arguments);
		this.converter = new JPIObjectConverter(provider);
		this.currentTerms = new PrologTerm[arguments.length];
		this.engine = provider.newEngine();
		this.provider = provider;
		this.path = path;
	}

	public boolean hasMoreElements() {

		if (!executed) {
			throw new NoExecutedException(getFunctor(), getArguments().length);
		}

		if (returnedTerms == null && currentTerms != null) {
			return true;
		} else if (returnedTerms != null && currentTerms != null) {
			return !Arrays.equals(returnedTerms, currentTerms);
		}

		return false;

	}

	public Object nextElement() {

		if (!executed) {
			throw new NoExecutedException(getFunctor(), getArguments().length);
		}

		if (!hasMoreElements()) {
			throw new NoSuchElementException();
		}

		returnedTerms = Arrays.copyOf(currentTerms, currentTerms.length);
		currentTerms = query.nextElement();

		Object[] values = new Object[returnedTerms.length];
		for (int i = 0; i < returnedTerms.length; i++) {
			values[i] = converter.toObject(returnedTerms[i]);
		}
		return values;

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
		checkSolutionAt(position, currentTerms.length);
		if (returnedTerms != null) {
			checkSolutionAt(position, returnedTerms.length);
			return converter.toObject(returnedTerms[position]);
		}
		return converter.toObject(currentTerms[position]);
	}

	public Object getArgumentValue(String name) {
		for (int i = 0; i < getArguments().length; i++) {
			String argumentName = getArguments()[i];
			if (argumentName.equals(name)) {
				return getArgumentValue(i);
			}
		}
		throw new ProcedureArgumentError(getFunctor(), name);
	}

	public ProcedureQuery setArgumentValue(int position, Object value) {
		checkSolutionAt(position, currentTerms.length);
		if (returnedTerms != null) {
			returnedTerms[position] = converter.toTerm(value);
		}
		currentTerms[position] = converter.toTerm(value);
		return this;
	}

	public ProcedureQuery setArgumentValue(String name, Object value) {
		for (int i = 0; i < getArguments().length; i++) {
			String argumentName = getArguments()[i];
			if (argumentName.equals(name)) {
				setArgumentValue(i, value);
				return this;
			}
		}
		throw new ProcedureArgumentError(getFunctor(), name);
	}

	public ProcedureQuery execute() {
		engine.consult(path);
		PrologTerm[] ts = new PrologTerm[getArguments().length];
		for (int i = 0; i < getArguments().length; i++) {
			String argumentName = getArguments()[i];
			Object argumentValue = converter.toObject(currentTerms[i]);
			if (argumentValue == null) {
				ts[i] = provider.newVariable(argumentName, i);
			} else {
				ts[i] = converter.toTerm(argumentValue);
			}
		}
		query = engine.query(provider.newStructure(getFunctor(), ts));
		PrologTerm[] x = query.nextSolution();
		for (int i = 0; i < x.length; i++) {
			currentTerms[i] = x[i];
		}
		executed = true;
		return this;
	}

	public List<Object> getSolutions() {
		int index = 0;
		List<Object> solutionList = new ArrayList<Object>();
		for (Enumeration<Object> e = this; e.hasMoreElements(); index++) {
			Object object = e.nextElement();
			if (index >= firstSolution && index < maxSolution) {
				solutionList.add(object);
			}
		}
		return solutionList;
	}

	public Object getSolution() throws NonSolutionError {
		return nextElement();
	}

	@Override
	public String toString() {
		return "" + query + "";
	}

	public void dispose() {
		firstSolution = 0;
		maxSolution = MAX;
		if (query != null) {
			query.dispose();
			query = null;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((converter == null) ? 0 : converter.hashCode());
		result = prime * result + Arrays.hashCode(currentTerms);
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
		result = prime * result + (executed ? 1231 : 1237);
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result + Arrays.hashCode(returnedTerms);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JPIProcedureQuery other = (JPIProcedureQuery) obj;
		if (converter == null) {
			if (other.converter != null)
				return false;
		} else if (!converter.equals(other.converter))
			return false;
		if (!Arrays.equals(currentTerms, other.currentTerms))
			return false;
		if (engine == null) {
			if (other.engine != null)
				return false;
		} else if (!engine.equals(other.engine))
			return false;
		if (executed != other.executed)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (!Arrays.equals(returnedTerms, other.returnedTerms))
			return false;
		return true;
	}

}
