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

import org.prolobjectlink.db.DatabaseFunction;
import org.prolobjectlink.db.ProcedureQuery;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.prolog.PrologProvider;

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
