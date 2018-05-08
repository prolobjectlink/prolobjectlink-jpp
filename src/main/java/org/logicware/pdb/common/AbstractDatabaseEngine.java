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

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.DatabaseEngine;
import org.logicware.pdb.DatabaseRole;
import org.logicware.pdb.DatabaseUser;
import org.logicware.pdb.DefaultTransaction;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.Transaction;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;
import org.logicware.pdb.tools.Backup;
import org.logicware.pdb.tools.Restore;

public abstract class AbstractDatabaseEngine extends AbstractContainer implements DatabaseEngine {

	private final String name;
	private final Schema schema;
	private final String location;
	private final DatabaseUser owner;
	private final Transaction transaction;
	private final Map<String, DatabaseUser> users;
	private final Map<String, DatabaseRole> roles;
	private final ContainerFactory containerFactory;

	public AbstractDatabaseEngine(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory, String location, String name, Schema schema, DatabaseUser owner) {
		super(provider, properties, converter);
		this.roles = new HashMap<String, DatabaseRole>();
		this.users = new HashMap<String, DatabaseUser>();
		this.transaction = new DefaultTransaction(this);
		this.containerFactory = containerFactory;
		this.location = location;
		this.schema = schema;
		this.owner = owner;
		this.name = name;
	}

	public AbstractDatabaseEngine(DatabaseEngine db) {
		super(db.getProvider(), db.getProperties(), db.getConverter());
		this.roles = new HashMap<String, DatabaseRole>();
		this.users = new HashMap<String, DatabaseUser>();
		this.transaction = new DefaultTransaction(this);
		this.containerFactory = db.getContainerFactory();
		this.location = db.getLocation();
		this.schema = db.getSchema();
		this.owner = db.getOwner();
		this.name = db.getName();
	}

	public void backup(String directory, String zipFileName) {
		Backup backup = new Backup(getBaseLocation() + File.separator + getName());
		backup.createBackup(directory, zipFileName);
	}

	public void restore(String directory, String zipFileName) {
		Restore restore = new Restore();
		restore.restoreBackup(directory, zipFileName);
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final Transaction getTransaction() {
		return transaction;
	}

	public final String getLocation() {
		return location;
	}

	public final Collection<DatabaseUser> getUsers() {
		ArrayList<DatabaseUser> u = new ArrayList<DatabaseUser>();
		u.add(owner);
		u.addAll(users.values());
		return u;
	}

	public final Collection<DatabaseRole> getRoles() {
		return roles.values();
	}

	public final Schema getSchema() {
		return schema;
	}

	public final DatabaseUser getOwner() {
		return owner;
	}

	public final String getName() {
		return name;
	}

}
