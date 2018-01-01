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
package org.logicware.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ReadWriteArrayList<E> extends ReadWriteList<E> implements List<E> {

	public ReadWriteArrayList() {
		super(new ArrayList<E>());
	}

	public ReadWriteArrayList(int initialCapacity) {
		super(new ArrayList<E>(initialCapacity));
	}

	public ReadWriteArrayList(Collection<? extends E> c) {
		super(new ArrayList<E>(c));
	}

}
