/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.database;

import java.net.URL;

public final class DatabaseFactory {

	public static DatabaseEngine newDatabase(URL url) {
		// TODO
		return null;
//		DatabaseEngine database = null;
//		if (url.getProtocol().equals("" + Protocol.MEMPDB + "")) {
//			database = MemoryHierarchical.newInstance(info, map);
//			System.out.println("Go fine Camilo???");
//		} else if (url.getProtocol().equals("" + Protocol.REMPDB + "")) {
//			database = RemoteHierarchical.newInstance(info, map);
//			System.out.println("Go fine Camilo???");
//		} else if (url.getProtocol().equals("" + Protocol.FILE + "")) {
//			database = EmbeddedHierarchical.newInstance(info, map);
//			System.out.println("Go fine Camilo???");
//		}
//		if (database != null) {
//			return database;
//		} else {
//			String message = "Not valid protocol in the URL.\n";
//			message += "Valid protocols are [memdb],[remdb],[file]";
//			throw new RuntimeError(message);
//		}
	}

	private DatabaseFactory() {
	}

}
