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
package org.logicware.prolog;

import java.util.Iterator;
import java.util.Map;

import org.logicware.AbstractWrapper;

public abstract class AbstractQuery extends AbstractWrapper implements PrologQuery {

	// engine for execute queries
	protected final AbstractEngine engine;

	public AbstractQuery(AbstractEngine engine) {
		this.engine = engine;
	}

	public final PrologEngine getEngine() {
		return engine;
	}

	public final PrologProvider getProvider() {
		return engine.getProvider();
	}

	public final <K extends PrologTerm> K toTerm(Object o, Class<K> from) {
		return engine.toTerm(o, from);
	}

	protected final <K extends PrologTerm> K[] toTermArray(Object[] os, Class<K[]> from) {
		return engine.toTermArray(os, from);
	}

	protected final <K extends PrologTerm> K[][] toTermTable(Object[][] oss, Class<K[][]> from) {
		return engine.toTermMatrix(oss, from);
	}

	protected final <K extends PrologTerm, V extends Object> Map<String, PrologTerm> toTermMap(Map<String, V> map,
			Class<K> from) {
		return engine.toTermMap(map, from);
	}

	protected final <K extends PrologTerm, V extends Object> Map<String, PrologTerm>[] toTermMapArray(
			Map<String, V>[] map, Class<K> from) {
		return engine.toTermMapArray(map, from);
	}

	protected final <K> K fromTerm(PrologTerm term, Class<K> to) {
		return engine.fromTerm(term, to);
	}

	protected final <K> K[] fromTermArray(PrologTerm[] terms, Class<K[]> to) {
		return engine.fromTermArray(terms, to);
	}

	protected final <K> K fromTerm(PrologTerm head, PrologTerm[] body, Class<K> to) {
		return engine.fromTerm(head, body, to);
	}

	public final Iterator<PrologTerm[]> iterator() {
		return new PrologQueryIterator(this);
	}

	public final boolean hasNext() {
		return hasMoreSolutions();
	}

	public final PrologTerm[] next() {
		// don't check has next
		// don't raise NoSuchElementException
		// need a deep revision in wrapped engines
		return nextSolution();
	}

	public final void remove() {
		// skip
		nextSolution();
	}

	public final Map<String, PrologTerm> one() {
		return oneVariablesSolution();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((engine == null) ? 0 : engine.hashCode());
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
		AbstractQuery other = (AbstractQuery) obj;
		if (engine == null) {
			if (other.engine != null)
				return false;
		} else if (!engine.equals(other.engine))
			return false;
		return true;
	}

}
