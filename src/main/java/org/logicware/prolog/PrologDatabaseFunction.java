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
package org.logicware.prolog;

import org.logicware.DatabaseFunction;
import org.logicware.Schema;
import org.logicware.NonSolutionError;
import org.logicware.ProcedureQuery;
import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;

public final class PrologDatabaseFunction extends DatabaseFunction {

	private static final long serialVersionUID = 142499432277102067L;

	private PrologDatabaseFunction() {
		// TODO use refelection utils
		// for internal reflection
	}

	public PrologDatabaseFunction(String name, Schema schema, String path, PrologProvider provider) {
		super(name, schema, path, provider);
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
		try {
			return p.execute().getSolution();
		} catch (NonSolutionError e) {
			LoggerUtils.error(getClass(), LoggerConstants.NON_SOLUTION);
		}
		return null;
	}

}