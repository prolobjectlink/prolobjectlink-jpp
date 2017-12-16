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
package org.logicware.jpd.util;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public final class ReadWriteLinkedList<E> extends ReadWriteList<E> implements List<E> {

    public ReadWriteLinkedList() {
	super(new LinkedList<E>());
    }

    public ReadWriteLinkedList(Collection<? extends E> c) {
	super(new LinkedList<E>(c));
    }

}
