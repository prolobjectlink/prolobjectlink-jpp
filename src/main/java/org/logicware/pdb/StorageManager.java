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

/**
 * Storage Manager is a file system manager to store data. Is an specification
 * of of {@link PersistentContainer} that manage many file and data folders. The
 * storage manager have two mode {@link Storage} and {@link StoragePool}
 * indicate by {@link StorageMode}. Single storage can be more faster for few
 * data. Storage pool distribute all data in short data file limited by data
 * number.
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Storage
 * @see StoragePool
 */
public interface StorageManager extends PersistentContainer {

	PersistentContainer loggerOf(Class<?> clazz);

	PersistentContainer masterOf(Class<?> clazz);

}
