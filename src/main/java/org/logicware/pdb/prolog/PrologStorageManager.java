/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.pdb.prolog;

import org.logicware.pdb.Container;
import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Storage;
import org.logicware.pdb.StorageManager;
import org.logicware.pdb.StorageMode;
import org.logicware.pdb.VolatileContainer;
import org.logicware.pdb.storage.AbstractStorageManager;

/**
 * Prolog {@link StorageManager} implementation
 * 
 * @author Jose Zalacain
 * @see Container
 * @see VolatileContainer
 * @see Storage
 * @since 1.0
 */
public class PrologStorageManager extends AbstractStorageManager implements StorageManager {

	public PrologStorageManager(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory, StorageMode storageMode) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory, storageMode);
	}

	public PrologStorageManager(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, StorageMode storageMode) {
		super(provider, properties, converter, location, containerFactory, storageMode);
	}

}