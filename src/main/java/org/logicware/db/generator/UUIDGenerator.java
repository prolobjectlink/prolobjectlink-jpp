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
package org.logicware.db.generator;

import java.util.UUID;

import org.logicware.db.IdGenerator;
import org.logicware.db.IdGeneratorType;

public final class UUIDGenerator extends AbstractIdGenerator<UUID> implements IdGenerator<UUID> {

	private static final long serialVersionUID = -6657592370387129719L;

	public UUIDGenerator(Class<?> clazz) {
		super(UUID.randomUUID(), clazz);
	}

	public UUID generateId() {
		return setValue(UUID.randomUUID());
	}

	public IdGeneratorType geIdGeneratorType() {
		return IdGeneratorType.UUID;
	}

}