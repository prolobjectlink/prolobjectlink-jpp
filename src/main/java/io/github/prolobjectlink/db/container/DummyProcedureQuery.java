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
package io.github.prolobjectlink.db.container;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.github.prolobjectlink.db.ProcedureQuery;

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
