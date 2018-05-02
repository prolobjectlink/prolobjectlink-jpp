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

public interface DatabaseEngine extends PersistentContainer {

	public Collection<DatabaseRole> getRoles();

	public Collection<DatabaseUser> getUsers();

	public String getDatabaseLocation();

	public String getStorageLocation();

	public String getBaseLocation();

	public DatabaseEngine create();

	public Schema getSchema();

	public DatabaseUser getOwner();

	public String getName();

	public void drop();

}
