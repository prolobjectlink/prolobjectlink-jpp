/*
 * #%L
 * prolobjectlink
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
package org.logicware;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.logicware.graph.RelationalGraph;
import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;
import org.logicware.prolog.PrologDatabaseFunction;
import org.logicware.prolog.PrologDatabaseView;
import org.logicware.prolog.PrologProvider;
import org.logicware.util.ArrayList;

public abstract class AbstractSchema implements DatabaseSchema {

	private int version;
	private final String location;
	private final Storage storage;
	private final DatabaseUser owner;
	private final PrologProvider provider;
	private final ContainerFactory containerFactory;

	//
	private final Map<String, DatabaseView> views;
	private final Map<String, DatabaseUser> users;
	private final Map<String, DatabaseClass> classes;
	private final Map<String, DatabaseFunction> functions;
	private final Map<String, DatabaseSequence> sequences;

	private DatabaseClass newDatabaseClass(Class<?> clazz) {
		DatabaseClass c = new DatabaseClass(clazz, this);
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			String name = field.getName();
			Class<?> type = field.getType();
			c.addField(name, i, type);
		}
		return c;
	}

	private DatabaseClass newDatabaseClass(String clazz) {
		return new DatabaseClass(clazz, this);
	}

	private DatabaseFunction newDatabaseFunction(String name) {
		String path0 = location.substring(0, location.lastIndexOf(File.separatorChar));
		String path1 = path0 + File.separator + "functions.pl";
		return new PrologDatabaseFunction(name, this, path1, provider);
	}

	private DatabaseView newDatabaseView(Class<? extends DatabaseView> target) {
		String path0 = location.substring(0, location.lastIndexOf(File.separatorChar));
		String path1 = path0 + File.separator + "views.pl";
		return new PrologDatabaseView(path1, target, this, provider);
	}

	private Class<?>[] findClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
		}
		ArrayList<Class<?>> list = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			list.addAll(findClasses(directory, packageName));
		}
		return list.toArray(new Class[list.size()]);
	}

	/**
	 * Recursive method used to find all classes in a given directory and
	 * subdirs.
	 *
	 * @param directory
	 *            The base directory
	 * @param packageName
	 *            The package name for classes found inside the base directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> list = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return list;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				list.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				list.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return list;
	}

	public AbstractSchema(String location, PrologProvider provider, ContainerFactory factory, DatabaseUser owner) {
		this.storage = factory.createStorage(location + File.separator + "metadata" + File.separator + "schema.pl");
		this.functions = new LinkedHashMap<String, DatabaseFunction>();
		this.sequences = new LinkedHashMap<String, DatabaseSequence>();
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

	public final DatabaseSchema addPackage(Package pack) {
		return addPackage(pack.getName());
	}

	public final DatabaseSchema addPackage(String packageName) {
		try {
			Class<?>[] classArray = findClasses(packageName);
			for (int i = 0; i < classArray.length; i++) {
				Class<?> clazz = classArray[i];
				Class<?> sc = clazz.getSuperclass();
				DatabaseClass c = getOrAddClass(clazz);
				DatabaseClass dbsc = addAbstractClass(sc);
				c.setSuperClass(dbsc);
			}
		} catch (ClassNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.CLASS_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		}
		return this;
	}

	public final DatabaseClass addClass(Class<?> clazz) {
		DatabaseClass c = newDatabaseClass(clazz);
		classes.put(clazz.getName(), c);
		return c;
	}

	/**
	 * Add a class to the current schema
	 * 
	 * @param className
	 *            name for the class
	 * @return database class instance.
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className) {
		DatabaseClass c = newDatabaseClass(className);
		classes.put(className, c);
		return c;
	}

	/**
	 * Add a class to the current schema and set a parent super class.
	 * 
	 * @param className
	 *            name for the class
	 * @param superClass
	 *            parent superclass
	 * @return database class instance.
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className, DatabaseClass superClass) {
		DatabaseClass c = newDatabaseClass(className);
		classes.put(className, c.setSuperClass(superClass));
		return c;
	}

	/**
	 * Add a class to the current schema and set a parent super classes.
	 * 
	 * @param className
	 *            name for the class
	 * @param superClasses
	 *            parent super classes
	 * @return database class instance
	 * @since 1.0
	 */
	public final DatabaseClass addClass(String className, DatabaseClass... superClasses) {
		DatabaseClass c = newDatabaseClass(className);
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

	public final DatabaseClass addAbstractClass(Class<?> clazz) {
		DatabaseClass c = newDatabaseClass(clazz);
		c.setAbstract(true).setJavaClass(clazz);
		classes.put(clazz.getSimpleName(), c);
		return c;
	}

	public final DatabaseClass addAbstractClass(String n) {
		DatabaseClass c = newDatabaseClass(n);
		classes.put(n, c.setAbstract(true));
		return c;
	}

	public final DatabaseClass addAbstractClass(String className, DatabaseClass superClass) {
		DatabaseClass c = addClass(className, superClass);
		c.setAbstract(true);
		return c;
	}

	public final DatabaseClass addAbstractClass(String className, DatabaseClass... superClasses) {
		DatabaseClass c = addClass(className, superClasses);
		c.setAbstract(true);
		return c;
	}

	public final DatabaseSchema removeClass(String className) {
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
		return addClass(className);
	}

	public final DatabaseClass getOrAddClass(Class<?> clazz) {
		DatabaseClass c = getClass(clazz);
		if (c != null) {
			return c;
		}
		return addClass(clazz);
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

	public final DatabaseFunction addFunction(String name) {
		DatabaseFunction f = newDatabaseFunction(name);
		functions.put(name, f);
		return f;
	}

	public final DatabaseSchema removeFunctions(String name) {
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

	public final DatabaseSequence addSequence(String name, Class<?> clazz, int increment) {
		DatabaseSequence s = new DatabaseSequence(name, clazz, increment, this);
		sequences.put(name, s);
		return s;
	}

	public final DatabaseSchema removeSequence(String name) {
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

	public final DatabaseView addView(Class<? extends DatabaseView> target) {
		DatabaseView f = newDatabaseView(target);
		views.put(target.getName(), f);
		return f;
	}

	public final DatabaseSchema removeView(Class<? extends DatabaseView> target) {
		views.remove(target.getName());
		return this;
	}

	public final DatabaseView getView(Class<? extends DatabaseView> target) {
		return views.get(target.getName());
	}

	public final boolean containsView(Class<? extends DatabaseView> target) {
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

	public final DatabaseSchema removeUser(String name) {
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

	public final Storage getStorage() {
		return storage;
	}

	public final RelationalGraph<DatabaseClass, DatabaseClass> getGraph() {
		DatabaseClass relationEdge = addAbstractClass(RelationalEdge.class);
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

	public final void clear() {
		sequences.clear();
		functions.clear();
		classes.clear();
		users.clear();
	}

	public final DatabaseSchema load() {
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
			functions.put(databaseFunction.getName(), databaseFunction.setSchema(this));
		}
		List<DatabaseSequence> s = storage.findAll(DatabaseSequence.class);
		for (DatabaseSequence databaseSequence : s) {
			sequences.put(databaseSequence.getName(), databaseSequence.setSchema(this));
		}
		List<DatabaseView> v = storage.findAll(DatabaseView.class);
		for (DatabaseView databaseView : v) {
			views.put(databaseView.getName(), databaseView.setSchema(this));
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
