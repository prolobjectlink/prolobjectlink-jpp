/*
 * #%L
 * prolobjectlink-db
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.pdb.prolog;

import org.logicware.pdb.ContainerFactory;
import org.logicware.pdb.ObjectConverter;
import org.logicware.pdb.RelationalCache;
import org.logicware.pdb.Schema;
import org.logicware.pdb.Settings;
import org.logicware.pdb.cache.AbstractRelationalCache;
import org.logicware.pdb.graph.RelationalGraph;
import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;

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
