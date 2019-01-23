/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.worklogic.db;

import java.io.Serializable;
import java.util.Collection;

public final class DatabaseResponse implements Serializable {

	private static final long serialVersionUID = -4194616833844740667L;
	private static final Class<?> VOID = Void.TYPE;

	private Serializable response;

	public DatabaseResponse() {
		// do nothing
	}

	public <R extends Serializable> void set(R response) {
		this.response = response;
	}

	public <R extends Serializable> R get() {
		return (R) response;
	}

	public void setVoid() {
		this.response = VOID;
	}

	@Override
	public String toString() {
		return "DatabaseResponse [response=" + response + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((response == null) ? 0 : response.hashCode());
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
		DatabaseResponse other = (DatabaseResponse) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		return true;
	}

	public void close() {
		if (response instanceof Collection) {
			((Collection<?>) response).clear();
		}
		response = null;
	}

}
