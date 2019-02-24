/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.savepoint;

import java.io.Serializable;

import org.prolobjectlink.db.OperationType;

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
