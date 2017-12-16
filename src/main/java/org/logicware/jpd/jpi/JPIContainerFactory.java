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

import org.logicware.jpd.AbstractFactories;
import org.logicware.jpd.Cache;
import org.logicware.jpd.ContainerFactory;
import org.logicware.jpd.Document;
import org.logicware.jpd.DocumentManager;
import org.logicware.jpd.DocumentPool;
import org.logicware.jpd.Properties;
import org.logicware.jpi.PrologProvider;

public abstract class JPIContainerFactory extends AbstractFactories implements ContainerFactory {

    protected JPIContainerFactory(Properties properties, PrologProvider provider) {
	super(properties, provider);
    }

    public Cache createCache() {
	return new JPICache(getProvider(), getProperties());
    }

    @Override
    public Document createDocument(String path) {
	return new JPIDocument(getProvider(), getProperties(), path);
    }

    @Override
    public DocumentPool createDocumentPool(String path) {
	ContainerFactory factory = createContainerFactory();
	return new JPIDocumentPool(getProvider(), getProperties(), path, factory);
    }

    @Override
    public DocumentManager createDocumentManager(String path) {
	ContainerFactory factory = createContainerFactory();
	return new JPIDocumentManager(getProvider(), getProperties(), path, factory);
    }

}
