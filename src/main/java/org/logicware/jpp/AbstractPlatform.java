/*
 * #%L
 * prolobjectlink
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
package org.logicware.jpp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;

public abstract class AbstractPlatform extends AbstractWrapper implements Platform {

	protected static final int IO_BUFFER_SIZE = 4 * 1024;
	protected static final long MAX_IO_BUFFER_SIZE = Long.MAX_VALUE;

	protected final synchronized long copy(InputStream in, OutputStream out) {
		long copied = 0;
		try {
			long length = MAX_IO_BUFFER_SIZE;
			int len = (int) Math.min(length, IO_BUFFER_SIZE);
			byte[] buffer = new byte[len];
			while (length > 0) {
				len = in.read(buffer, 0, len);
				if (len < 0) {
					break;
				}
				if (out != null) {
					out.write(buffer, 0, len);
				}
				copied += len;
				length -= len;
				len = (int) Math.min(length, IO_BUFFER_SIZE);
			}
			return copied;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return copied;
	}

	public final boolean runOnOsX() {
		return getOsName().indexOf("mac os x", 0) != -1;
	}

	public final boolean runOnWindows() {
		return getOsName().indexOf("windows", 0) != -1;
	}

	public final boolean runOnLinux() {
		return getOsName().indexOf("linux", 0) != -1;
	}

	public final String getOsName() {
		String os = System.getProperty("os.name");
		if (os == null)
			return "unknow";
		return os;
	}

	protected final void clean(String file) {
		PrintWriter w;
		try {
			w = new PrintWriter(file);
			w.print("");
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	protected final void delete(String file) {
		File x = new File(file);
		if (Files.exists(x.toPath())) {
			try {
				Files.delete(x.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
