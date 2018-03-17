/*
 * #%L
 * prolobjectlink
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
package org.logicware;

import org.logicware.prolog.PrologProvider;

public interface ContainerFactory {

	public Settings getProperties();

	public PrologProvider getProvider();

	public void setProvider(PrologProvider provider);

	public void setProperties(Settings properties);

	public RelationalCache createRelationalCache(DatabaseSchema schema);

	public RelationalDatabase createRelationalDatabase(String name, DatabaseUser user);

	public RelationalDatabase createRelationalDatabase(String name, String username, String password);

	public HierarchicalCache createHierarchicalCache();

	public HierarchicalDatabase createHierarchicalDatabase(String name, DatabaseUser user);

	public HierarchicalDatabase createHierarchicalDatabase(String name, String username, String password);

	public StorageManager createStorageManager(String path);

	public StoragePool createStoragePool(String path, String name);

	public StorageGraph createStorageGraph(String path, DatabaseSchema schema);

	public Storage createStorage(String path);

}
