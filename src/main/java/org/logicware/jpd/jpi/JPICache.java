/*
 * #%L
 * prolobjectlink
 * %%
 * Copyright (C) 2012 - 2017 Logicware Project
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
package org.logicware.jpd.jpi;

import org.logicware.jpd.AbstractCache;
import org.logicware.jpd.Cache;
import org.logicware.jpd.ObjectConverter;
import org.logicware.jpd.Properties;
import org.logicware.jpi.PrologProvider;
import org.logicware.jpi.PrologTerm;

public class JPICache extends AbstractCache implements Cache {

	public JPICache(PrologProvider provider) {
		super(provider, new Properties(), new JPIObjectConverter(provider));
	}

	public JPICache(PrologProvider provider, Properties properties) {
		super(provider, properties, new JPIObjectConverter(provider));
	}

	public JPICache(PrologProvider provider, Properties properties, ObjectConverter<PrologTerm> converter) {
		super(provider, properties, converter);
	}

}
