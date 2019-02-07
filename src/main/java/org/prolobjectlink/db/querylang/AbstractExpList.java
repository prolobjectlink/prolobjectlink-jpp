/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.querylang;

import java.util.Iterator;

import org.prolobjectlink.AbstractIterator;
import org.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractExpList extends JpaAbstractWrapper implements ExpList {

	Exp exp; // expression of this list node
	ExpList explist; // next list element (optional null)

	protected AbstractExpList(Exp e) {
		explist = null;
		exp = e;
	}

	protected AbstractExpList(ExpList p, Exp e) {
		explist = p;
		exp = e;
	}

	@Override
	public final String toString() {
		if (explist != null)
			return explist + "," + exp;
		else
			return "" + exp + "";
	}

	public final Iterator<ExpList> iterator() {
		return new ExpListIter();
	}

	private class ExpListIter extends AbstractIterator<ExpList> implements Iterator<ExpList> {

		public boolean hasNext() {
			// TODO Auto-generated method stub
			return false;
		}

		public ExpList next() {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
