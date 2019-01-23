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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.worklogic.db.querylang.ast;

import org.worklogic.db.querylang.AbstractProgram;
import org.worklogic.db.querylang.DeklList;
import org.worklogic.db.querylang.ExpList;
import org.worklogic.db.querylang.ParList;
import org.worklogic.db.querylang.Program;

public class QueryProgram extends AbstractProgram implements Program {

	public QueryProgram(ParList p, DeklList d, ExpList e, ExpList a) {
		super(p, d, e, a);
	}

}
