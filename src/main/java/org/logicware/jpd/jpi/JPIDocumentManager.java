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
package org.logicware.jpd.jpi;

import java.util.List;

import org.logicware.jpd.AbstractDocumentManager;
import org.logicware.jpd.Container;
import org.logicware.jpd.ContainerFactory;
import org.logicware.jpd.Document;
import org.logicware.jpd.DocumentManager;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.Properties;
import org.logicware.jpd.Query;
import org.logicware.jpd.VolatileContainer;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Container
 * @see VolatileContainer
 * @see Document
 */
public class JPIDocumentManager extends AbstractDocumentManager implements DocumentManager {

	public JPIDocumentManager(PrologProvider provider, String location, ContainerFactory containerFactory) {
		super(provider, new Properties(), new JPIObjectConverter(provider), location, containerFactory);
	}

	public JPIDocumentManager(PrologProvider provider, Properties properties, String location,
			ContainerFactory containerFactory) {
		super(provider, properties, new JPIObjectConverter(provider), location, containerFactory);
	}

	public JPIDocumentManager(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter,
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
		return new JPIQuery(solutionsOf(terms, classes));
	}

}
