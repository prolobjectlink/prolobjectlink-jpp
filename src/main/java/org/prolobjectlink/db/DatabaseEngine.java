/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
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

	public boolean exist();

	public Schema getSchema();

	public String getName();

	public DatabaseMode getMode();

	public DatabaseType getType();

	public URL getURL();

	public Map<String, DatabaseUnitInfo> getPersistenceUnits();

}
