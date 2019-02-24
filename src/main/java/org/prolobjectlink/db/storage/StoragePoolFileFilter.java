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
package org.prolobjectlink.db.storage;

import static org.prolobjectlink.logging.LoggerConstants.IO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import org.prolobjectlink.logging.LoggerUtils;

/**
 * File filter for filter files in storage pools. The storages files to filter
 * are files that end with a number that indicate the pool index. Used when
 * document pool open the entire pool and need filter pool files in a pool root
 * directory that have lock files and pool files.
 * 
 * @author Jose Zalacain
 * @since 1.0
 *
 */
final class StoragePoolFileFilter implements FileFilter {

	static final String NUMBER_REGEX = "[0-9]+";

	public boolean accept(File pathname) {
		String name;
		try {
			name = pathname.getCanonicalPath();
			int lastDotIndex = name.lastIndexOf('.');
			String index = name.substring(lastDotIndex + 1);
			return index.matches(NUMBER_REGEX);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		}
		return false;
	}

}
