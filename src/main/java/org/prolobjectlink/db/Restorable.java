/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
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
package org.prolobjectlink.db;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface Restorable {

	/**
	 * Create a database backup named zipFileName.zip hosted on the specified
	 * directory.
	 * 
	 * @param directory   location to save create zipFileName.zip
	 * @param zipFileName database backup file name
	 * @since 1.0
	 * @see #restore(String, String)
	 */
	public void backup(String directory, String zipFileName);

	/**
	 * Restore a database backup named zipFileName in the directory location.
	 * 
	 * @param directory   location to restore database
	 * @param zipFileName database backup file name
	 * @since 1.0
	 * @see #backup(String, String)
	 */
	public void restore(String directory, String zipFileName);

}
