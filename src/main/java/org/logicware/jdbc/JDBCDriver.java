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
package org.logicware.jdbc;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public final class JDBCDriver implements Driver {

	private static final int MAJOR = 1;
	private static final int MINOR = 0;

	static final String URL_PREFIX = "jdbc:prolobjectlink:store:";
	public static final String JPI_PROVIDER_PROPERTY = "org.logicware.prolobjectlink.jpi.provider";
	// private static final ObjectReflector REFLECTOR = new ObjectReflector();

	public Connection connect(String url, Properties info) throws SQLException {
		url = url.replace(JDBCDriver.URL_PREFIX, "");
		// String c = String.valueOf(info.getProperty(JPI_PROVIDER_PROPERTY));
		// PrologProvider provider = PrologFactory.newProvider(c);
		// PrologEngine engine = provider.newEngine();
		return new JDBCConnection(url, info);
	}

	public boolean acceptsURL(String url) throws SQLException {
		return url.startsWith(URL_PREFIX);
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[0];
	}

	public int getMajorVersion() {
		return MAJOR;
	}

	public int getMinorVersion() {
		return MINOR;
	}

	public boolean jdbcCompliant() {
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new SQLFeatureNotSupportedException("getParentLogger()");
	}

}
