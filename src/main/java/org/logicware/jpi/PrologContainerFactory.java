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

import org.logicware.AbstractFactories;
import org.logicware.Cache;
import org.logicware.ContainerFactory;
import org.logicware.Storage;
import org.logicware.StorageManager;
import org.logicware.StoragePool;
import org.logicware.Properties;

public abstract class PrologContainerFactory extends AbstractFactories implements ContainerFactory {

	protected PrologContainerFactory(Properties properties, PrologProvider provider) {
		super(properties, provider);
	}

	public Cache createCache() {
		return new PrologCache(getProvider(), getProperties());
	}

	public Storage createStorage(String path) {
		ContainerFactory factory = createContainerFactory();
		return new PrologStorage(getProvider(), getProperties(), path, factory);
	}

	public StoragePool createStoragePool(String path, String name) {
		ContainerFactory factory = createContainerFactory();
		return new PrologStoragePool(getProvider(), getProperties(), path, name, factory);
	}

	public StorageManager createStorageManager(String path) {
		ContainerFactory factory = createContainerFactory();
		return new PrologStorageManager(getProvider(), getProperties(), path, factory);
	}

}
