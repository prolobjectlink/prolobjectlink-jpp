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
package org.logicware.database.prolog;

import java.util.HashMap;
import java.util.Map;

import org.logicware.database.Renamer;
import org.logicware.prolog.PrologProvider;

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
