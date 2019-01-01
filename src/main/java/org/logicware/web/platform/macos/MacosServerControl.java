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
package org.logicware.web.platform.macos;

import org.logicware.db.DatabaseServer;
import org.logicware.db.platform.macos.MacosDatabaseServer;
import org.logicware.web.platform.WebServer;
import org.logicware.web.platform.WebServerControl;
import org.logicware.web.platform.macos.jetty.MacosJettyWebServer;
import org.logicware.web.platform.macos.tomcat.MacosTomcatWebServer;

public class MacosServerControl extends MacosPlatform implements WebServerControl {

	public MacosServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public static void main(String[] args) throws Exception {
		// TODO catch server type default is Jetty
		// TODO Database Server
		// TODO Port from args
		MacosDatabaseServer database = new MacosDatabaseServer();
		MacosJettyWebServer jetty = new MacosJettyWebServer(8888);
		MacosTomcatWebServer tomcat = new MacosTomcatWebServer(8080);
		new MacosServerControl(jetty, database).run(args);
	}

}
