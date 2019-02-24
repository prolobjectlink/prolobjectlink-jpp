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
package org.prolobjectlink.db.prolog;

import java.util.HashMap;
import java.util.Map;

import org.prolobjectlink.db.Renamer;
import org.prolobjectlink.prolog.PrologProvider;

abstract class AbstractRenamer implements Renamer {

	// provider for variable creation
	private final PrologProvider provider;

	// map prolog renamed variable name to declared class
	private final Map<String, Class<?>> variableMap;

	AbstractRenamer(PrologProvider provider) {
		variableMap = new HashMap<String, Class<?>>();
		this.provider = provider;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((variableMap == null) ? 0 : variableMap.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractRenamer other = (AbstractRenamer) obj;
		if (variableMap == null) {
			if (other.variableMap != null)
				return false;
		} else if (!variableMap.equals(other.variableMap))
			return false;
		return true;
	}

	public final Map<String, Class<?>> getVariableMap() {
		return variableMap;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

}
