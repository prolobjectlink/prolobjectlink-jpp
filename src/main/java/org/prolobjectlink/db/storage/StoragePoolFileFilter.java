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
