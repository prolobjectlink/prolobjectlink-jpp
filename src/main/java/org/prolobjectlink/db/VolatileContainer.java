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

import java.util.List;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface VolatileContainer extends Container {

	public <O> void add(O... facts);

	public <O> void modify(O match, O merge);

	public void remove(Class<?> clazz);

	public <O> void remove(O... facts);

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
	 * Remove all object of given class from the cache.
	 * 
	 * @param cls entity class
	 */
	public void evict(Class<?> cls);

	/**
	 * Clear the cache.
	 */
	public void evictAll();

	/**
	 * Clear cache erase all objects in main memory. The {@code ObjectStore}
	 * implementations erase all objects in main memory such as a flush method
	 * invocation set to empty the physic file
	 */
	public void clear();

}
