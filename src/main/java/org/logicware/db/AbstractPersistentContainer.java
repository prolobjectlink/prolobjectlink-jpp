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
package org.logicware.db;

import java.io.File;

import org.logicware.ContainerFactory;
import org.logicware.ObjectConverter;
import org.logicware.PersistentContainer;
import org.logicware.Settings;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.logicware.tools.Backup;
import org.logicware.tools.Restore;

public abstract class AbstractPersistentContainer extends AbstractContainer implements PersistentContainer {

	// open/close state flag
	protected boolean open;

	// database root location
	// location for all data files
	private final String location;

	// file system separator
	protected static final char SEPARATOR = File.separatorChar;

	// container factory for create containers
	protected final ContainerFactory containerFactory;

	protected AbstractPersistentContainer(PrologProvider provider, Settings properties,
			ObjectConverter<PrologTerm> converter, String location, ContainerFactory containerFactory) {
		super(provider, properties, converter);
		this.containerFactory = containerFactory;
		this.location = location;
		this.open = true;
	}

	public final ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public void backup(String directory, String zipFileName) {
		Backup backup = new Backup(getLocation());
		backup.createBackup(directory, zipFileName);
	}

	public final void restore(String directory, String zipFileName) {
		Restore restore = new Restore();
		restore.restoreBackup(directory, zipFileName);
	}

	public final String getLocation() {
		return location;
	}

	public final void begin() {
		open();
	}

	public final void commit() {
		flush();
	}

	public final void rollback() {
		// roll back open the file
		// losing all memory data changes
		open();
	}

	public final boolean isOpen() {
		return open;
	}

}
