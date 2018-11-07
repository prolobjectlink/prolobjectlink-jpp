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
class VariableEntry extends SymbolEntry {

	int index;
	boolean isInput;

	public VariableEntry(String v, int line, boolean ii, int ind) {
		super(v, line);
		isInput = ii;
		index = ind;
	}

	@Override
	public int kind() {
		return SymbolEntry.VAR;
	}

	@Override
	public String toString() {
		if (isInput)
			return "input var " + name + "  (" + index + ")";
		else
			return "parameter " + name + "  (" + index + ")";
	}

	public int getIndex() {
		return index;
	}

	public boolean isInput() {
		return isInput;
	}
}
