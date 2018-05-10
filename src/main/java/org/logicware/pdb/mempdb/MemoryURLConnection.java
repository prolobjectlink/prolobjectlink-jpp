/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.mempdb;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.logicware.pdb.HierarchicalCache;
import org.logicware.pdb.Protocol;

public class MemoryURLConnection extends URLConnection {

	protected MemoryURLConnection(URL url) {
		super(url);
	}

	@Override
	public void connect() throws IOException {
		// do nothing

	}

	@Override
	public String getContentType() {
		return Protocol.MEMPDB.name();
	}

	@Override
	public Object getContent() throws IOException {
		// TODO Auto-generated method stub
		return super.getContent();
	}

}
