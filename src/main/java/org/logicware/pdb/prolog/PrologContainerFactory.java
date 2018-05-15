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

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.HierarchicalDatabase;
import org.logicware.pdb.RelationalCache;
import org.logicware.pdb.RelationalDatabase;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Storage;
import org.logicware.pdb.StorageGraph;
import org.logicware.pdb.StorageManager;
import org.logicware.pdb.StorageMode;
import org.logicware.pdb.StoragePool;
import org.logicware.pdb.common.AbstractContainerFactory;

public abstract class PrologContainerFactory extends AbstractContainerFactory implements ContainerFactory {

	protected PrologContainerFactory(Settings properties, PrologProvider provider) {
		super(properties, provider);
	}

	public final RelationalCache createRelationalCache(Schema schema) {
		return new PrologRelationalCache(schema, getSettings(), getProvider(), this);
	}

	public final Storage createStorage(String path) {
		return new PrologStorage(getProvider(), getSettings(), path, this);
	}

	public final StoragePool createStoragePool(String path, String name) {
		return new PrologStoragePool(getProvider(), getSettings(), path, name, this);
	}

	public final StorageManager createStorageManager(String path, StorageMode storageMode) {
		return new PrologStorageManager(getProvider(), getSettings(), path, this, storageMode);
	}

	public final StorageGraph createStorageGraph(String path, Schema schema, StorageMode storageMode) {
		return new PrologStorageGraph(getProvider(), getSettings(), path, schema, this, storageMode);
	}

	public final RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return new PrologRelationalDatabase(getSettings(), storageMode, name, user);
	}

	public final HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name,
			DatabaseUser user) {
		return new PrologHierarchicalDatabase(getSettings(), storageMode, name, user);
	}

}
