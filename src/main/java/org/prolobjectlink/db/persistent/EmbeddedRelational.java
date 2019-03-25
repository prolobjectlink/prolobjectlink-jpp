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
package org.prolobjectlink.db.persistent;

import java.net.URL;

import org.prolobjectlink.db.DatabaseMode;
import org.prolobjectlink.db.DatabaseType;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.EmbeddedDatabase;
import org.prolobjectlink.db.PersistentContainer;
import org.prolobjectlink.db.Schema;

public final class EmbeddedRelational extends EmbeddedDatabaseClient implements EmbeddedDatabase {

	protected static EmbeddedRelational embeddedRelationalDB;

	EmbeddedRelational(String name, URL url, Schema schema, DatabaseUser owner, PersistentContainer storage) {
		super(storage.getProperties(), url, name, schema, owner, storage);
	}

	public static final EmbeddedRelational newInstance() {
		if (embeddedRelationalDB == null) {
			// TODO LOAD ALL FROM PROPERTIES FILE
		}
		return embeddedRelationalDB;
	}

	public DatabaseMode getMode() {
		return DatabaseMode.EMBEDDED;
	}

	public DatabaseType getType() {
		return DatabaseType.RELATIONAL;
	}

}
