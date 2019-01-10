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
package org.worklogic.web.platform.macosx.jetty;

import org.worklogic.web.platform.AbstractJettyServer;
import org.worklogic.web.platform.JettyWebServer;

public class MacosxJettyWebServer extends AbstractJettyServer implements JettyWebServer {

	public MacosxJettyWebServer(int serverPort) {
		super(serverPort);
	}

}
