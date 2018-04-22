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
package org.logicware.prolog;

import org.logicware.ContainerFactory;
import org.logicware.DatabaseUser;
import org.logicware.HierarchicalCache;
import org.logicware.HierarchicalDatabase;
import org.logicware.Settings;
import org.logicware.RelationalCache;
import org.logicware.RelationalDatabase;
import org.logicware.Schema;
import org.logicware.Storage;
import org.logicware.StorageGraph;
import org.logicware.StorageManager;
import org.logicware.StoragePool;
import org.logicware.db.AbstractContainerFactory;

public abstract class PrologContainerFactory extends AbstractContainerFactory implements ContainerFactory {

	protected PrologContainerFactory(Settings properties, PrologProvider provider) {
		super(properties, provider);
	}

	public RelationalCache createRelationalCache(Schema schema) {
		return new PrologRelationalCache(schema, getSettings(), getProvider(), this);
	}

	public HierarchicalCache createHierarchicalCache() {
		return new PrologHierarchicalCache(getProvider(), getSettings());
	}

	public Storage createStorage(String path) {
		return new PrologStorage(getProvider(), getSettings(), path, this);
	}

	public StoragePool createStoragePool(String path, String name) {
		return new PrologStoragePool(getProvider(), getSettings(), path, name, this);
	}

	public StorageManager createStorageManager(String path) {
		return new PrologStorageManager(getProvider(), getSettings(), path, this);
	}

	public StorageGraph createStorageGraph(String path, Schema schema) {
		return new PrologStorageGraph(getProvider(), getSettings(), path, schema, this);
	}

	public RelationalDatabase createRelationalDatabase(String name, DatabaseUser user) {
		return new PrologRelationalDatabase(getProvider(), getSettings(), this, name, user);
	}

	public HierarchicalDatabase createHierarchicalDatabase(String name, DatabaseUser user) {
		return new PrologHerarchicalDatabase(getProvider(), getSettings(), this, name, user);
	}

}
