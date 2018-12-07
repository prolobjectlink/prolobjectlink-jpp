/*
 * #%L
 * prolobjectlink-jcq
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
package org.logicware.database.querylang;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractUminus extends AbstractExp implements Uminus {

	Exp exp; // the negated expression

	public AbstractUminus(Exp e) {
		exp = e;
	}

	@Override
	public final String toString() {
		return "-" + exp;
	}

}
