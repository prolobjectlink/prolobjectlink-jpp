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
