/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the Prolobjectlink Project nor the names of its contributors
 *    may be used to endorse or promote products derived from this software without
 *    specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED
 * OF THE POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */
package org.prolobjectlink.db;

/**
 * 
 * @author Jose Zalacain
 * @since 1.0
 */
public interface XmlParser {

	//
	public static final String XML_ROOT = "META-INF/";
	public static final String XML_BASE_NAME = "persistence.xml";
	// public static final String XSD_DIR = "javax/persistence/";
	public static final String XSD_DIR = XML_ROOT + "persistence/";
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
