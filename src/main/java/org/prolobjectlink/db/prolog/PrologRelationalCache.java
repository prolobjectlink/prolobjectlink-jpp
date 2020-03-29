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
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.RelationalCache;
import org.prolobjectlink.db.RelationalGraph;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.cache.AbstractRelationalCache;
import org.prolobjectlink.db.etc.Settings;

import io.github.prolobjectlink.prolog.PrologProvider;
import io.github.prolobjectlink.prolog.PrologTerm;

public final class PrologRelationalCache extends AbstractRelationalCache implements RelationalCache {

	public PrologRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory) {
		super(schema, properties, provider, containerFactory, new PrologObjectConverter(provider));
	}

	public PrologRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, RelationalGraph<Object, Object> graph) {
		super(schema, properties, provider, containerFactory, new PrologObjectConverter(provider), graph);
	}

	public PrologRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter) {
		super(schema, properties, provider, containerFactory, converter);
	}

	public PrologRelationalCache(Schema schema, Settings properties, PrologProvider provider,
			ContainerFactory containerFactory, ObjectConverter<PrologTerm> converter,
			RelationalGraph<Object, Object> graph) {
		super(schema, properties, provider, containerFactory, converter, graph);
	}

	// public PrologRelationalCache(PrologProvider provider, Settings
	// properties) {
	// super(provider, properties, new PrologObjectConverter(provider));
	// }
	//
	// public PrologRelationalCache(PrologProvider provider, Settings
	// properties,
	// ObjectConverter<PrologTerm> converter) {
	// super(provider, properties, converter);
	// }

}
