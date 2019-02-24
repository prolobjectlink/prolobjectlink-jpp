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
