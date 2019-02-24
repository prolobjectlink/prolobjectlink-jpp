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
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Restorable {

	/**
	 * Create a database backup named zipFileName.zip hosted on the specified
	 * directory.
	 * 
	 * @param directory   location to save create zipFileName.zip
	 * @param zipFileName database backup file name
	 * @since 1.0
	 * @see #restore(String, String)
	 */
	public void backup(String directory, String zipFileName);

	/**
	 * Restore a database backup named zipFileName in the directory location.
	 * 
	 * @param directory   location to restore database
	 * @param zipFileName database backup file name
	 * @since 1.0
	 * @see #backup(String, String)
	 */
	public void restore(String directory, String zipFileName);

}
