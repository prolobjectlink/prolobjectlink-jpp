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
package io.github.prolobjectlink.db.storage;

import static io.github.prolobjectlink.logging.LoggerConstants.IO;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import io.github.prolobjectlink.logging.LoggerUtils;

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
