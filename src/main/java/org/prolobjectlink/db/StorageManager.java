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

/**
 * Storage Manager is a file system manager to store data. Is an specification
 * of of {@link PersistentContainer} that manage many file and data folders. The
 * storage manager have two mode {@link Storage} and {@link StoragePool}
 * indicate by {@link StorageMode}. Single storage can be more faster for few
 * data. Storage pool distribute all data in short data file limited by data
 * number.
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Storage
 * @see StoragePool
 */
public interface StorageManager extends PersistentContainer {

	PersistentContainer loggerOf(Class<?> clazz);

	PersistentContainer masterOf(Class<?> clazz);

}
