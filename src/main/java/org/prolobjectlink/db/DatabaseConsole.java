/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
package org.prolobjectlink.db;

import java.util.Map;

import org.prolobjectlink.web.platform.WebServerControl;

/**
 * Represent the database system console.
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface DatabaseConsole {

	/**
	 * Return an integer that represent the default port for http web server control
	 * embedded in the platform.
	 * 
	 * @return default http port for web server listening
	 * @since 1.0
	 */
	public int getDefaultHttpPort();

	/**
	 * Return an instance of the http web server control embedded in the platform.
	 * The server control is showed in host system try after run invocation and from
	 * there the server can be started.
	 * 
	 * @param port port to web server listening
	 * @return an instance of the web server control
	 * @since 1.0
	 */
	public WebServerControl getWebServerControl(int port);

	/**
	 * Create a arguments map from a given string arguments array. Used for convert
	 * command line interface program arguments array to argument map.
	 * 
	 * @param args string arguments array
	 * @return arguments map
	 * @since 1.0
	 */
	public Map<String, String> getArguments(String[] args);

	/**
	 * <p>
	 * Command line interface program run method for this platform. Take the program
	 * arguments from main entry point and execute the job. Used like:
	 * </p>
	 * 
	 * <tt>
	 * public class Main{
	 * public static void main(String[] args) {
	 *	new Main().run(args);
	 *}
	 *
	 *}
	 * </tt>
	 * 
	 * @param args command line interface program arguments array
	 * @since 1.0
	 */
	public void run(String[] args);

	/**
	 * Used to print console usage.
	 * 
	 * @since 1.0
	 */
	public void printUsage();

}
