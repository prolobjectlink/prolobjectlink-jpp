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

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

public final class JDBCConnection extends JDBCWrapper implements Connection {

	//
	private final String url;
	private final Properties info;

	//
	private boolean readOnly;
	private boolean autoCommit;

	private Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();

	JDBCConnection(String url, Properties info) {
		this.info = info;
		this.url = url;
	}

	public Statement createStatement() throws SQLException {
		// TODO use to refactoring sql query lower case i.e.
		return null;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		// TODO use to refactoring sql query lower case i.e.
		return null;
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		// TODO use to refactoring sql query lower case i.e.
		return null;
	}

	public String nativeSQL(String sql) throws SQLException {
		// TODO use to refactoring sql query lower case i.e.
		return null;
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
	}

	public boolean getAutoCommit() throws SQLException {
		return autoCommit;
	}

	public void commit() throws SQLException {
		// TODO Auto-generated method stub

	}

	/**
	 * Consult the file loading persistent data and removing from memory all
	 * changes
	 */
	public void rollback() throws SQLException {
		// TODO Auto-generated method stub

	}

	public DatabaseMetaData getMetaData() throws SQLException {
		throw new SQLFeatureNotSupportedException("getMetaData()");
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() throws SQLException {
		return readOnly;
	}

	public void setCatalog(String catalog) throws SQLException {
		// TODO Auto-generated method stub

	}

	public String getCatalog() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setTransactionIsolation(int level) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getTransactionIsolation() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public SQLWarning getWarnings() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void clearWarnings() throws SQLException {
		// TODO Auto-generated method stub

	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return typeMap;
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.typeMap = map;
	}

	public void setHoldability(int holdability) throws SQLException {
		// TODO Auto-generated method stub

	}

	public int getHoldability() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public Savepoint setSavepoint() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub

	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// TODO Auto-generated method stub

	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setSchema(String schema) throws SQLException {
		throw new SQLFeatureNotSupportedException("setSchema(String)");
	}

	public String getSchema() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public void abort(Executor executor) throws SQLException {
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
	}

	public int getNetworkTimeout() throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getUrl() {
		return url;
	}

	public Properties getInfo() {
		return info;
	}

	@Override
	public String toString() {
		return "JDBCConnection [url=" + url + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		JDBCConnection other = (JDBCConnection) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	public void close() throws SQLException {
		// TODO Auto-generated method stub

	}

	public boolean isClosed() throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

}
