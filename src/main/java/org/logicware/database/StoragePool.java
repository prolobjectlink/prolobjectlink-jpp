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
package org.logicware.database;

import java.io.FileFilter;
import java.util.List;

/**
 * Container folder that have many single storages with limited clauses for each
 * storage in a located root folder.
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Storage
 * @see StorageManager
 */
public interface StoragePool extends PersistentContainer {

	public Object find(String string);

	public Object find(String functor, Object... args);

	public <O> O find(O o);

	public <O> O find(Class<O> clazz);

	public <O> O find(Predicate<O> predicate);

	public List<Object> findAll(String string);

	public List<Object> findAll(String functor, Object... args);

	public <O> List<O> findAll(O o);

	public <O> List<O> findAll(Class<O> clazz);

	public <O> List<O> findAll(Predicate<O> predicate);

	public Storage createStorage(String location, int maxCapacity);

	public List<Storage> getStorages();

	public String getPoolName();

	public int getCapacity();

	/**
	 * Storage number in this storage pool.
	 * 
	 * @return Storage number in this storage pool.
	 * @since 1.0
	 */
	public int getPoolSize();

	/**
	 * Check if this storage pool no have any storage. Equivalent to
	 * {@code #getPoolSize()==0}.
	 * 
	 * @return true if storage pool no have any storage and false otherwise
	 * @since 1.0
	 */
	public boolean isEmpty();

	/**
	 * File filter used for filter pool files into document pool.
	 * 
	 * @return File filter used for filter pool files into document pool.
	 * @since 1.0
	 */
	public FileFilter getFilter();

}
