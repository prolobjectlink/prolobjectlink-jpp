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
package org.logicware.pdb.common;

import java.io.Serializable;
import java.util.Collection;

import org.logicware.pdb.DataTransferObject;
import org.logicware.pdb.DataTransferType;

public abstract class AbstractDataTransfer<Q extends Serializable, R extends Serializable>
		implements DataTransferObject<Q, R>, Serializable {

	private Q query;
	private R result;
	private final DataTransferType type;
	private static final long serialVersionUID = -8793751981186352447L;

	public AbstractDataTransfer(DataTransferType type, Q query) {
		this.query = query;
		this.type = type;
	}

	public void setBooleanResult(Boolean b) {
		result = (R) b;
	}

	public boolean getBooleanResult() {
		return (Boolean) result;
	}

	public final Q getQuery() {
		return query;
	}

	public final R getResult() {
		return result;
	}

	public final void setResult(R result) {
		this.result = result;
	}

	public final DataTransferType getType() {
		return type;
	}

	public final void close() {
		if (result instanceof Collection) {
			((Collection<?>) result).clear();
		}
		result = null;
		query = null;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [query=" + query + ", result=" + result + ", type=" + type + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int r = 1;
		r = prime * r + ((query == null) ? 0 : query.hashCode());
		r = prime * r + ((this.result == null) ? 0 : this.result.hashCode());
		r = prime * r + ((type == null) ? 0 : type.hashCode());
		return r;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractDataTransfer<?, ?> other = (AbstractDataTransfer<?, ?>) obj;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return type == other.type;
	}

}
