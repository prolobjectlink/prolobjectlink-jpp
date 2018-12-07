/*
 * #%L
 * prolobjectlink-db
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
package org.logicware.database;

import java.util.Map;

import org.logicware.GraphEdge;
import org.logicware.prolog.PrologTerm;

public interface RelationalEdge<R> extends GraphEdge<R> {

	public Map<PrologTerm, PrologTerm> getCache();

	// one to one

	public boolean isLinkLink();

	// one to many

	public boolean isLinkLinkList();

	public boolean isLinkLinkMap();

	public boolean isLinkLinkSet();

	// many to one

	public boolean isLinkListLink();

	public boolean isLinkMapLink();

	public boolean isLinkSetLink();

	// many to many

	public boolean isLinkListLinkList();

	public boolean isLinkMapLinkList();

	public boolean isLinkSetLinkList();

	public boolean isLinkListLinkMap();

	public boolean isLinkMapLinkMap();

	public boolean isLinkSetLinkMap();

	public boolean isLinkListLinkSet();

	public boolean isLinkMapLinkSet();

	public boolean isLinkSetLinkSet();

	// instances checkers

	public boolean isList(Class<?> clazz);

	public boolean isMap(Class<?> clazz);

	public boolean isSet(Class<?> clazz);

	public boolean isCollection(Class<?> clazz);

}
