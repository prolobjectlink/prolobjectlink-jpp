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
package org.logicware.db;

import org.logicware.AbstractSchema;
import org.logicware.ContainerFactory;
import org.logicware.DatabaseSchema;
import org.logicware.DatabaseUser;
import org.logicware.prolog.PrologProvider;

public class MetadataSchema extends AbstractSchema implements DatabaseSchema {

	public MetadataSchema(String location, PrologProvider provider, ContainerFactory factory, DatabaseUser owner) {
		super(location, provider, factory, owner);
	}

}
