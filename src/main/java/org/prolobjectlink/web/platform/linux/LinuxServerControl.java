/*-
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
