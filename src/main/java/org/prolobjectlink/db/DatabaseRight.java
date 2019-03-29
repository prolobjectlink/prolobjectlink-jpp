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
