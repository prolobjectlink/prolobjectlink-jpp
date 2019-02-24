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
package org.prolobjectlink.db;

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
