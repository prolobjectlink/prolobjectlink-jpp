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
package org.logicware.jpd;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.logicware.jpi.PrologProvider;

public final class ObjectConverterFactory {

    private static final ExceptionFactory EXCEPTION_FACTORY = new ExceptionFactory();

    public ObjectConverterFactory() {
    }

    public static <T> ObjectConverter<T> createConverter(Class<T> cls, PrologProvider provider) {
	ObjectConverter<T> converter = null;
	try {
	    Constructor<?> constructor = cls.getConstructor(PrologProvider.class);
	    converter = (ObjectConverter<T>) constructor.newInstance(provider);
	} catch (InstantiationException e) {
	    throw EXCEPTION_FACTORY.instantiationException(cls.getName(), e);
	} catch (IllegalAccessException e) {
	    throw EXCEPTION_FACTORY.illegalAccessException(cls.getName(), e);
	} catch (NoSuchMethodException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (SecurityException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IllegalArgumentException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (InvocationTargetException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return converter;
    }

}
