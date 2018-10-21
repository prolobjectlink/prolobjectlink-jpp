/*
 * #%L
 * prolobjectlink-jpi
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

import java.util.Collection;
import java.util.List;

public class PrologLinkedList<E> extends AbstractLinkedList<E> implements List<E> {

	private static final long serialVersionUID = 2370240611871530505L;

	/**
	 * Create new empty list
	 */
	PrologLinkedList() {
	}

	/**
	 * Create new list with element and empty tail
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	PrologLinkedList(E e) {
		super(e);
	}

	/**
	 * Create new list with element and next reference
	 * 
	 * @param e first element
	 * @since 1.0
	 */
	PrologLinkedList(E e, AbstractLinkedList<E> next) {
		super(next);
	}

	PrologLinkedList(Collection<? extends E> c) {
		super(c);
	}

}