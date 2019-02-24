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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.prolobjectlink.ArrayQueue;
import org.prolobjectlink.logging.LoggerUtils;

public class Backup extends Tool {

	/**
	 * Create backs up database file.
	 * 
	 * @param path        the path to copy into zip backup file
	 * @param zipFileName the name of the target backup file (including path)
	 * @param directory   the destiny directory name
	 */
	public void createBackup(String db, String directory, String zipFileName) {

		File filePtr = null;
		InputStream in = null;
		OutputStream out = null;
		ZipOutputStream zipOut = null;

		try {

			//
			File zipFile = new File(directory + zipFileName);
			if (!zipFile.exists()) {
				File parent = zipFile.getParentFile();
				if (parent != null) {
					parent.mkdirs();
				}
			}

			//
			out = new FileOutputStream(zipFile);
			zipOut = new ZipOutputStream(out);
			zipOut.setComment("Prolobjectlink Database Backup File");

			//
			Queue<File> queue = new ArrayQueue<File>();

			queue.offer(new File(db));
			while (!queue.isEmpty()) {
				filePtr = queue.poll();
				if (filePtr.isDirectory()) {

					File[] files = filePtr.listFiles();
					for (File file : files) {
						queue.offer(file);
					}

				} else {

					String path = filePtr.getPath();
					in = new FileInputStream(filePtr);
					ZipEntry entry = new ZipEntry(path);
					zipOut.putNextEntry(entry);
					copy(in, zipOut);
					zipOut.closeEntry();

				}
			}

		} catch (IOException e) {
			LoggerUtils.error(getClass(), IO, e);
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO, e);
				}
			}
		}

	}

}
