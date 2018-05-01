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

import static org.logicware.logging.LoggerConstants.SQL_ERROR;
import java.sql.SQLException;
import java.sql.Savepoint;

import org.logicware.Transaction;
import org.logicware.logging.LoggerUtils;

public final class JDBCSavepoint extends JDBCWrapper implements Savepoint, Comparable<Savepoint> {

	private final int savepointId;
	private final String savepointName;
	private final Transaction transaction;

	public JDBCSavepoint(int savepointId, String savepointName, Transaction transaction) {
		this.transaction = transaction;
		this.savepointId = savepointId;
		this.savepointName = savepointName;
	}

	public int getSavepointId() throws SQLException {
		return savepointId;
	}

	public String getSavepointName() throws SQLException {
		return savepointName;
	}

	public int compareTo(Savepoint o) {
		int r = 0;
		try {
			if (savepointId < o.getSavepointId()) {
				r = -1;
			} else if (savepointId > o.getSavepointId()) {
				r = 1;
			}
			r = savepointName.compareTo(o.getSavepointName());
		} catch (SQLException e) {
			LoggerUtils.error(getClass(), SQL_ERROR, e);
		}
		return r;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + savepointId;
		result = prime * result + ((savepointName == null) ? 0 : savepointName.hashCode());
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
		JDBCSavepoint other = (JDBCSavepoint) obj;
		if (savepointId != other.savepointId)
			return false;
		if (savepointName == null) {
			if (other.savepointName != null)
				return false;
		} else if (!savepointName.equals(other.savepointName))
			return false;
		return true;
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final void commit() {
		transaction.commit();
	}

	public final void rollback() {
		transaction.rollback();
	}

}
