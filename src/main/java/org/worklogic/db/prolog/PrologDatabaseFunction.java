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
package org.worklogic.db.prolog;

import org.logicware.prolog.PrologProvider;
import org.worklogic.db.DatabaseFunction;
import org.worklogic.db.ProcedureQuery;
import org.worklogic.db.Schema;

public final class PrologDatabaseFunction extends DatabaseFunction {

	private static final long serialVersionUID = 142499432277102067L;

	private PrologDatabaseFunction() {
		// for internal reflection
		super();
	}

	public PrologDatabaseFunction(String name, String comment, Schema schema, String path, PrologProvider provider) {
		super(name, comment, schema, path, provider);
	}

	private ProcedureQuery createProcedureQuery(Object... arguments) {
		return new PrologProcedureQuery(getPath(), getProvider(), getName(), getArgs(arguments));
	}

	private String[] getArgs(Object[] arguments) {
		String[] args = new String[arguments.length];
		for (int i = 0; i < arguments.length; i++) {
			args[i] = "" + arguments[i] + "";
		}
		return args;
	}

	public Object execute(Object... arguments) {

		ProcedureQuery p = createProcedureQuery(arguments);
		return p.execute().getSolution();
	}

}
