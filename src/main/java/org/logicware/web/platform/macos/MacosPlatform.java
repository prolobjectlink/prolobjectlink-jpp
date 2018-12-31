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

import java.io.IOException;

import org.logicware.database.DatabaseServer;
import org.logicware.web.container.WebPlatform;
import org.logicware.web.container.WebServer;
import org.logicware.web.platform.AbstractWebControl;

public class MacosPlatform extends AbstractWebControl implements WebPlatform {

	public MacosPlatform(WebServer webServer, DatabaseServer databaseServer) {
		super(webServer, databaseServer);
	}

	public final void openBrowser(String url) {
		if (!started) {
			try {
				runtime.exec("open " + url);
				started = true;
			} catch (final IOException e) {
			}
		}
	}

}
