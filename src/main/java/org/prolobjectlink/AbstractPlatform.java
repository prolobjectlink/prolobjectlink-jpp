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
