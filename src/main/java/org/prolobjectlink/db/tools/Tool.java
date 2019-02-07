/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.tools;

import java.io.InputStream;
import java.io.OutputStream;

import org.prolobjectlink.AbstractPlatform;
import org.prolobjectlink.logging.LoggerUtils;

public abstract class Tool extends AbstractPlatform {

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
			LoggerUtils.error(getClass(), "Some error occurss on copy", e);
		}
		return copied;
	}

}
