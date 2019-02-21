/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.web.platform.linux;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.prolobjectlink.db.DatabaseServer;
import org.prolobjectlink.web.platform.AbstractWebControl;
import org.prolobjectlink.web.platform.WebServer;
import org.prolobjectlink.web.platform.WebServerControl;

public abstract class LinuxServerControl extends AbstractWebControl implements WebServerControl {

	public LinuxServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public void openBrowser(String url) {

		// See if the default browser is Konqueror by resolving the symlink.
		boolean isDefaultKonqueror = false;
		try {
			// Find out the location of the x-www-browser link from path.
			Process process = runtime.exec("which x-www-browser");
			BufferedInputStream ins = new BufferedInputStream(process.getInputStream());
			BufferedReader bufreader = new BufferedReader(new InputStreamReader(ins));
			String defaultLinkPath = bufreader.readLine();
			ins.close();

			// The path is null if the link did not exist.
			if (defaultLinkPath != null) {
				// See if the default browser is Konqueror.
				File file = new File(defaultLinkPath);
				String canonical = file.getCanonicalPath();
				if (canonical.indexOf("konqueror") != -1) {
					isDefaultKonqueror = true;
				}
			}
		} catch (IOException e1) {
			// The symlink was probably not found, so this is ok.
		}

		// Try x-www-browser, which is symlink to the default browser,
		// except if we found that it is Konqueror.
		if (!started && !isDefaultKonqueror) {
			try {
				runtime.exec("x-www-browser " + url);
				started = true;
			} catch (final IOException e) {
			}
		}

		// Try firefox
		if (!started) {
			try {
				runtime.exec("firefox " + url);
				started = true;
			} catch (final IOException e) {
			}
		}

		// Try mozilla
		if (!started) {
			try {
				runtime.exec("mozilla " + url);
				started = true;
			} catch (final IOException e) {
			}
		}

		// Try konqueror
		if (!started) {
			try {
				runtime.exec("konqueror " + url);
				started = true;
			} catch (final IOException e) {
			}
		}
	}

}
