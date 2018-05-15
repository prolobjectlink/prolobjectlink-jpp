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
package org.logicware.pdb;

import java.util.Collection;
import java.util.List;

public interface DatabaseEngine extends PersistentContainer {

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

	public Collection<DatabaseRole> getRoles();

	public Collection<DatabaseUser> getUsers();

	/**
	 * Return the low level {@link PersistentContainer} held by this
	 * {@link DatabaseEngine}. Low level {@link PersistentContainer} can be some
	 * storage object used by this database for persistence operations. In other
	 * words this method will be returned an instance of
	 * {@link Storage},{@link StorageGraph},{@link StorageManager},{@link StoragePool}.
	 * 
	 * @return low level {@link PersistentContainer}
	 * @since 1.0
	 */
	public PersistentContainer getContainer();

	public String getDatabaseLocation();

	public String getStorageLocation();

	public String getRootLocation();

	public DatabaseUser getOwner();

	public DatabaseEngine create();

	public DatabaseEngine drop();

	public Schema getSchema();

	public String getName();

}
