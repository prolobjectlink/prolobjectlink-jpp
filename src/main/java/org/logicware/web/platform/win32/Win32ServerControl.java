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
package org.logicware.web.platform.win32;

import org.logicware.database.DatabaseServer;
import org.logicware.database.platform.win32.Win32DatabaseServer;
import org.logicware.web.platform.WebServer;
import org.logicware.web.platform.WebServerControl;
import org.logicware.web.platform.win32.jetty.Win32JettyWebServer;
import org.logicware.web.platform.win32.tomcat.Win32TomcatWebServer;

public class Win32ServerControl extends Win32Platform implements WebServerControl {

	public Win32ServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public static void main(String[] args) throws Exception {
		// TODO catch server type default is Jetty
		// TODO Database Server
		// TODO Port from args
		Win32DatabaseServer database = new Win32DatabaseServer();
		Win32JettyWebServer jetty = new Win32JettyWebServer(8888);
		Win32TomcatWebServer tomcat = new Win32TomcatWebServer(8080);
		new Win32Platform(tomcat, database).run(args);
	}

}
