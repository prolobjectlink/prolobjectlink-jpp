/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.pdb.tools;

import static org.logicware.pdb.logging.LoggerConstants.IO_ERROR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.logicware.pdb.logging.LoggerUtils;

public class Backup extends Tool {

	private String db;

	/**
	 * Create an backup tool object
	 * 
	 * @param path
	 *            the database path
	 */
	public Backup(String path) {
		this.db = path;
	}

	/**
	 * Create backs up database file.
	 * 
	 * @param zipFileName
	 *            the name of the target backup file (including path)
	 * @param directory
	 *            the destiny directory name
	 */
	public void createBackup(String directory, String zipFileName) {

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
			Queue<File> queue = new ArrayDeque<File>();

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
			LoggerUtils.error(getClass(), IO_ERROR, e);
		} finally {
			if (zipOut != null) {
				try {
					zipOut.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO_ERROR, e);
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO_ERROR, e);
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					LoggerUtils.error(getClass(), IO_ERROR, e);
				}
			}
		}

	}
}
