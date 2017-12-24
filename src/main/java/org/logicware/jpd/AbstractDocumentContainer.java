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
	protected static final char SEPARATOR = File.separatorChar;

	protected AbstractDocumentContainer(PrologProvider provider, Properties properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory) {
		super(provider, properties, converter, location);
		this.containerFactory = containerFactory;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

}
