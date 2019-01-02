/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.db;

import org.logicware.RuntimeError;

public final class ProcedureInvokationError extends RuntimeError {

	private static final long serialVersionUID = -1926528417397697548L;

	public ProcedureInvokationError(String name, int expected, int invoked) {
		super("Illegal arguments number for procedure " + name + " invocation. Expected " + expected
				+ " and was invoked with " + invoked);
	}

}