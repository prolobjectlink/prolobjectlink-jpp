/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database.prolog;

import org.logicware.database.ContainerFactory;
import org.logicware.database.HierarchicalCache;
import org.logicware.database.ObjectConverter;
import org.logicware.database.Settings;
import org.logicware.database.cache.AbstractHierarchicalCache;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

public abstract class PrologHierarchicalCache extends AbstractHierarchicalCache implements HierarchicalCache {

	private final ContainerFactory containerFactory;

	public PrologHierarchicalCache(PrologProvider provider, Settings properties, ContainerFactory containerFactory) {
		super(provider, properties, new PrologObjectConverter(provider));
		this.containerFactory = containerFactory;
	}

	public PrologHierarchicalCache(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			ContainerFactory containerFactory) {
		super(provider, properties, converter);
		this.containerFactory = containerFactory;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

}
