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
package org.logicware.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class JavaLists {

	private JavaLists() {
	}

	/**
	 * Create a new ArrayList.
	 * 
	 * @param <T>
	 *            the type
	 * @return the object
	 */
	public static <T> List<T> arrayList() {
		return new ArrayList<T>();
	}

	/**
	 * Create a new ArrayList.
	 * 
	 * @param <T>
	 *            the type
	 * @param capacity
	 *            the initial capacity
	 * @return the object
	 */
	public static <T> List<T> arrayList(int capacity) {
		return new ArrayList<T>(capacity);
	}

	/**
	 * Create a new ArrayList.
	 * 
	 * @param <T>
	 *            the type
	 * @param c
	 *            the collection
	 * @return the object
	 */
	public static <T> List<T> arrayList(Collection<T> c) {
		return new ArrayList<T>(c);
	}

}
