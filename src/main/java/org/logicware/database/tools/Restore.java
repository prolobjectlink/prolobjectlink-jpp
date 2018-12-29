/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.logicware.database.tools;

import static org.logicware.logging.LoggerConstants.IO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.logicware.AbstractPlatform;
import org.logicware.logging.LoggerUtils;

public class Restore extends AbstractPlatform {

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
