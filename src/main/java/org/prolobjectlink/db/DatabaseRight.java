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

public class DatabaseRight implements Serializable {

	public static final int FINDALL = 1;
	public static final int DELETE = 2;
	public static final int INSERT = 4;
	public static final int UPDATE = 8;
	public static final int ALL = FINDALL | DELETE | INSERT | UPDATE;
	private static final long serialVersionUID = -6836793238106398114L;

	private DatabaseClass databaseclass;
	private DatabaseGrantee grantee;
	private DatabaseRole role;
	private final int right;

	public DatabaseRight(int right) {
		this.right = right;
	}

	public DatabaseRight(int right, DatabaseClass databaseclass, DatabaseGrantee grantee, DatabaseRole role) {
		this.databaseclass = databaseclass;
		this.grantee = grantee;
		this.right = right;
		this.role = role;
	}

	public DatabaseClass getDatabaseclass() {
		return databaseclass;
	}

	public void setDatabaseclass(DatabaseClass databaseclass) {
		this.databaseclass = databaseclass;
	}

	public DatabaseGrantee getGrantee() {
		return grantee;
	}

	public void setGrantee(DatabaseGrantee grantee) {
		this.grantee = grantee;
	}

	public DatabaseRole getRole() {
		return role;
	}

	public void setRole(DatabaseRole role) {
		this.role = role;
	}

	public int getRight() {
		return right;
	}

}
