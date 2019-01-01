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
package org.logicware.web.platform.macos.jetty;

import org.logicware.web.platform.AbstractJettyServer;
import org.logicware.web.platform.JettyWebServer;

public class MacosJettyWebServer extends AbstractJettyServer implements JettyWebServer {

	public MacosJettyWebServer(int serverPort) {
		super(serverPort);
	}

}
