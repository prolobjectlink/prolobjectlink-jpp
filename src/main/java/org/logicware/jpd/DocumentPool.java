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
package org.logicware.jpd;

import java.io.FileFilter;
import java.util.List;

/**
 * Document container that have many documents with limited clauses storage in a
 * located root folder.
 * 
 * @author Jose Zalacain
 * @since 1.0
 * @see Document
 * @see DocumentContainer
 * @see DocumentManager
 */
public interface DocumentPool extends DocumentContainer {

	public Document createDocument(String location, int maxCapacity);

	public List<Document> getDocuments();

	public int getCapacity();

	/**
	 * Documents number in this document pool.
	 * 
	 * @return Documents number in this document pool.
	 * @since 1.0
	 */
	public int getPoolSize();

	/**
	 * Check if this document pool no have any document. Equivalent to
	 * {@code #getPoolSize()==0}.
	 * 
	 * @return true if document pool no have any document and false otherwise
	 * @since 1.0
	 */
	public boolean isEmpty();

	/**
	 * File filter used for filter pool files into document pool.
	 * 
	 * @return File filter used for filter pool files into document pool.
	 * @since 1.0
	 */
	public FileFilter getFilter();

	public void flush();

}
