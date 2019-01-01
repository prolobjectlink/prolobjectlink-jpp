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
package org.logicware.database.platform.win32;

import java.io.IOException;

import org.logicware.database.DatabaseServer;
import org.logicware.database.DatabaseServerType;

public class Win32DatabaseServer implements DatabaseServer {

	public void startup() throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

	}

	public DatabaseServerType getType() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getURL() {
		// TODO Auto-generated method stub
		return null;
	}

	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
