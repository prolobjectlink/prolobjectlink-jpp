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

import org.logicware.Licenses;
import org.mortbay.jetty.Server;

public class AbstractJettyServer extends AbstractWebServer implements JettyWebServer {

	private final Server jettyServer;

	public AbstractJettyServer(int serverPort) {
		super(serverPort);
		jettyServer = new Server(serverPort);
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
