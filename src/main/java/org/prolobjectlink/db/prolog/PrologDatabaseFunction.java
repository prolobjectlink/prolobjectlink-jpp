/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
