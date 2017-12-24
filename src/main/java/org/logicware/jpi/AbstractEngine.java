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
package org.logicware.jpi;

import java.util.Map;

import org.logicware.jpp.AbstractPlatform;

public abstract class AbstractEngine extends AbstractPlatform implements PrologEngine {

	protected final PrologProvider provider;
	protected static final String UNKNOW_VERSION = "unknow";

	protected AbstractEngine(PrologProvider provider) {
		this.provider = provider;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	public final boolean contains(String goal) {
		return query(goal).hasSolution();
	}

	public final boolean contains(PrologTerm... goal) {
		return query(goal).hasSolution();
	}

	public final Map<String, PrologTerm> find(String goal) {
		return query(goal).oneVariablesSolution();
	}

	public final Map<String, PrologTerm> find(PrologTerm... goal) {
		return query(goal).oneVariablesSolution();
	}

	public final Map<String, PrologTerm>[] findAll(String goal) {
		return query(goal).allVariablesSolutions();
	}

	public final Map<String, PrologTerm>[] findAll(PrologTerm... goal) {
		return query(goal).allVariablesSolutions();
	}

	public final <K extends PrologTerm> K toTerm(Object o, Class<K> from) {
		return provider.toTerm(o, from);
	}

	public final <K extends PrologTerm> K[] toTermArray(Object[] os, Class<K[]> from) {
		return provider.toTermArray(os, from);
	}

	public final <K extends PrologTerm> K[][] toTermTable(Object[][] oss, Class<K[][]> from) {
		return provider.toTermTable(oss, from);
	}

	public final <K extends PrologTerm, V extends Object> Map<String, PrologTerm> toTermMap(Map<String, V> map,
			Class<K> from) {
		return provider.toTermMap(map, from);
	}

	public final <K extends PrologTerm, V extends Object> Map<String, PrologTerm>[] toTermMapArray(Map<String, V>[] map,
			Class<K> from) {
		return provider.toTermMapArray(map, from);
	}

	public final <K> K fromTerm(PrologTerm term, Class<K> to) {
		return provider.fromTerm(term, to);
	}

	public final <K> K[] fromTermArray(PrologTerm[] terms, Class<K[]> to) {
		return provider.fromTermArray(terms, to);
	}

	public final <K> K fromTerm(PrologTerm head, PrologTerm[] body, Class<K> to) {
		return provider.fromTerm(head, body, to);
	}

	public final boolean isProgramEmpty() {
		return getProgramSize() == 0;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((provider == null) ? 0 : provider.hashCode());
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
		AbstractEngine other = (AbstractEngine) obj;
		if (provider == null) {
			if (other.provider != null)
				return false;
		} else if (!provider.equals(other.provider))
			return false;
		return true;
	}

}
