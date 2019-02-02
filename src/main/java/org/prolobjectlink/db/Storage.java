/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
