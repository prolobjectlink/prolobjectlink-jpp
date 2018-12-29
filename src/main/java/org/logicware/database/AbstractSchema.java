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

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.logicware.Direction;
import org.logicware.database.prolog.PrologDatabaseFunction;
import org.logicware.database.prolog.PrologDatabaseTrigger;
import org.logicware.database.prolog.PrologDatabaseView;
import org.logicware.prolog.PrologEngine;
import org.logicware.prolog.PrologProvider;

public abstract class AbstractSchema implements Schema {

	private int version;
	private final String location;
	private final DatabaseUser owner;

	//
	private final transient Storage storage;
	private final transient PrologProvider provider;
	private final transient ContainerFactory containerFactory;

	//
	private final Map<String, DatabaseView> views;
	private final Map<String, DatabaseUser> users;
	private final Map<String, DatabaseClass> classes;
	private final Map<String, DatabaseTrigger> triggers;
	private final Map<String, DatabaseFunction> functions;
	private final Map<String, DatabaseSequence> sequences;

	private static final long serialVersionUID = 2324575651388389914L;

	private DatabaseClass newDatabaseClass(Class<?> clazz, String comment) {
		DatabaseClass c = new DatabaseClass(clazz, comment, this);
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();
			Class<?> type = field.getType();
			c.addField(name, "", i, type);
		}
		return c;
	}

	private DatabaseClass newDatabaseClass(String clazz, String comment) {
		return new DatabaseClass(clazz, comment, this);
	}

	private DatabaseFunction newDatabaseFunction(String name, String comment) {
		String path0 = location.substring(0, location.lastIndexOf(File.separatorChar));
		String path1 = path0.replace(File.separatorChar, '/') + "/functions.pl";
		return new PrologDatabaseFunction(name, comment, this, path1, provider);
	}

	private DatabaseTrigger newDatabaseTrigger(String name, String comment) {
		String path0 = location.substring(0, location.lastIndexOf(File.separatorChar));
		String path1 = path0.replace(File.separatorChar, '/') + "/triggers.pl";
		return new PrologDatabaseTrigger(name, comment, this, path1, provider);
	}

	private DatabaseView newDatabaseView(Class<?> target, String comment) {
		String path0 = location.substring(0, location.lastIndexOf(File.separatorChar));
		String path1 = path0.replace(File.separatorChar, '/') + "/views.pl";
		return new PrologDatabaseView(path1, target, comment, this, provider);
	}

	public AbstractSchema(String location, PrologProvider provider, ContainerFactory factory, DatabaseUser owner) {
		this.storage = factory.createStorage(location + File.separator + "metadata" + File.separator + "schema.pl");
		this.functions = new LinkedHashMap<String, DatabaseFunction>();
		this.sequences = new LinkedHashMap<String, DatabaseSequence>();
		this.triggers = new LinkedHashMap<String, DatabaseTrigger>();
		this.classes = new LinkedHashMap<String, DatabaseClass>();
		this.users = new LinkedHashMap<String, DatabaseUser>();
		this.views = new LinkedHashMap<String, DatabaseView>();
		this.location = location + File.separator + "metadata";
		this.containerFactory = factory;
		this.provider = provider;
		this.owner = owner;
		this.version = 0;
	}

	public final String generate() {
		StringBuilder buffer = new StringBuilder();
		for (DatabaseClass clazz : classes.values()) {
			buffer.append(clazz.generate());
		}
		return "" + buffer + "";
	}

	public final List<Class<?>> compile() {
		int size = classes.size();
		List<Class<?>> l = new ArrayList<Class<?>>(size);
		DynamicClassLoader dcl = new DynamicClassLoader();
		for (DatabaseClass c : classes.values()) {
			l.add(dcl.defineClass(c.getName(), c.compile()));
		}
		return l;
	}

	public final boolean isRelationalType(Class<?> type) {
		return !isJavaLangType(type) && isJavaUtilType(type);
	}

	public final boolean isJavaUtilType(Class<?> type) {
		return isSet(type) || isList(type) || isCollection(type) || isMap(type);
	}

	public final boolean isMap(Class<?> type) {
		return type.equals(Map.class);
	}

	public final boolean isCollection(Class<?> type) {
		return type.equals(Collection.class);
	}

	public final boolean isList(Class<?> type) {
		return type.equals(List.class);
	}

	public final boolean isSet(Class<?> type) {
		return type.equals(Set.class);
	}

	public final boolean isJavaLangType(Class<?> type) {
		Package langPackage = Object.class.getPackage();
		Package typePackage = type.getPackage();
		return typePackage.equals(langPackage);
	}

	public final DatabaseClass addClass(Class<?> clazz, String comment) {
		DatabaseClass c = newDatabaseClass(clazz, comment);
		classes.put(clazz.getName(), c);
		return c;
	}

	/**
	 * Add a class to the current schema
	 * 
	 * @param className name for the class
	 * @return database class instance.
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className, String comment) {
		DatabaseClass c = newDatabaseClass(className, comment);
		classes.put(className, c);
		return c;
	}

	/**
	 * Add a class to the current schema and set a parent super class.
	 * 
	 * @param className  name for the class
	 * @param superClass parent superclass
	 * @return database class instance.
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className, String comment, DatabaseClass superClass) {
		DatabaseClass c = newDatabaseClass(className, comment);
		classes.put(className, c.setSuperClass(superClass));
		return c;
	}

	/**
	 * Add a class to the current schema and set a parent super classes.
	 * 
	 * @param className    name for the class
	 * @param superClasses parent super classes
	 * @return database class instance
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className, String comment, DatabaseClass... superClasses) {
		DatabaseClass c = newDatabaseClass(className, comment);
		if (superClasses != null && superClasses.length >= 1) {
			c.setSuperClass(superClasses[0]);
			for (int i = 1; i < superClasses.length; i++) {
				DatabaseClass superClass = superClasses[i];
				c.addSuperClass(superClass);
			}
		}
		classes.put(className, c);
		return c;
	}

	public final DatabaseClass addAbstractClass(Class<?> clazz, String comment) {
		DatabaseClass c = newDatabaseClass(clazz, comment);
		c.setAbstract(true).setJavaClass(clazz);
		classes.put(clazz.getSimpleName(), c);
		return c;
	}

	public final DatabaseClass addAbstractClass(String n, String comment) {
		DatabaseClass c = newDatabaseClass(n, comment);
		classes.put(n, c.setAbstract(true));
		return c;
	}

	public final DatabaseClass addAbstractClass(String className, String comment, DatabaseClass superClass) {
		DatabaseClass c = addClass(className, comment, superClass);
		c.setAbstract(true);
		return c;
	}

	public final DatabaseClass addAbstractClass(String className, String comment, DatabaseClass... superClasses) {
		DatabaseClass c = addClass(className, comment, superClasses);
		c.setAbstract(true);
		return c;
	}

	public final Schema removeClass(String className) {
		classes.remove(className);
		return this;
	}

	public final boolean containsClass(String className) {
		return classes.containsKey(className);
	}

	public final DatabaseClass getClass(Class<?> clazz) {
		return getClass(clazz.getName());
	}

	public final DatabaseClass getClass(String className) {
		return classes.get(className);
	}

	public final DatabaseClass getOrAddClass(String className) {
		DatabaseClass clazz = getClass(className);
		if (clazz != null) {
			return clazz;
		}
		return addClass(className, "");
	}

	public final DatabaseClass getOrAddClass(Class<?> clazz) {
		DatabaseClass c = getClass(clazz);
		if (c != null) {
			return c;
		}
		return addClass(clazz, "");
	}

	public final DatabaseClass getOrAddClass(String className, DatabaseClass superClass) {
		DatabaseClass c = getOrAddClass(className);
		c.setSuperClass(superClass);
		return c;
	}

	public final DatabaseClass getOrAddClass(String className, DatabaseClass... superClasses) {
		DatabaseClass c = getOrAddClass(className);
		if (superClasses != null && superClasses.length >= 1) {
			c.setSuperClass(superClasses[0]);
			for (int i = 1; i < superClasses.length; i++) {
				DatabaseClass superClass = superClasses[i];
				c.addSuperClass(superClass);
			}
		}
		return c;
	}

	public final Collection<DatabaseClass> getClasses() {
		return classes.values();
	}

	public final int countClasses() {
		return classes.size();
	}

	public final DatabaseFunction addFunction(String name, String comment) {
		DatabaseFunction f = newDatabaseFunction(name, comment);
		functions.put(name, f);
		return f;
	}

	public final Schema removeFunctions(String name) {
		functions.remove(name);
		return this;
	}

	public final DatabaseFunction getFunction(String name) {
		return functions.get(name);
	}

	public final boolean containsFunction(String name) {
		return functions.containsKey(name);
	}

	public final Collection<DatabaseFunction> getFunctions() {
		return functions.values();
	}

	public final int countFunctions() {
		return functions.size();
	}

	public final DatabaseSequence addSequence(String name, String comment, Class<?> clazz, int increment) {
		DatabaseSequence s = new DatabaseSequence(name, comment, clazz, increment, this);
		sequences.put(name, s);
		return s;
	}

	public final Schema removeSequence(String name) {
		sequences.remove(name);
		return this;
	}

	public final boolean containsSequence(String name) {
		return sequences.containsKey(name);
	}

	public final Collection<DatabaseSequence> getSequences() {
		return sequences.values();
	}

	public final DatabaseSequence getSequence(String name) {
		return sequences.get(name);
	}

	public final DatabaseSequence getSequence(Class<?> clazz) {
		for (DatabaseSequence sequence : getSequences()) {
			if (sequence.getJavaClass().equals(clazz)) {
				return sequence;
			}
		}
		return null;
	}

	public final int countSequences() {
		return sequences.size();
	}

	public final DatabaseTrigger addTrigger(String name, String comment) {
		DatabaseTrigger t = newDatabaseTrigger(name, comment);
		triggers.put(name, t);
		return t;
	}

	public final Schema removeTrigger(String name) {
		triggers.remove(name);
		return this;
	}

	public final DatabaseTrigger getTrigger(String name) {
		return triggers.get(name);
	}

	public final boolean containsTrigger(String name) {
		return triggers.containsKey(name);
	}

	public final Collection<DatabaseTrigger> getTriggers() {
		return triggers.values();
	}

	public final int countTriggers() {
		return triggers.size();
	}

	public final DatabaseView addView(Class<?> target, String comment) {
		DatabaseView f = newDatabaseView(target, comment);
		views.put(target.getName(), f);
		return f;
	}

	public final Schema removeView(Class<?> target) {
		views.remove(target.getName());
		return this;
	}

	public final DatabaseView getView(Class<?> target) {
		return views.get(target.getName());
	}

	public final boolean containsView(Class<?> target) {
		return views.containsKey(target.getName());
	}

	public final Collection<DatabaseView> getViews() {
		return views.values();
	}

	public final int countViews() {
		return views.size();
	}

	public final DatabaseUser addUser(String name, String password) {
		DatabaseUser user = new DatabaseUser(name, password);
		users.put(name, user);
		user.setSchema(this);
		return user;
	}

	public final Schema removeUser(String name) {
		users.remove(name);
		return this;
	}

	public final boolean containsUser(String name) {
		if (owner.getUsername().equals(name)) {
			return true;
		}
		return users.containsKey(name);
	}

	public final Collection<DatabaseUser> getUsers() {
		List<DatabaseUser> all = new ArrayList<DatabaseUser>();
		all.add(owner);
		all.addAll(users.values());
		return all;
	}

	public final DatabaseUser getUser(String name) {
		return users.get(name);
	}

	public final int countUsers() {
		return users.size() + 1;
	}

	public final DatabaseUser getOwner() {
		return owner;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + version;
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractSchema other = (AbstractSchema) obj;
		return version == other.version;
	}

	public final int getVersion() {
		return version;
	}

	public final String getLocation() {
		return location;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public final PrologProvider getProvider() {
		return provider;
	}

	public final PrologEngine getEngine() {
		return storage.getEngine();
	}

	public final Storage getStorage() {
		return storage;
	}

	public final RelationalGraph<DatabaseClass, DatabaseClass> getGraph() {
		DatabaseClass relationEdge = addAbstractClass(RelationalEdge.class, "");
		RelationalGraph<DatabaseClass, DatabaseClass> g = new RelationalGraph<DatabaseClass, DatabaseClass>();
		for (DatabaseClass clazz : new ArrayList<DatabaseClass>(classes.values())) {
			if (!clazz.equals(relationEdge) && !clazz.isSubClassOf(relationEdge)) {
				if (g.getVertex(clazz) == null) {
					g.addVertex(clazz);
				}
				for (DatabaseField field : clazz.getFields()) {
					Class<?> type = field.getType();
					if (type != null && isRelationalType(type)) {
						type = field.getLinkedType();
					}
					if (type != null && !isJavaLangType(type)) {
						DatabaseClass linkedClass = getClass(type);
						if (linkedClass != null) {
							DatabaseField inKey = clazz.getPrimaryKeyField();
							DatabaseField outKey = linkedClass.getPrimaryKeyField();
							String inName = linkedClass.getName() + clazz.getName();
							String outName = clazz.getName() + linkedClass.getName();
							DatabaseClass in = getOrAddClass(inName, relationEdge);
							// TODO Add fields here use @Id jpa annotation
							DatabaseClass out = getOrAddClass(outName, relationEdge);
							// TODO Add fields here use @Id jpa annotation
							if (g.getVertex(linkedClass) == null) {
								g.addVertex(linkedClass);
							}
							g.addEdge(linkedClass, clazz, out, Direction.IN);
							g.addEdge(clazz, linkedClass, in, Direction.OUT);
						}
					}
				}
			}
		}
		return g;
	}

	public final List<Class<?>> getJavaClasses() {
		int size = countClasses();
		List<Class<?>> l = new ArrayList<Class<?>>(size);
		for (DatabaseClass c : getClasses()) {
			l.add(c.getJavaClass());
		}
		return l;
	}

	public final void clear() {
		sequences.clear();
		functions.clear();
		classes.clear();
		users.clear();
	}

	public final Schema load() {
		storage.getTransaction().begin();
		List<DatabaseUser> u = storage.findAll(DatabaseUser.class);
		for (DatabaseUser databaseUser : u) {
			users.put(databaseUser.getUsername(), databaseUser.setSchema(this));
		}
		List<DatabaseClass> c = storage.findAll(DatabaseClass.class);
		for (DatabaseClass databaseClass : c) {
			classes.put(databaseClass.getName(), databaseClass.setSchema(this));
		}
		List<PrologDatabaseFunction> f = storage.findAll(PrologDatabaseFunction.class);
		for (DatabaseFunction databaseFunction : f) {
			databaseFunction.setSchema(this);
			databaseFunction.setEngine(getEngine());
			databaseFunction.setProvider(getProvider());
			functions.put(databaseFunction.getName(), databaseFunction);
		}
		List<DatabaseSequence> s = storage.findAll(DatabaseSequence.class);
		for (DatabaseSequence databaseSequence : s) {
			sequences.put(databaseSequence.getName(), databaseSequence.setSchema(this));
		}
		List<DatabaseTrigger> t = storage.findAll(DatabaseTrigger.class);
		for (DatabaseTrigger databaseTrigger : t) {
			databaseTrigger.setSchema(this);
			databaseTrigger.setEngine(getEngine());
			databaseTrigger.setProvider(getProvider());
			triggers.put(databaseTrigger.getName(), databaseTrigger);
		}
		List<DatabaseView> v = storage.findAll(DatabaseView.class);
		for (DatabaseView databaseView : v) {
			databaseView.setSchema(this);
			databaseView.setEngine(getEngine());
			databaseView.setProvider(getProvider());
			views.put(databaseView.getName(), databaseView);
		}
		storage.getTransaction().close();
		return this;
	}

	public final void flush() {
		storage.getTransaction().begin();
		for (DatabaseUser user : getUsers()) {
			storage.insert(user);
		}
		for (DatabaseClass clazz : getClasses()) {
			storage.insert(clazz);
		}
		for (DatabaseFunction function : getFunctions()) {
			storage.insert(function);
			function.save();
		}
		for (DatabaseSequence sequence : getSequences()) {
			storage.insert(sequence);
		}
		for (DatabaseTrigger trigger : getTriggers()) {
			storage.insert(trigger);
			trigger.save();
		}
		for (DatabaseView view : getViews()) {
			storage.insert(view);
			view.save();
		}
		try {
			storage.getTransaction().commit();
		} catch (Exception e) {
			storage.rollback();
		}
		storage.getTransaction().close();
	}

}
