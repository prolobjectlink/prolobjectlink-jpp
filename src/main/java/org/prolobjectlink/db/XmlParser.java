/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2019 Prolobjectlink Project
 * %%
 * COMMON DEVELOPMENT AND DISTRIBUTION LICENSE (CDDL) Version 1.0
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Common Development and Distrubtion License as
 * published by the Sun Microsystems, either version 1.0 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the Common Development and Distrubtion
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-1.0.html>.
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
