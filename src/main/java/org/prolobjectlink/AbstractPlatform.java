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
package org.prolobjectlink;

/**
 * Common implementation of {@link Platform} interface.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public abstract class AbstractPlatform implements Platform {

	protected static final int IO_BUFFER_SIZE = 4 * 1024;
	protected static final long MAX_IO_BUFFER_SIZE = Long.MAX_VALUE;

	public final String getUserName() {
		return System.getProperty("user.name");
	}

	public final String getJavaVersion() {
		return System.getProperty("java.version");
	}

	public final String getJavaVendor() {
		return System.getProperty("java.vendor");
	}

	public final String getJavaName() {
		return System.getProperty("java.vm.name");
	}

	public final String getJavaHome() {
		return System.getProperty("java.home");
	}

	public final boolean runOnOsX() {
		return getOsName().equals("Mac OS X") || getOsName().equals("Darwin");
	}

	public final boolean runOnWindows() {
		return getOsName().startsWith("Windows");
	}

	public final boolean runOnLinux() {
		return getOsName().equals("Linux");
	}

	public final String getOsName() {
		String os = System.getProperty("os.name");
		if (os == null)
			return "unknow";
		return os;
	}

	public String getClassPath() {
		return System.getenv("java.class.path");
	}

	public final String getPath() {
		return System.getenv("Path");
	}

	public final String getPathSeparator() {
		return System.getProperty("path.separator");
	}

	public final String getPathExt() {
		return System.getenv("PATHEXT");
	}

	public final Runtime getRuntime() {
		return Runtime.getRuntime();
	}

	public final String getUserHome() {
		return System.getProperty("user.home");
	}

	public final String getUserDir() {
		return System.getProperty("user.dir");
	}

	public final String getWorkDir() {
		return System.getProperty("user.dir");
	}

	public final void checkJDKInstalation() {
		if (getJavaHome() == null || getJavaHome().isEmpty()) {
			throw new RuntimeException("You need install JDK 1.7 or above");
		}
	}

	public final String getArch() {
		return System.getProperty("os.arch");
	}

}
