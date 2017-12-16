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

import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public abstract class AbstractPersistentContainer extends AbstractVolatileContainer implements PersistentContainer {

    // open/close state flag
    protected boolean open;

    // database root location
    // location for all data files
    private final String location;

    protected AbstractPersistentContainer(PrologProvider provider, Properties properties,
	    ObjectConverter<PrologTerm> converter, String location) {
	super(provider, properties, converter);
	this.location = location;
	this.open = true;
    }

    public abstract void backup(String directory, String zipFileName);

    public abstract void restore(String directory, String zipFileName);

    public final String getLocation() {
	return location;
    }

    public final boolean isOpen() {
	return open;
    }

    public abstract void close();

    @Deprecated
    public abstract void open();

}
