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
package org.logicware.pdb.common;

import java.util.Arrays;

import org.logicware.pdb.ProcedureQuery;

public abstract class AbstractProcedureQuery<S> extends AbstractQuery<S> implements ProcedureQuery {

	// prolog procedure name
	private final String functor;

	// prolog procedures arguments
	private final String[] arguments;

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
		AbstractProcedureQuery<?> other = (AbstractProcedureQuery<?>) obj;
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