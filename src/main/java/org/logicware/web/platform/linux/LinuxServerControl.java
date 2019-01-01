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
package org.logicware.web.platform.linux;

import org.logicware.database.DatabaseServer;
import org.logicware.database.platform.linux.LinuxDatabaseServer;
import org.logicware.web.platform.WebServer;
import org.logicware.web.platform.WebServerControl;
import org.logicware.web.platform.linux.jetty.LinuxJettyWebServer;
import org.logicware.web.platform.linux.tomcat.LinuxTomcatWebServer;

public class LinuxServerControl extends LinuxPlatform implements WebServerControl {

	public LinuxServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public static void main(String[] args) throws Exception {
		// TODO catch server type default is Jetty
		// TODO Database Server
		// TODO Port from args
		LinuxDatabaseServer database = new LinuxDatabaseServer();
		LinuxJettyWebServer jetty = new LinuxJettyWebServer(8888);
		LinuxTomcatWebServer tomcat = new LinuxTomcatWebServer(8080);
		new LinuxServerControl(jetty, database).run(args);
	}

}
