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

import org.prolobjectlink.db.jpa.criteria.JpaAbstractWrapper;

/**
 *
 * @author Jose Zalacain
 */
/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractBoolExp extends JpaAbstractWrapper implements BoolExp {

	char kind; // '=', '<' and '!' for "<="
	Exp exp1; // left subexpression
	Exp exp2; // right subexpression

	protected AbstractBoolExp(Exp e1, char k, Exp e2) {
		exp1 = e1;
		kind = k;
		exp2 = e2;
	}

	@Override
	public final String toString() {
		if (kind != '!')
			return "" + exp1 + kind + exp2;
		else
			return exp1 + "<=" + exp2;
	}

}
