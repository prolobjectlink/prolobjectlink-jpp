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
package org.logicware.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class DatabaseGrantee implements Serializable {

	private final Map<DatabaseRole, DatabaseRight> roles;
	private final Map<DatabaseClass, DatabaseRight> rights;
	private static final long serialVersionUID = -3671269581803119411L;

	public DatabaseGrantee() {
		rights = new HashMap<DatabaseClass, DatabaseRight>();
		roles = new HashMap<DatabaseRole, DatabaseRight>();
	}

	public DatabaseGrantee(Map<DatabaseRole, DatabaseRight> roles, Map<DatabaseClass, DatabaseRight> rights) {
		this.rights = new HashMap<DatabaseClass, DatabaseRight>(rights);
		this.roles = new HashMap<DatabaseRole, DatabaseRight>(roles);
	}

	public final Map<DatabaseRole, DatabaseRight> getRoles() {
		return roles;
	}

	public final Map<DatabaseClass, DatabaseRight> getRights() {
		return rights;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rights == null) ? 0 : rights.hashCode());
		result = prime * result + ((roles == null) ? 0 : roles.hashCode());
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
		DatabaseGrantee other = (DatabaseGrantee) obj;
		if (rights == null) {
			if (other.rights != null)
				return false;
		} else if (!rights.equals(other.rights))
			return false;
		if (roles == null) {
			if (other.roles != null)
				return false;
		} else if (!roles.equals(other.roles))
			return false;
		return true;
	}

}
