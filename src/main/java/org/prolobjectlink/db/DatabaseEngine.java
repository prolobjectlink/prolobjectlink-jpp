/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
