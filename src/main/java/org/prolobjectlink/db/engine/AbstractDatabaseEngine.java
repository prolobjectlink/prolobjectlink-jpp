/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db.engine;

import static org.prolobjectlink.db.XmlParser.XML;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.spi.PersistenceUnitInfo;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.DatabaseEngine;
import org.prolobjectlink.db.DatabaseRole;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.Predicate;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.container.AbstractContainer;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.tools.Backup;
import org.prolobjectlink.db.tools.Restore;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractDatabaseEngine extends AbstractContainer implements DatabaseEngine {

	private URL url;
	private final String name;
	private final Schema schema;
	private final String location;
	private final DatabaseUser owner;
	protected static final char SEPARATOR = '/';
	private final Map<String, DatabaseUser> users;
	private final Map<String, DatabaseRole> roles;
	private final ContainerFactory containerFactory;
	private final Map<String, PersistenceUnitInfo> units;

	private static final Backup backuper = new Backup();
	private static final Restore restorer = new Restore();

	protected static final String URL_PREFIX = "jdbc:prolobjectlink:";
	protected static final URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	public AbstractDatabaseEngine(Settings settings, String location, String name, Schema schema, DatabaseUser owner) {
		super(settings.getProvider(), settings, settings.getObjectConverter());
		this.units = new HashMap<String, PersistenceUnitInfo>();
		this.containerFactory = settings.getContainerFactory();
		this.roles = new HashMap<String, DatabaseRole>();
		this.users = new HashMap<String, DatabaseUser>();
		try {
			this.url = new URL("file:/" + location);
		} catch (MalformedURLException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
			this.url = null;
		}
		this.location = location;
		this.schema = schema;
		this.owner = owner;
		this.name = name;
	}

	public AbstractDatabaseEngine(Settings settings, URL url, String name, Schema schema, DatabaseUser owner) {
		super(settings.getProvider(), settings, settings.getObjectConverter());
		this.units = new HashMap<String, PersistenceUnitInfo>();
		this.containerFactory = settings.getContainerFactory();
		this.roles = new HashMap<String, DatabaseRole>();
		this.users = new HashMap<String, DatabaseUser>();
		this.location = url.getPath();
		this.schema = schema;
		this.owner = owner;
		this.name = name;
		this.url = url;
	}

	public AbstractDatabaseEngine(DatabaseEngine db) {
		super(db.getProvider(), db.getProperties(), db.getConverter());
		this.roles = new HashMap<String, DatabaseRole>();
		this.users = new HashMap<String, DatabaseUser>();
		this.containerFactory = db.getContainerFactory();
		this.units = db.getPersistenceUnits();
		this.location = db.getLocation();
		this.schema = db.getSchema();
		this.owner = db.getOwner();
		this.name = db.getName();
		this.url = db.getURL();
	}

	public void backup(String directory, String zipFileName) {
		backuper.createBackup(getLocation(), directory, zipFileName);
	}

	public void restore(String directory, String zipFileName) {
		restorer.restoreBackup(directory, zipFileName);
	}

	public Object find(String string) {
		return createQuery(string).getSolution();
	}

	public Object find(String functor, Object... args) {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = JavaReflect.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			JavaReflect.writeValue(fields[i], instance, args[i]);
		}
		return find(instance);
	}

	public <O> O find(O o) {
		return createQuery(o).getSolution();
	}

	public <O> O find(Class<O> clazz) {
		return createQuery(clazz).getSolution();
	}

	public <O> O find(Predicate<O> predicate) {
		return createQuery(predicate).getSolution();
	}

	public List<Object> findAll(String string) {
		return createQuery(string).getSolutions();
	}

	public final List<Object> findAll(String functor, Object... args) {
		Class<?> clazz = classOf(functor, args.length);
		Object instance = JavaReflect.newInstance(clazz);
		Field[] fields = clazz.getDeclaredFields();
		checkProcedureInvokation(functor, fields, args);
		for (int i = 0; i < fields.length; i++) {
			JavaReflect.writeValue(fields[i], instance, args[i]);
		}
		return findAll(instance);
	}

	public <O> List<O> findAll(O o) {
		return createQuery(o).getSolutions();
	}

	public <O> List<O> findAll(Class<O> clazz) {
		return createQuery(clazz).getSolutions();
	}

	public <O> List<O> findAll(Predicate<O> predicate) {
		return createQuery(predicate).getSolutions();
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
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

	public final Map<String, PersistenceUnitInfo> getPersistenceUnits() {
		return units;
	}

	public final String getRootLocation() {
		return url.getPath();
	}

	public final List<Class<?>> classes() {
		return getSchema().getJavaClasses();
	}

	public final String getDatabaseLocation() {
		return getLocation() + SEPARATOR + "database";
	}

	public final URL getURL() {
		return url;
	}

}
