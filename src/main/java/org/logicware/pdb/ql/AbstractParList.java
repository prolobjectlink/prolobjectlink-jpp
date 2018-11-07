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
package org.logicware.pdb.ql;

import java.util.Iterator;

import org.logicware.AbstractIterator;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractParList implements ParList {

	Ident ident; // identifier
	ParList parlist; // rest of the list (optional null)

	protected AbstractParList(Ident i) {
		parlist = null;
		ident = i;
	}

	protected AbstractParList(ParList p, Ident i) {
		parlist = p;
		ident = i;
	}

	@Override
	public final String toString() {
		if (parlist != null) {
			return parlist + "," + ident;
		} else {
			return ident.toString();
		}
	}

	public final int arity() {
		int size = 0;
		Iterator<?> i = iterator();
		while (i.hasNext()) {
			i.next();
			size++;
		}
		return size;
	}

	public final Iterator<Ident> iterator() {
		return new ParListIter();
	}

	private class ParListIter extends AbstractIterator<Ident> implements Iterator<Ident> {

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public Ident next() {
			// TODO Auto-generated method stub
			return null;
		}
	}

}
