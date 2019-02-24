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
 * Represent the platform where the system is running. The platform meanings is
 * the application architecture stack until java virtual machine runtime. The
 * stacked architecture elements are Operating System (OS) and Java Virtual
 * Machine (JVM).
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Platform {

	public void checkJDKInstalation();

	/**
	 * Return the java vm name. Is a shortcut to
	 * {@code System.getProperty("java.vm.name");}
	 * 
	 * @return the java version
	 * @since 1.0
	 */
	public String getJavaName();

	/**
	 * Return the java version. Is a shortcut to
	 * {@code System.getProperty("java.version");}
	 * 
	 * @return the java version
	 * @since 1.0
	 */
	public String getJavaVersion();

	/**
	 * Return the java vendor. Is a shortcut to
	 * {@code System.getProperty("java.vendor");}
	 * 
	 * @return the java vendor
	 * @since 1.0
	 */
	public String getJavaVendor();

	/**
	 * Check if the host operating system name refer to Windows OS.
	 * 
	 * @return true if the host operating system name refer to Windows OS and false
	 *         otherwise.
	 * @since 1.0
	 */
	public boolean runOnWindows();

	/**
	 * Check if the host operating system name refer to Linux OS.
	 * 
	 * @return true if the host operating system name refer to Linux OS and false
	 *         otherwise.
	 * @since 1.0
	 */
	public boolean runOnLinux();

	/**
	 * Check if the host operating system name refer to OsX.
	 * 
	 * @return true if the host operating system name refer to OsX and false
	 *         otherwise.
	 * @since 1.0
	 */
	public boolean runOnOsX();

	/**
	 * Return the host operating system name. Is a shortcut to
	 * {@code System.getProperty("os.name");}.
	 * 
	 * @return the host operating system name.
	 * @since 1.0
	 */
	public String getOsName();

	public String getJavaHome();

	public String getPath();

	public String getPathSeparator();

	public String getPathExt();

	public Runtime getRuntime();

	public String getUserHome();

	public String getUserDir();

	public String getWorkDir();

	public String getArch();

}
