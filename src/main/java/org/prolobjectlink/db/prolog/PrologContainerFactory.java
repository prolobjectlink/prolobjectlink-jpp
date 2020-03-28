/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
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
	 * @deprecated Use MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return new PrologRelationalDatabase(getSettings(), storageMode, name, user);
	}

	/**
	 * @deprecated Use MemoryHierarchical,EmbeddedHierarchical, RemoteHierarchical
	 *             instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public final HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name,
			DatabaseUser user) {
		return new PrologHierarchicalDatabase(getSettings(), storageMode, name, user);
	}

}
