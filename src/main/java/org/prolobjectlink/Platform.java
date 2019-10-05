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
	 * Return the current user name.
	 * 
	 * @return the current user name.
	 * @since 1.0
	 */
	public String getUserName();

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
