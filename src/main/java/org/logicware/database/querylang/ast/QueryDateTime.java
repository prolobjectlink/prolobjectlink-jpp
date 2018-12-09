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
package org.logicware.database.querylang.ast;

import org.logicware.database.querylang.AbstractDateTime;
import org.logicware.database.querylang.Date;
import org.logicware.database.querylang.DateTime;
import org.logicware.database.querylang.SymbolTable;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryDateTime extends AbstractDateTime implements DateTime {

	public QueryDateTime(Date date) {
		super(date);
	}

	@Override
	public String compile(SymbolTable symbols) {
		return "'" + date + "'";
	}

}
