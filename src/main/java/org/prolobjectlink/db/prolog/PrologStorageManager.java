/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
 * #L%
 */
package org.prolobjectlink.db.prolog;

import org.prolobjectlink.db.Container;
import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.Storage;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.VolatileContainer;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.db.storage.AbstractStorageManager;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

/**
 * Prolog {@link StorageManager} implementation
 * 
 * @author Jose Zalacain
 * @see Container
 * @see VolatileContainer
 * @see Storage
 * @since 1.0
 */
public class PrologStorageManager extends AbstractStorageManager implements StorageManager {

	public PrologStorageManager(PrologProvider provider, Settings properties, String location,
			ContainerFactory containerFactory, StorageMode storageMode) {
		super(provider, properties, new PrologObjectConverter(provider), location, containerFactory, storageMode);
	}

	public PrologStorageManager(PrologProvider provider, Settings properties, ObjectConverter<PrologTerm> converter,
			String location, ContainerFactory containerFactory, StorageMode storageMode) {
		super(provider, properties, converter, location, containerFactory, storageMode);
	}

}
