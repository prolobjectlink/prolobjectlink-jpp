/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.pdb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.logicware.pdb.prolog.PrologProvider;

public final class ObjectConverterFactory {

	private ObjectConverterFactory() {
	}

	public static <T> ObjectConverter<T> createConverter(Class<T> cls, PrologProvider provider) {
		ObjectConverter<T> converter = null;
		try {
			Constructor<?> constructor = cls.getConstructor(PrologProvider.class);
			converter = (ObjectConverter<T>) constructor.newInstance(provider);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return converter;
	}

}