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
package org.logicware;

/**
 * Wrapper class that hold under-laying prolog specific classes. Contains two
 * methods for unwrap hold class obtaining a down casting of the wrapper class.
 * 
 * @author Jose Zalacain
 * @see AbstractWrapper
 * @since 1.0
 */
public interface Wrapper {

	/**
	 * Down cast the current wrapper object to specific given class. Call
	 * {@code Wrapper#unwrap(Object, Class)} passing like argument this instance
	 * and the given class. If down casting is not possible raise a
	 * {@link RuntimeError}.
	 * 
	 * @param cls
	 *            class to obtain a down cast.
	 * @throws RuntimeError
	 *             if down casting is not possible
	 * @return current instance of type cls.
	 * @since 1.0
	 * @see #unwrap(Object, Class)
	 */
	<K> K unwrap(Class<K> cls);

	/**
	 * Down cast a given wrapper object to specific given class. If down casting
	 * is not possible raise a {@link RuntimeError}.
	 * 
	 * @param cls
	 *            class to obtain a down cast.
	 * @throws RuntimeError
	 *             if down casting is not possible
	 * @return current instance of type cls.
	 * @since 1.0
	 * @see #unwrap(Class)
	 */
	<K> K unwrap(Object o, Class<K> cls);

	/**
	 * 
	 * @param cls
	 * @return
	 * @since 1.0
	 */
	boolean isWrappedFor(Class<?> cls);

	boolean isWrappedFor(Object o, Class<?> cls);

}
