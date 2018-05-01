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
import java.sql.Driver;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.logicware.DatabaseService;

public final class JDBCConnection extends JDBCWrapper implements Connection {

	//
	private final String url;
	private final Driver driver;
	private Properties info;

	//
	private boolean closed;
	private int holdability;
	private boolean readOnly;
	private boolean autoCommit;
	private int isolationLevel;

	//
	private final DatabaseService database;

	//
	private final List<Savepoint> savepoints = new ArrayList<Savepoint>();

	//
	private Map<String, Class<?>> typeMap = new HashMap<String, Class<?>>();

	public JDBCConnection(String url, Properties info, Driver driver, DatabaseService database) {
		this.database = database;
		this.driver = driver;
		this.info = info;
		this.url = url;
	}

	public Statement createStatement() throws SQLException {
		throw new SQLFeatureNotSupportedException("createStatement()");
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException("prepareStatement(String sql)");
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException("prepareCall(String sql)");
	}

	public String nativeSQL(String sql) throws SQLException {
		throw new SQLFeatureNotSupportedException("nativeSQL(String sql)");
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		this.autoCommit = autoCommit;
	}

	public boolean getAutoCommit() throws SQLException {
		return autoCommit;
	}

	public void commit() throws SQLException {
		for (Savepoint savepoint : savepoints) {
			if (savepoint instanceof JDBCSavepoint) {
				((JDBCSavepoint) savepoint).commit();
			}
		}
	}

	public void rollback() throws SQLException {
		for (Savepoint savepoint : savepoints) {
			if (savepoint instanceof JDBCSavepoint) {
				((JDBCSavepoint) savepoint).rollback();
			}
		}
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return new JDBCMetadata(database.getSchema(), driver, this);
	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() throws SQLException {
		return readOnly;
	}

	public void setCatalog(String catalog) throws SQLException {
		throw new SQLFeatureNotSupportedException("setCatalog(String catalog)");
	}

	public String getCatalog() throws SQLException {
		throw new SQLFeatureNotSupportedException("getCatalog()");
	}

	public void setTransactionIsolation(int level) throws SQLException {
		this.isolationLevel = level;
	}

	public int getTransactionIsolation() throws SQLException {
		return isolationLevel;
	}

	public SQLWarning getWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException("getWarnings()");
	}

	public void clearWarnings() throws SQLException {
		throw new SQLFeatureNotSupportedException("clearWarnings()");
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLFeatureNotSupportedException("createStatement(int resultSetType, int resultSetConcurrency) ");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
			throws SQLException {
		throw new SQLFeatureNotSupportedException(
				"prepareStatement(String sql, int resultSetType, int resultSetConcurrency)");
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency) throws SQLException {
		throw new SQLFeatureNotSupportedException(
				"prepareCall(String sql, int resultSetType, int resultSetConcurrency)");
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException {
		return typeMap;
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
		this.typeMap = map;
	}

	public void setHoldability(int holdability) throws SQLException {
		this.holdability = holdability;
	}

	public int getHoldability() throws SQLException {
		return holdability;
	}

	public Savepoint setSavepoint() throws SQLException {
		return setSavepoint("" + System.currentTimeMillis() + "");
	}

	public Savepoint setSavepoint(String name) throws SQLException {
		Savepoint savepoint = new JDBCSavepoint(savepoints.size(), name, database.getTransaction());
		savepoints.add(savepoint);
		return savepoint;
	}

	public void rollback(Savepoint savepoint) throws SQLException {
		// int index=Collections.binarySearch(savepoints, savepoint);
		for (int i = savepoints.indexOf(savepoint) + 1; i < savepoints.size(); i++) {
			Savepoint oldSavepoint = savepoints.remove(i);
			if (oldSavepoint instanceof JDBCSavepoint) {
				JDBCSavepoint sp = (JDBCSavepoint) oldSavepoint;
				sp.rollback();
			}
		}
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException {
		// int index=Collections.binarySearch(savepoints, savepoint);
		for (int i = savepoints.indexOf(savepoint); i < savepoints.size(); i++) {
			savepoints.remove(i);
		}
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)
			throws SQLException {
		throw new SQLFeatureNotSupportedException(
				"createStatement(int resultSetType, int resultSetConcurrency, int resultSetHoldability)");
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new SQLFeatureNotSupportedException(
				"prepareStatement(String sql, int resultSetType, int resultSetConcurrency,int resultSetHoldability)");
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
			int resultSetHoldability) throws SQLException {
		throw new SQLFeatureNotSupportedException(
				"prepareCall(String sql, int resultSetType, int resultSetConcurrency,int resultSetHoldability)");
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public Clob createClob() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public Blob createBlob() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public NClob createNClob() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public SQLXML createSQLXML() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public boolean isValid(int timeout) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public void setClientInfo(String name, String value) throws SQLClientInfoException {
		info.setProperty(name, value);
	}

	public void setClientInfo(Properties properties) throws SQLClientInfoException {
		this.info = properties;
	}

	public String getClientInfo(String name) throws SQLException {
		return info.getProperty(name);
	}

	public Properties getClientInfo() throws SQLException {
		return info;
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public void setSchema(String schema) throws SQLException {
		throw new SQLFeatureNotSupportedException("setSchema(String)");
	}

	public String getSchema() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public void abort(Executor executor) throws SQLException {
		throw new SQLFeatureNotSupportedException("abort(Executor executor)");
	}

	public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
	}

	public int getNetworkTimeout() throws SQLException {
		throw new SQLFeatureNotSupportedException("getSchema()");
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

	public boolean isClosed() throws SQLException {
		return closed;
	}

	public void close() throws SQLException {
		closed = true;
	}

}
