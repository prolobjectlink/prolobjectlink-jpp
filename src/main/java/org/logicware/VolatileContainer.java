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

import java.util.List;

public interface VolatileContainer extends Container {

	public <O> void add(O... facts);

	public <O> void modify(O match, O merge);

	public void remove(Class<?> clazz);

	public <O> void remove(O... facts);

	public Object find(String string) throws NonSolutionError;

	public Object find(String functor, Object... args) throws NonSolutionError;

	public <O> O find(O o) throws NonSolutionError;

	public <O> O find(Class<O> clazz) throws NonSolutionError;

	public <O> O find(Predicate<O> predicate) throws NonSolutionError;

	public List<Object> findAll(String string);

	public List<Object> findAll(String functor, Object... args);

	public <O> List<O> findAll(O o);

	public <O> List<O> findAll(Class<O> clazz);

	public <O> List<O> findAll(Predicate<O> predicate);

	/**
	 * Clear cache erase all objects in main memory. The {@code ObjectStore}
	 * implementations erase all objects in main memory such as a flush method
	 * invocation set to empty the physic file
	 */
	public void clear();

}
