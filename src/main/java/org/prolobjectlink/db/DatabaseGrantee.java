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
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
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
