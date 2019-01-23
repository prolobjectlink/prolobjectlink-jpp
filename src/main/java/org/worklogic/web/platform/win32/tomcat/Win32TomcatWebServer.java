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
package org.worklogic.web.platform.win32.tomcat;

import org.worklogic.web.platform.AbstractTomcatServer;
import org.worklogic.web.platform.TomcatWebServer;

public class Win32TomcatWebServer extends AbstractTomcatServer implements TomcatWebServer {

	public Win32TomcatWebServer(int serverPort) {
		super(serverPort);
	}

}
