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

import org.apache.catalina.startup.Tomcat;
import org.logicware.Licenses;

public class AbstractTomcatServer extends AbstractWebServer implements TomcatWebServer {

	private final Tomcat tomcatServer = new Tomcat();

	public AbstractTomcatServer(int serverPort) {
		super(serverPort);
		tomcatServer.setPort(serverPort);
	}

	public final String getLicense() {
		return Licenses.APACHE_V2;
	}

	public final String getVersion() {
		// TODO
		return "Tomcat";
	}

	public final String getName() {
		return TOMCAT;
	}

}
