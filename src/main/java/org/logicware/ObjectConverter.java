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

import java.util.Map;

import org.logicware.prolog.PrologProvider;

public interface ObjectConverter<T> {

	public String removeApices(String functor);

	public boolean containApices(String functor);

	//

	public Class<?> toClass(T prologTerm);

	public Class<?>[] toClassArray(T[] terms);

	public Object toObject(T prologTerm);

	public Object[] toObjectsArray(T[] terms);

	public Object toObject(Class<?> clazz, Map<String, T> solutionsMap);

	//

	public T toTerm(Object object);

	public T[] toTermsArray(String string);

	public T[] toTermsArray(Object[] objects);

	public T toTerm(Object object, Map<String, T> solutionsMap);

	public PrologProvider getProvider();

}
