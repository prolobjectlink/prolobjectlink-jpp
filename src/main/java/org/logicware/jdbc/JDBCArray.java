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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Arrays;
import java.util.Map;

final class JDBCArray implements Array {

	private final Object[] array;
	private final Class<?> componentType;

	JDBCArray(Object[] array, Class<?> componentType) {
		this.array = array;
		this.componentType = componentType;
	}

	public String getBaseTypeName() throws SQLException {
		throw new SQLFeatureNotSupportedException("getBaseTypeName()");
	}

	public int getBaseType() throws SQLException {
		throw new SQLFeatureNotSupportedException("getBaseType()");
	}

	public Object getArray() throws SQLException {
		return getArray(null);
	}

	public Object getArray(Map<String, Class<?>> map) throws SQLException {
		return array;
	}

	public Object getArray(long index, int count) throws SQLException {
		return getArray(index, count, null);
	}

	public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
		// check index and count
		// check can cast index to int
		Object[] result = new Object[count];
		for (int i = (int) index; i < count; i++) {
			result[i] = array[i];
		}
		return result;
	}

	public ResultSet getResultSet() throws SQLException {
		return getResultSet(null);
	}

	public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
		return new JDBCResultSet(array);
	}

	public ResultSet getResultSet(long index, int count) throws SQLException {
		return getResultSet(index, count, null);
	}

	public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
		return new JDBCResultSet((Object[]) getArray(index, count));
	}

	public void free() throws SQLException {
		for (int i = 0; i < array.length; i++) {
			array[i] = null;
		}
	}

	@Override
	public String toString() {
		return Arrays.toString(array);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(array);
		result = prime * result + ((componentType == null) ? 0 : componentType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JDBCArray other = (JDBCArray) obj;
		if (!Arrays.equals(array, other.array))
			return false;
		if (componentType == null) {
			if (other.componentType != null)
				return false;
		} else if (!componentType.equals(other.componentType))
			return false;
		return true;
	}

}
