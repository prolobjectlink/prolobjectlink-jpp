/*
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
package org.worklogic.db.savepoint;

import java.io.Serializable;

import org.worklogic.db.OperationType;

public final class SavePointRecord implements Comparable<SavePointRecord>, Serializable {

	private static final long serialVersionUID = 6467074863113661503L;
	private final OperationType commitType;
	private final OperationType rollbackType;
	private final Class<?> clazz;
	private final Object record;
	private final long time;

	public SavePointRecord(OperationType commitType, OperationType rollbackType, Class<?> clazz, Object record) {
		this.time = System.currentTimeMillis();
		this.rollbackType = rollbackType;
		this.commitType = commitType;
		this.record = record;
		this.clazz = clazz;
	}

	public final OperationType getCommitType() {
		return commitType;
	}

	public final OperationType getRollbackType() {
		return rollbackType;
	}

	public final Object getRecord() {
		return record;
	}

	public final Class<?> getRecordClass() {
		return clazz;
	}

	public final long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "SavePointRecord [record=" + record + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((record == null) ? 0 : record.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
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
		SavePointRecord other = (SavePointRecord) obj;
		if (record == null) {
			if (other.record != null)
				return false;
		} else if (!record.equals(other.record)) {
			return false;
		}
		return time == other.time;
	}

	public int compareTo(SavePointRecord o) {
		if (time > o.time) {
			return (int) Math.abs(time - o.time);
		} else if (time < o.time) {
			return (int) (Math.abs(time - o.time) * -1);
		}
		return 0;
	}

}
