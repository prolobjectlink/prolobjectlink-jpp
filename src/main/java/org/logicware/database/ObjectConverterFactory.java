/*
 * #%L
 * prolobjectlink-jpp
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
package org.logicware.database;

import org.logicware.database.util.JavaReflect;
import org.logicware.prolog.PrologProvider;

public final class ObjectConverterFactory {

	public static <T> ObjectConverter<T> createConverter(Class<T> cls, PrologProvider provider) {
		return (ObjectConverter<T>) JavaReflect.newInstance(cls, new Class[] { PrologProvider.class },
				new PrologProvider[] { provider });
	}

	private ObjectConverterFactory() {
	}

}
