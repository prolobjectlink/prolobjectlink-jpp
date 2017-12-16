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
import org.logicware.jpd.ContainerFactory;
import org.logicware.jpd.Document;
import org.logicware.jpd.DocumentManager;
import org.logicware.jpd.DocumentPool;
import org.logicware.jpd.Factories;
import org.logicware.jpd.Properties;
import org.logicware.jpi.PrologProvider;

public abstract class JPIFactories extends AbstractFactories implements Factories {

    protected JPIFactories(Properties properties, PrologProvider provider) {
	super(properties, provider);
    }

    @Override
    public final Document createDocument(String path) {
	return new JPIDocument(getProvider(), getProperties(), path);
    }

    @Override
    public final DocumentPool createDocumentPool(String path) {
	ContainerFactory factory = createContainerFactory();
	return new JPIDocumentPool(getProvider(), getProperties(), path, factory);
    }

    @Override
    public final DocumentManager createDocumentManager(String path) {
	ContainerFactory factory = createContainerFactory();
	return new JPIDocumentManager(getProvider(), getProperties(), path, factory);
    }

}
