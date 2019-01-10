/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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

package org.worklogic.db.querylang;

/**
 *
 * @author Jose Zalacain
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractIdent extends AbstractExp implements Ident {

	String name;
	int lineno;

	int index; // number of ident in environment
	boolean isInput; // is it an input variable?

	public AbstractIdent(String n) {
		name = n;
	}

	public AbstractIdent(String s, int line) {
		name = s;
		lineno = line;
	}

	@Override
	public final String toString() {
		return name;
	}

}
