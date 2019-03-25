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
package org.prolobjectlink.db.prolog;

import java.io.File;

import org.prolobjectlink.db.DatabaseSchema;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.RelationalDatabase;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.engine.AbstractRelationalDatabase;
import org.prolobjectlink.db.etc.Settings;

/**
 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
 *             RemoteRelationalHierarchical} instead
 * @author Jose Zalacain
 *
 */
@Deprecated
public final class PrologRelationalDatabase extends AbstractRelationalDatabase implements RelationalDatabase {

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public PrologRelationalDatabase(Settings properties, StorageMode storageMode, String name, DatabaseUser user) {
		super(name, new DatabaseSchema(LOCATION + File.separator + name, properties.getProvider(), properties, user),
				user,
				new PrologStorageGraph(LOCATION + File.separator + name + File.separator + "database",
						new DatabaseSchema(LOCATION + File.separator + name, properties.getProvider(), properties,
								user),
						properties, properties.getProvider(), properties,
						new PrologObjectConverter(properties.getProvider()), storageMode));
	}

}
