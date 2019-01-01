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
package org.logicware.web.platform;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.logicware.Licenses;

public class AbstractJettyServer extends AbstractWebServer implements JettyWebServer {

	private final Server jettyServer = new Server();
	private final ServerConnector connector = new ServerConnector(jettyServer);

	public AbstractJettyServer(int serverPort) {
		super(serverPort);
		connector.setPort(serverPort);
		jettyServer.setConnectors(new Connector[] { connector });
	}

	public final String getLicense() {
		Package p = Server.class.getPackage();
		String quialify = p.getName();
		if (quialify.contains("org.mortbay.jetty")) {
			return Licenses.APACHE_V2;
		}
		return Licenses.EPL_V1;
	}

	public final String getVersion() {
		return Server.getVersion();
	}

	public final String getName() {
		return JETTY;
	}

}
