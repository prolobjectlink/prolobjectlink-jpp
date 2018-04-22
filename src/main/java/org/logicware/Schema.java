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
package org.logicware;

import java.util.Collection;
import java.util.List;

import org.logicware.graph.RelationalGraph;
import org.logicware.prolog.PrologProvider;

public interface Schema {

	public void flush();

	public Schema load();

	public void clear();

	public RelationalGraph<DatabaseClass, DatabaseClass> getGraph();

	public Storage getStorage();

	public PrologProvider getProvider();

	public ContainerFactory getContainerFactory();

	public String getLocation();

	public int getVersion();

	public DatabaseUser getOwner();

	public int countUsers();

	public DatabaseUser getUser(String name);

	public Collection<DatabaseUser> getUsers();

	public boolean containsUser(String name);

	public Schema removeUser(String name);

	public DatabaseUser addUser(String name, String password);

	public int countSequences();

	public DatabaseSequence getSequence(String name);

	public DatabaseSequence getSequence(Class<?> clazz);

	public Collection<DatabaseSequence> getSequences();

	public boolean containsSequence(String name);

	public Schema removeSequence(String name);

	public DatabaseSequence addSequence(String name, Class<?> clazz, int increment);

	public int countFunctions();

	public Collection<DatabaseFunction> getFunctions();

	public boolean containsFunction(String name);

	public DatabaseFunction getFunction(String name);

	public Schema removeFunctions(String name);

	public DatabaseFunction addFunction(String name);

	public int countViews();

	public Collection<DatabaseView> getViews();

	public boolean containsView(Class<?> target);

	public DatabaseView getView(Class<?> target);

	public Schema removeView(Class<?> target);

	public DatabaseView addView(Class<?> target);

	public int countClasses();

	public Collection<DatabaseClass> getClasses();

	public DatabaseClass getOrAddClass(String className, DatabaseClass... superClasses);

	public DatabaseClass getOrAddClass(String className, DatabaseClass superClass);

	public DatabaseClass getOrAddClass(Class<?> clazz);

	public DatabaseClass getOrAddClass(String className);

	public DatabaseClass getClass(String className);

	public DatabaseClass getClass(Class<?> clazz);

	public boolean containsClass(String className);

	public Schema removeClass(String className);

	public DatabaseClass addAbstractClass(String className, DatabaseClass... superClasses);

	public DatabaseClass addAbstractClass(String className, DatabaseClass superClass);

	public DatabaseClass addAbstractClass(String n);

	public DatabaseClass addAbstractClass(Class<?> clazz);

	public DatabaseClass addClass(String className, DatabaseClass... superClasses);

	public DatabaseClass addClass(String className, DatabaseClass superClass);

	public DatabaseClass addClass(String className);

	public DatabaseClass addClass(Class<?> clazz);

	public Schema addPackage(String packageName);

	public Schema addPackage(Package pack);

	public boolean isJavaLangType(Class<?> type);

	public boolean isSet(Class<?> type);

	public boolean isList(Class<?> type);

	public boolean isCollection(Class<?> type);

	public boolean isMap(Class<?> type);

	public boolean isJavaUtilType(Class<?> type);

	public boolean isRelationalType(Class<?> type);

	public List<Class<?>> getJavaClasses();

	public List<Class<?>> compile();

	public String generate();

}
