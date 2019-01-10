/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.web.platform.win32;

import java.io.IOException;
import java.util.Map;

import org.worklogic.db.DatabaseServer;
import org.worklogic.db.platform.win32.Win32DatabaseServer;
import org.worklogic.web.platform.AbstractWebControl;
import org.worklogic.web.platform.WebServer;
import org.worklogic.web.platform.WebServerControl;
import org.worklogic.web.platform.win32.jetty.Win32JettyWebServer;
import org.worklogic.web.platform.win32.tomcat.Win32TomcatWebServer;

public final class Win32ServerControl extends AbstractWebControl implements WebServerControl {

	public Win32ServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public void openBrowser(String url) {
		if (!started) {
			try {
				runtime.exec("cmd /c start " + url);
				started = true;
			} catch (final IOException e) {
			}
		}
	}

	public Map<String, String> getArguments(String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

	public void printUsage() {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) throws Exception {
		// TODO catch server type default is Jetty
		// TODO Database Server
		// TODO Port from args
		Win32DatabaseServer database = new Win32DatabaseServer();
		Win32JettyWebServer jetty = new Win32JettyWebServer(8888);
		Win32TomcatWebServer tomcat = new Win32TomcatWebServer(8080);
		new Win32ServerControl(tomcat, database).run(args);
	}

}
