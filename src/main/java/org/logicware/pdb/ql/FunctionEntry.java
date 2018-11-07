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

/**
 *
 * @author Jose Zalacain
 */
public class FunctionEntry extends SymbolEntry {
	int arity;
	Dekl dekl; // location of definition

	public FunctionEntry(String f, int line, Dekl d, int a) {
		super(f, line);
		dekl = d;
		arity = a;
	}

	@Override
	public int kind() {
		return SymbolEntry.FUN;
	}

	@Override
	public String toString() {
		return "function    " + name + ", arity " + arity;
	}

	public int arity() {
		return arity;
	}

	public Dekl getDekl() {
		return dekl;
	}
}
