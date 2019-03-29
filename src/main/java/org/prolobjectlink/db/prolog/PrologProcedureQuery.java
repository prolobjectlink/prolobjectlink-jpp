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
package org.prolobjectlink.db.prolog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.container.AbstractProcedureQuery;
import org.prolobjectlink.prolog.PrologEngine;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologQuery;
import org.prolobjectlink.prolog.PrologTerm;

/**
 * Prolog Procedure Query implementation. Is an query extension for procedures
 * cases. It allow specify arguments and procedure name.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public final class PrologProcedureQuery extends AbstractProcedureQuery implements ProcedureQuery {

	private boolean executed;

	// location to consult
	private final String path;

	// last returned terms
	private transient PrologTerm[] returnedTerms;

	// current returned terms
	private transient PrologTerm[] currentTerms;

	// Prolog query reference
	private transient PrologQuery query;

	// Prolog engine reference
	private final transient PrologEngine engine;

	// prolog driver
	private final transient PrologProvider provider;

	// converter for object/term creation
	private final transient ObjectConverter<PrologTerm> converter;

	private static final long serialVersionUID = -4371082961137952685L;

	public PrologProcedureQuery(String path, PrologProvider provider, String functor, String... arguments) {
		super(functor, arguments);
		this.converter = new PrologObjectConverter(provider);
		this.currentTerms = new PrologTerm[arguments.length];
		this.engine = provider.newEngine();
		this.provider = provider;
		this.path = path;
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

	public ProcedureQuery setArgumentValue(int position, Object value) {
		checkSolutionAt(position, currentTerms.length);
		if (returnedTerms != null) {
			returnedTerms[position] = converter.toTerm(value);
		}
		currentTerms[position] = converter.toTerm(value);
		return this;
	}

	public ProcedureQuery execute() {
		if (path != null && !path.isEmpty()) {
			engine.consult(path);
		}
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
		List<Object> solutions = new ArrayList<Object>();
		for (Iterator<Object> i = this; i.hasNext(); index++) {
			Object object = i.next();
			if (index >= firstSolution && index < maxSolution) {
				solutions.add(object);
			}
		}
		return solutions;
	}

	public Object getSolution() {
		return next();
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
		result = prime * result + Arrays.hashCode(currentTerms);
		result = prime * result + (executed ? 1231 : 1237);
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		PrologProcedureQuery other = (PrologProcedureQuery) obj;
		if (!Arrays.equals(currentTerms, other.currentTerms))
			return false;
		if (executed != other.executed)
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path)) {
			return false;
		}
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query)) {
			return false;
		}
		return Arrays.equals(returnedTerms, other.returnedTerms);
	}

	public boolean hasNext() {
		if (!executed) {
			throw new IllegalStateException("Call execute method");
		}

		if (returnedTerms == null && currentTerms != null) {
			return true;
		} else if (returnedTerms != null && currentTerms != null) {
			return !Arrays.equals(returnedTerms, currentTerms);
		}

		return false;
	}

	public Object next() {
		if (!executed) {
			throw new IllegalStateException("Call execute method");
		}

		if (!hasNext()) {
			throw new NoSuchElementException();
		}

		returnedTerms = Arrays.copyOf(currentTerms, currentTerms.length);

		// Never use query.hasNext() or query.hasMoreSolutions()
		try {
			currentTerms = query.nextSolution();
		} catch (Exception e) {
			// NoSuchElement exception or others
			currentTerms = null;
		}

		Object[] values = new Object[returnedTerms.length];
		for (int i = 0; i < returnedTerms.length; i++) {
			values[i] = converter.toObject(returnedTerms[i]);
		}
		return values;
	}

	@Override
	public void remove() {
		// skip
		next();
	}

}
