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

import org.prolobjectlink.db.jpa.criteria.JpaTreeNode;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractIfThenElse extends AbstractExp implements JpaTreeNode {

	BoolExp boolexp; // condition
	Exp exp1; // then branch
	Exp exp2; // else branch

	public AbstractIfThenElse(BoolExp b, Exp e1, Exp e2) {
		boolexp = b;
		exp1 = e1;
		exp2 = e2;
	}

	@Override
	public final String toString() {
		return "if " + boolexp + " then " + exp1 + " else " + exp2 + " fi";
	}

}
