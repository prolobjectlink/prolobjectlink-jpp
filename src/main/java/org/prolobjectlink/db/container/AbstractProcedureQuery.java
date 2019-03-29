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
package org.prolobjectlink.db.container;

import java.util.Arrays;

import org.prolobjectlink.db.ProcedureQuery;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractProcedureQuery extends AbstractContainerQuery implements ProcedureQuery {

	// prolog procedure name
	private final String functor;

	// prolog procedures arguments
	private final String[] arguments;

	private static final long serialVersionUID = 3865394932385130879L;

	protected AbstractProcedureQuery(String functor, String[] arguments) {
		this.functor = functor;
		this.arguments = arguments;
	}

	protected final String getFunctor() {
		return functor;
	}

	protected final String[] getArguments() {
		return arguments;
	}

	public final Object getArgumentValue(String name) {
		for (int i = 0; i < getArguments().length; i++) {
			String argumentName = getArguments()[i];
			if (argumentName.equals(name)) {
				return getArgumentValue(i);
			}
		}
		throw new IllegalArgumentException(
				"No register argument '" + name + "' for the procedure '" + getFunctor() + "'");
	}

	public final ProcedureQuery setArgumentValue(String name, Object value) {
		for (int i = 0; i < getArguments().length; i++) {
			String argumentName = getArguments()[i];
			if (argumentName.equals(name)) {
				setArgumentValue(i, value);
				return this;
			}
		}
		throw new IllegalArgumentException(
				"No register argument '" + name + "' for the procedure '" + getFunctor() + "'");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(arguments);
		result = prime * result + ((functor == null) ? 0 : functor.hashCode());
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
		AbstractProcedureQuery other = (AbstractProcedureQuery) obj;
		if (!Arrays.equals(arguments, other.arguments))
			return false;
		if (functor == null) {
			if (other.functor != null)
				return false;
		} else if (!functor.equals(other.functor))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AbstractProcedureQuery [functor=" + functor + ", arguments=" + Arrays.toString(arguments) + "]";
	}

}
