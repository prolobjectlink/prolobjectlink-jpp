/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

import org.prolobjectlink.db.etc.Settings;
import org.prolobjectlink.prolog.PrologConverter;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface ContainerFactory {

	public Settings getSettings();

	public PrologProvider getProvider();

	public void setSettings(Settings properties);

	public void setProvider(PrologProvider provider);

	public RelationalCache createRelationalCache(Schema schema);

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user);

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, String username,
			String password);

	public HierarchicalCache createHierarchicalCache();

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, DatabaseUser user);

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, String username,
			String password);

	public StorageManager createStorageManager(String path, StorageMode storageMode);

	public StoragePool createStoragePool(String path, String name);

	public StorageGraph createStorageGraph(String path, Schema schema, StorageMode storageMode);

	public Storage createStorage(String path);

	public <K> PrologConverter<K> getConverter();

	ObjectConverter<PrologTerm> getObjectConverter();

}
