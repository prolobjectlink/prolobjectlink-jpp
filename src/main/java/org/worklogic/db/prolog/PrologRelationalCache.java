/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 WorkLogic Project
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
package org.worklogic.db.prolog;

import org.logicware.prolog.PrologProvider;
import org.logicware.prolog.PrologTerm;
import org.worklogic.db.ContainerFactory;
import org.worklogic.db.ObjectConverter;
import org.worklogic.db.RelationalCache;
import org.worklogic.db.RelationalGraph;
import org.worklogic.db.Schema;
import org.worklogic.db.cache.AbstractRelationalCache;
import org.worklogic.db.etc.Settings;

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
