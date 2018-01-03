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
package org.logicware.jpi;

import java.util.List;

import org.logicware.AbstractStorageManager;
import org.logicware.Container;
import org.logicware.ContainerFactory;
import org.logicware.Storage;
import org.logicware.StorageManager;
import org.logicware.ObjectConverter;
import org.logicware.Properties;
import org.logicware.Query;
import org.logicware.VolatileContainer;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Container
 * @see VolatileContainer
 * @see Storage
 */
public class PrologStorageManager extends AbstractStorageManager implements StorageManager {

	public PrologStorageManager(PrologProvider provider, String location, ContainerFactory containerFactory) {
		super(provider, new Properties(), new PrologObjectConverter(provider), location, containerFactory);
	}

	public PrologStorageManager(PrologProvider provider, Properties properties, String location,
			ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory);
	}

	public PrologStorageManager(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory) {
		super(provider, properties, converter, location, containerFactory);
	}

	public Query createQuery(String string) {
		ObjectConverter<PrologTerm> c = getConverter();
		PrologTerm[] terms = c.toTermsArray(string);
		List<Class<?>> classes = classesOf(terms);
		for (Class<?> clazz : classes) {
			getEngine().include(locationOf(clazz));
		}
		return new PrologContainerQuery(solutionsOf(terms, classes));
	}

}
