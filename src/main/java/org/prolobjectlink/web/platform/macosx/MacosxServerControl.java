/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.web.platform.macosx;

import java.io.IOException;
import java.util.Map;

import org.prolobjectlink.db.DatabaseServer;
import org.prolobjectlink.db.platform.macosx.MacosxDatabaseServer;
import org.prolobjectlink.web.platform.AbstractWebControl;
import org.prolobjectlink.web.platform.WebServer;
import org.prolobjectlink.web.platform.WebServerControl;
import org.prolobjectlink.web.platform.macosx.jetty.MacosxJettyWebServer;
import org.prolobjectlink.web.platform.macosx.tomcat.MacosxTomcatWebServer;

public final class MacosxServerControl extends AbstractWebControl implements WebServerControl {

	public MacosxServerControl(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public void openBrowser(String url) {
		if (!started) {
			try {
				runtime.exec("open " + url);
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
		MacosxDatabaseServer database = new MacosxDatabaseServer();
		MacosxJettyWebServer jetty = new MacosxJettyWebServer(8888);
		MacosxTomcatWebServer tomcat = new MacosxTomcatWebServer(8080);
		new MacosxServerControl(jetty, database).run(args);
	}

}
