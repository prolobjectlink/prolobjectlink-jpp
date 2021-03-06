/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package io.github.prolobjectlink.db.savepoint;

import java.io.Serializable;

import io.github.prolobjectlink.db.OperationType;

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
