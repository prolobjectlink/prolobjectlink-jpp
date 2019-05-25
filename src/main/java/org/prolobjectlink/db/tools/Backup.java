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
					if (files != null) {
						for (File file : files) {
							queue.offer(file);
						}
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
