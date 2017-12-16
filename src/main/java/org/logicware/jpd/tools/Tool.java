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
package org.logicware.jpd.tools;

import java.io.InputStream;
import java.io.OutputStream;

abstract class Tool {

    /**
     * The block size for I/O operations.
     */
    public static final int IO_BUFFER_SIZE = 4 * 1024;

    /**
     * The max block size allowed for I/O operations.
     */
    public static final long MAX_IO_BUFFER_SIZE = Long.MAX_VALUE;

    /**
     * Copy all data from the input stream to the output stream. Both streams
     * are kept open.
     * 
     * @param in
     *            the input stream
     * @param out
     *            the output stream (null if writing is not required)
     * @param length
     *            the maximum number of bytes to copy
     * @return the number of bytes copied
     */
    public long copy(InputStream in, OutputStream out) {
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
}
