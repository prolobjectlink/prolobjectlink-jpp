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
import java.util.Collection;
import java.util.List;

import io.github.prolobjectlink.prolog.PrologEngine;
import io.github.prolobjectlink.prolog.PrologProvider;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Schema extends Serializable {

	public void flush();

	public Schema load();

	public void clear();

	public RelationalGraph<DatabaseClass, DatabaseClass> getGraph();

	public Storage getStorage();

	public PrologEngine getEngine();

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

	public DatabaseSequence addSequence(String name, String comment, Class<?> clazz, int increment);

	public int countFunctions();

	public Collection<DatabaseFunction> getFunctions();

	public boolean containsFunction(String name);

	public DatabaseFunction getFunction(String name);

	public Schema removeFunctions(String name);

	public DatabaseFunction addFunction(String name, String comment);

	public int countViews();

	public Collection<DatabaseView> getViews();

	public boolean containsView(Class<?> target);

	public DatabaseView getView(Class<?> target);

	public Schema removeView(Class<?> target);

	public DatabaseView addView(Class<?> target, String comment);

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

	public DatabaseClass addAbstractClass(String className, String comment, DatabaseClass... superClasses);

	public DatabaseClass addAbstractClass(String className, String comment, DatabaseClass superClass);

	public DatabaseClass addAbstractClass(String n, String comment);

	public DatabaseClass addAbstractClass(Class<?> clazz, String comment);

	public DatabaseClass addClass(String className, String comment, DatabaseClass... superClasses);

	public DatabaseClass addClass(String className, String comment, DatabaseClass superClass);

	public DatabaseClass addClass(String className, String comment);

	public DatabaseClass addClass(Class<?> clazz, String comment);

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
