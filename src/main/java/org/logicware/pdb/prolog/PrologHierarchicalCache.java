/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb.prolog;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.HierarchicalCache;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.Settings;
import org.logicware.pdb.common.AbstractHierarchicalCache;

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
