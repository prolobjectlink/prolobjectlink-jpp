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
package org.logicware.db.querylang.ast;

import org.logicware.db.querylang.AbstractDekl;
import org.logicware.db.querylang.Dekl;
import org.logicware.db.querylang.Exp;
import org.logicware.db.querylang.Ident;
import org.logicware.db.querylang.ParList;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryDekl extends AbstractDekl implements Dekl {

	public QueryDekl(Ident i, ParList p, Exp e) {
		super(i, p, e);
	}

}
