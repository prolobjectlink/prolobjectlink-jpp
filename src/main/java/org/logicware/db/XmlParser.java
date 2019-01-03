/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 WorkLogic Project
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
package org.logicware.db;

public interface XmlParser {

	//
	public static final String XML_ROOT = "META-INF/";
	public static final String XML_BASE_NAME = "persistence.xml";
	public static final String XSD_DIR = "javax/persistence/";
	public static final String XML = XML_ROOT + XML_BASE_NAME;
	public static final String NS_URI = "http://java.sun.com/xml/ns/persistence";

	//
	static final String PERSISTENCE_VERSION = "version";
	static final String PERSISTENCE_XMLNS = "xmlns";
	static final String PERSISTENCE_XMLNS_XSI = "xmlns:xsi";
	static final String PERSISTENCE_XSI_SCHEMALOCATION = "xsi:schemaLocation";

	//
	static final String PERSISTENCE_UNIT = "persistence-unit";
	static final String PERSISTENCE_UNIT_NAME = "name";
	static final String PERSISTENCE_UNIT_TRANSACTION_TYPE = "transaction-type";

	//
	static final String PERSISTENCE_DESCRIPTION = "description";
	static final String PERSISTENCE_PROVIDER = "provider";
	static final String PERSISTENCE_JTA_DATA_SOURCE = "jta-data-source";
	static final String PERSISTENCE_NON_JTA_DATA_SOURCE = "non-jta-data-source";
	static final String PERSISTENCE_MAPPING_FILE = "mapping-file";
	static final String PERSISTENCE_JAR_FILE = "jar-file";
	static final String PERSISTENCE_CLASS = "class";
	static final String PERSISTENCE_EXCLUDE_UNLISTED_CLASSES = "exclude-unlisted-classes";
	static final String PERSISTENCE_SHARED_CACHE_MODE = "shared-cache-mode";
	static final String PERSISTENCE_VALIDATION_MODE = "validation-mode";
	static final String PERSISTENCE_PROPERTIES = "properties";
	static final String PERSISTENCE_PROPERTY_NAME = "name";
	static final String PERSISTENCE_PROPERTY_VALUE = "value";

}
