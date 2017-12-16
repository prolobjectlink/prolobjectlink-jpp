/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd;

import java.io.File;

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractDocumentContainer extends AbstractPersistentDocument implements DocumentContainer {

    // container factory for create containers
    protected final ContainerFactory containerFactory;

    // file system separator
    protected final static char separator = File.separatorChar;

    protected AbstractDocumentContainer(PrologProvider provider, Properties properties,
	    ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory) {
	super(provider, properties, converter, location);
	this.containerFactory = containerFactory;
    }

    public final ContainerFactory getContainerFactory() {
	return containerFactory;
    }

    public abstract void open();

    public abstract <O> void insert(O... objects);

    public abstract <O> void update(O match, O update);

    public abstract <O> void delete(O... objects);

    public abstract void delete(Class<?> clazz);

    public abstract boolean contains(String string);

    public abstract <O> boolean contains(O o);

    public abstract <O> boolean contains(Class<O> clazz);

    public abstract <O> boolean contains(Predicate<O> predicate);

    public abstract Transaction getTransaction();

    public abstract Query createQuery(String string);

    public abstract <O> TypedQuery<O> createQuery(O o);

    public abstract <O> TypedQuery<O> createQuery(Class<O> clazz);

    public abstract <O> TypedQuery<O> createQuery(Predicate<O> predicate);

    public abstract <O> ConstraintQuery<O> createConstraintQuery(Class<O> clazz);

    public abstract ProcedureQuery createProcedureQuery(String functor, String... args);

    public abstract PersistentContainer containerOf(Class<?> clazz);

    public abstract String locationOf(Class<?> clazz);

    public abstract void close();

}
