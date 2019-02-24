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

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.HierarchicalDatabase;
import org.prolobjectlink.db.RelationalCache;
import org.prolobjectlink.db.RelationalDatabase;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.Storage;
import org.prolobjectlink.db.StorageGraph;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.StoragePool;
import org.prolobjectlink.db.container.AbstractContainerFactory;
import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.prolog.PrologProvider;

/** @author Jose Zalacain @since 1.0 */
public abstract class PrologContainerFactory extends AbstractContainerFactory implements ContainerFactory {

	protected PrologContainerFactory(Settings properties, PrologProvider provider) {
		super(properties, provider);
	}

	public final RelationalCache createRelationalCache(Schema schema) {
		return new PrologRelationalCache(schema, getSettings(), getProvider(), this);
	}

	public final Storage createStorage(String path) {
		return new PrologStorage(getProvider(), getSettings(), path, this);
	}

	public final StoragePool createStoragePool(String path, String name) {
		return new PrologStoragePool(getProvider(), getSettings(), path, name, this);
	}

	public final StorageManager createStorageManager(String path, StorageMode storageMode) {
		return new PrologStorageManager(getProvider(), getSettings(), path, this, storageMode);
	}

	public final StorageGraph createStorageGraph(String path, Schema schema, StorageMode storageMode) {
		return new PrologStorageGraph(getProvider(), getSettings(), path, schema, this, storageMode);
	}

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return new PrologRelationalDatabase(getSettings(), storageMode, name, user);
	}

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name,
			DatabaseUser user) {
		return new PrologHierarchicalDatabase(getSettings(), storageMode, name, user);
	}

}
