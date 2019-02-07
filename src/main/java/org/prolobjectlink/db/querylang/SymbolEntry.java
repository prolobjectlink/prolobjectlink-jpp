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

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class SymbolEntry {

	public int sym;
	public int left;
	public int right;
	private int line;
	private int column;
	public Object value;

	public SymbolEntry(int type, int line, int column) {
		this(type, line, column, -1, -1, null);
	}

	public SymbolEntry(int type, int line, int column, Object value) {
		this(type, line, column, -1, -1, value);
	}

	public SymbolEntry(int type, int line, int column, int left, int right, Object value) {
		this.sym = type;
		this.left = left;
		this.right = right;
		this.value = value;
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "line " + line + ", column " + column + ", sym: " + sym
				+ (value == null ? "" : (", value: '" + value + "'"));
	}

}
