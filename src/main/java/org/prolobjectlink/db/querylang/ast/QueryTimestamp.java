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
package org.prolobjectlink.db.querylang.ast;

import org.prolobjectlink.db.querylang.AbstractTimestamp;
import org.prolobjectlink.db.querylang.Timestamp;

/**
 *
 * @author Jose Zalacain
 * @since 1.0
 */
public class QueryTimestamp extends AbstractTimestamp implements Timestamp {

	public QueryTimestamp(long timestamp) {
		super(timestamp);
	}

}