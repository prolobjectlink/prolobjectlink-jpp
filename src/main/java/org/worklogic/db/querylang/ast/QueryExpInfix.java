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

package org.worklogic.db.querylang.ast;

import org.worklogic.db.querylang.AbstractExpInfix;
import org.worklogic.db.querylang.Exp;
import org.worklogic.db.querylang.ExpInfix;

public class QueryExpInfix extends AbstractExpInfix implements ExpInfix {

	public QueryExpInfix(Exp e1, char k, Exp e2) {
		super(e1, k, e2);
	}

}
