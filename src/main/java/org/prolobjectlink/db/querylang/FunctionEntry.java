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
 */
public class FunctionEntry extends SymbolEntry {
	int arity;
	Dekl dekl; // location of definition

	public FunctionEntry(int type, int line, int column, int left, int right, Object value, Dekl d, int a) {
		super(type, line, column, left, right, value);
		arity = a;
		dekl = d;
	}

	public FunctionEntry(int type, int line, int column, Object value, Dekl d, int a) {
		super(type, line, column, value);
		arity = a;
		dekl = d;
	}

	public FunctionEntry(int type, int line, int column, Dekl d, int a) {
		super(type, line, column);
		arity = a;
		dekl = d;
	}

	@Override
	public String toString() {
		return "function    " + value + ", arity " + arity;
	}

	public int arity() {
		return arity;
	}

	public Dekl getDekl() {
		return dekl;
	}
}
