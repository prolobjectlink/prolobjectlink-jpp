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
