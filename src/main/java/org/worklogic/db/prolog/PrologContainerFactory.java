/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.prolog;

import org.logicware.prolog.PrologProvider;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.DatabaseUser;
import org.worklogic.db.HierarchicalDatabase;
import org.worklogic.db.RelationalCache;
import org.worklogic.db.RelationalDatabase;
import org.worklogic.db.Schema;
import org.worklogic.db.Storage;
import org.worklogic.db.StorageGraph;
import org.worklogic.db.StorageManager;
import org.worklogic.db.StorageMode;
import org.worklogic.db.StoragePool;
import org.worklogic.db.container.AbstractContainerFactory;
import org.worklogic.db.etc.Settings;

/** @author Jose Zalacain @since 1.0 */
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

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return new PrologRelationalDatabase(getSettings(), storageMode, name, user);
	}

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name,
			DatabaseUser user) {
		return new PrologHierarchicalDatabase(getSettings(), storageMode, name, user);
	}

}
