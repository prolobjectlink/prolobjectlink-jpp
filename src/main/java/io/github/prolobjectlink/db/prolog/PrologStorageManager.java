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
package io.github.prolobjectlink.db.prolog;

import io.github.prolobjectlink.db.Container;
import io.github.prolobjectlink.db.ContainerFactory;
import io.github.prolobjectlink.db.ObjectConverter;
import io.github.prolobjectlink.db.Storage;
import io.github.prolobjectlink.db.StorageManager;
import io.github.prolobjectlink.db.StorageMode;
import io.github.prolobjectlink.db.VolatileContainer;
import io.github.prolobjectlink.db.etc.Settings;
import io.github.prolobjectlink.db.storage.AbstractStorageManager;
import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

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
