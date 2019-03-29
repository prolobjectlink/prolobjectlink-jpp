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
package org.prolobjectlink.db.tools;

import static org.prolobjectlink.logging.LoggerConstants.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.prolobjectlink.logging.LoggerUtils;

public class Restore extends Tool {

	/**
	 * Restore database files.
	 * 
	 * @param zipFileName the name of the backup file
	 * @param directory   the directory name to be restored
	 */
	public void restoreBackup(String directory, String zipFileName) {

		ZipInputStream zipIn = null;

		try {

			// streams initialization
			zipIn = new ZipInputStream(new FileInputStream(zipFileName));
			ZipEntry entry = zipIn.getNextEntry();

			// while entries exist
			while (entry != null) {

				// retrieve entry name
				String fileName = entry.getName();

				// restoring windows backups on linux and vice versa
				fileName = fileName.replace('\\', File.separator.charAt(0));
				fileName = fileName.replace('/', File.separator.charAt(0));

				// root treatment
				if (fileName.startsWith(File.separator)) {
					fileName = fileName.substring(1);
				}

				// directories creation if needed
				File file = new File(directory + fileName);
				if (!file.exists()) {
					File parent = file.getParentFile();
					if (parent != null) {
						parent.mkdirs();
					}
				}

				// extraction copy
				OutputStream out = new FileOutputStream(file, false);
				copy(zipIn, out);
				out.close();

				// close current entry
				zipIn.closeEntry();

				// retrieve next entry
				entry = zipIn.getNextEntry();

			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO + zipFileName, e);
		} finally {
			if (zipIn != null) {
				try {
					zipIn.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}
}
