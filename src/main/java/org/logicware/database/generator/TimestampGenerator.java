/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.generator;

import org.logicware.database.IdGenerator;
import org.logicware.database.IdGeneratorType;

public final class TimestampGenerator extends AbstractIdGenerator<Long> implements IdGenerator<Long> {

	private static final long serialVersionUID = 7830482991999467963L;

	public TimestampGenerator(Class<?> clazz) {
		super(System.currentTimeMillis(), clazz);
	}

	public Long generateId() {
		return setValue(System.currentTimeMillis());
	}

	public IdGeneratorType geIdGeneratorType() {
		return IdGeneratorType.TIMESTAMP;
	}

}
