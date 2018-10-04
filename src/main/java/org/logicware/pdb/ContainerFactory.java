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
package org.logicware.pdb;

import org.logicware.prolog.PrologConverter;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public interface ContainerFactory {

	public Settings getSettings();

	public PrologProvider getProvider();

	public void setSettings(Settings properties);

	public void setProvider(PrologProvider provider);

	public RelationalCache createRelationalCache(Schema schema);

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user);

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, String username,
			String password);

	public HierarchicalCache createHierarchicalCache();

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, DatabaseUser user);

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, String username,
			String password);

	public StorageManager createStorageManager(String path, StorageMode storageMode);

	public StoragePool createStoragePool(String path, String name);

	public StorageGraph createStorageGraph(String path, Schema schema, StorageMode storageMode);

	public Storage createStorage(String path);

	public <K> PrologConverter<K> getConverter();

	ObjectConverter<PrologTerm> getObjectConverter();

}
