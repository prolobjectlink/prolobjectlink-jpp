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
