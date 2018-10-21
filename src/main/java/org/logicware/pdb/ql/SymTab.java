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

import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author Jose Zalacain
 */
public class SymTab {
	Hashtable h; // contains the liste of words
					// key: String, value: SymtabEntry

	SymTab pred; // predecessor symbol table (if exists)

	public SymTab() {
		this(null);
	}

	public SymTab(SymTab p) {
		h = new Hashtable();
		pred = p;
	}

	public boolean enter(String s, SymtabEntry e) {
		Object value = lookup(s);
		h.put(s, e);
		return (value == null);
	}

	public SymtabEntry lookup(String s) {
		Object value = h.get(s);
		if (value == null && pred != null)
			value = pred.lookup(s);
		return ((SymtabEntry) value);
	}

	@Override
	public String toString() { // for output with print
		String res = "symbol table\n=============\n";
		Enumeration e = h.keys();
		String key;

		while (e.hasMoreElements()) {
			key = (String) e.nextElement();
			res += key + "   \t" + h.get(key) + "\n";
		}

		if (pred != null)
			res += "++ predecessor!\n";
		return (res);
	}

	public int size() {
		return (h.size());
	}
}
