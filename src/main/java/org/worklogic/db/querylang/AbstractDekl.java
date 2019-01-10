/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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

package org.worklogic.db.querylang;

import org.worklogic.db.jpa.criteria.JpaAbstractWrapper;

/**
 *
 * @author Jose Zalacain
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractDekl extends JpaAbstractWrapper implements Dekl {

	Ident ident; // identifier
	ParList parlist; // liste of parameter
	Exp exp; // function body

	protected AbstractDekl(Ident i, ParList p, Exp e) {
		parlist = p;
		ident = i;
		exp = e;
	}

	@Override
	public String toString() {
		return ident + "(" + parlist + ") = \n  " + exp;
	}

	public final int arity() {
		return (parlist.arity());
	}

}
