/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db.container;

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.tools.Backup;
import org.prolobjectlink.db.tools.Restore;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

/** @author Jose Zalacain @since 1.0 */
public abstract class AbstractPersistentContainer extends AbstractContainer implements PersistentContainer {

	// open/close state flag
	protected boolean open;

	// database root location
	// location for all data files
	private final String location;

	// file system separator
	protected static final char SEPARATOR = '/';

	// container factory for create containers
	protected final ContainerFactory containerFactory;

	private static final Backup backuper = new Backup();
	private static final Restore restorer = new Restore();

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

	public final void backup(String directory, String zipFileName) {
		backuper.createBackup(getLocation(), directory, zipFileName);
	}

	public final void restore(String directory, String zipFileName) {
		restorer.restoreBackup(directory, zipFileName);
	}

	public final String getLocation() {
		return location;
	}

	public final boolean isActive() {
		return getTransaction().isActive();
	}

	public final void begin() {
		getTransaction().begin();
	}

	public final void commit() {
		getTransaction().commit();
	}

	public final void rollback() {
		getTransaction().rollback();
	}

	public final boolean isOpen() {
		return open;
	}

}
