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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.worklogic.db.querylang;

import org.worklogic.db.jpa.criteria.JpaAbstractWrapper;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractProgram extends JpaAbstractWrapper implements Program {

	ParList parlist; // input variables
	DeklList dekllist; // function declarations
	ExpList explist; // result expressions
	ExpList arguments; // input values

	SymbolTable inputs; // table of input variables
	SymbolTable functions; // table of functions

	public AbstractProgram(ParList p, DeklList d, ExpList e, ExpList a) {
		parlist = p;
		dekllist = d;
		explist = e;
		arguments = a;
	}

	@Override
	public final String toString() {

		return "Program:\n=============\ninput " + parlist + "\nfunctions\n" + dekllist + "\noutput " + explist
				+ "\narguments " + arguments + "\nend";

	}

}
