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
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

public final class JDBCDataSource extends JDBCCommonDataSource implements DataSource {

	private final String url;
	private final String username;
	private final String password;

	public JDBCDataSource(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public Connection getConnection() throws SQLException {
		return getConnection(username, password);
	}

	public Connection getConnection(String username, String password) throws SQLException {
		try {
			Class.forName(JDBCDriver.class.getCanonicalName());
		} catch (ClassNotFoundException e) {
			System.err.println("DataSource unable to load JDBC Driver");
		}
		return DriverManager.getConnection(url, username, password);
	}

}
