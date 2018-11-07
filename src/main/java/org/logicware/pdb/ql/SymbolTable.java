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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jose Zalacain
 */
public class SymbolTable {

	SymbolTable pred; // predecessor symbol table (if exists)
	Map<String, SymbolEntry> table;

	public SymbolTable() {
		this(null);
	}

	public SymbolTable(SymbolTable p) {
		table = new HashMap<String, SymbolEntry>();
		pred = p;
	}

	public boolean enter(String s, SymbolEntry e) {
		Object value = lookup(s);
		table.put(s, e);
		return (value == null);
	}

	public SymbolEntry lookup(String s) {
		SymbolEntry value = table.get(s);
		if (value == null && pred != null)
			value = pred.lookup(s);
		return value;
	}

	@Override
	public String toString() { // for output with print
		String res = "symbol table\n=============\n";
		Iterator<String> e = table.keySet().iterator();
		String key;

		while (e.hasNext()) {
			key = e.next();
			res += key + "   \t" + table.get(key) + "\n";
		}

		if (pred != null)
			res += "++ predecessor!\n";
		return (res);
	}

	public int size() {
		return (table.size());
	}
}
