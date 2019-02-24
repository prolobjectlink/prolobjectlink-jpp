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

import java.io.File;
import java.util.List;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Storage extends PersistentContainer {

	// TODO REMOVE IN FIND METHODS RETURN NULL IF NOT
	// EXIST

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

	/**
	 * Check that this store have less clauses number in prolog engine that given
	 * capacity.
	 * 
	 * @return true if this store has a clause number lesser than given capacity or
	 *         false otherwise
	 * @since 1.0
	 */
	public boolean hasCapacity();

	public int getCapacity();

	/**
	 * The length of the host file for this document.
	 * 
	 * @return length of the file
	 * @since 1.0
	 */
	public long getLength();

	public int getSize();

	public File getFile();

	public void setDirty(boolean dirty);

	public boolean isDirty();

	public boolean locked();

}
