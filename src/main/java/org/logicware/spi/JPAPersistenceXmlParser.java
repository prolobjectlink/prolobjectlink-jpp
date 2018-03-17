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
package org.logicware.spi;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class JPAPersistenceXmlParser {

    //
    public static final String XML_ROOT = "META-INF/";
    public static final String XML_BASE_NAME = "persistence.xml";
    public static final String XSD_DIR = "javax/persistence/";
    public static final String XML = XML_ROOT + XML_BASE_NAME;
    public static final String NS_URI = "http://java.sun.com/xml/ns/persistence";

    //
    private static final String PERSISTENCE_VERSION = "version";
    private static final String PERSISTENCE_XMLNS = "xmlns";
    private static final String PERSISTENCE_XMLNS_XSI = "xmlns:xsi";
    private static final String PERSISTENCE_XSI_SCHEMALOCATION = "xsi:schemaLocation";

    //
    private static final String PERSISTENCE_UNIT = "persistence-unit";
    private static final String PERSISTENCE_UNIT_NAME = "name";
    private static final String PERSISTENCE_UNIT_TRANSACTION_TYPE = "transaction-type";

    //
    private static final String PERSISTENCE_DESCRIPTION = "description";
    private static final String PERSISTENCE_PROVIDER = "provider";
    private static final String PERSISTENCE_JTA_DATA_SOURCE = "jta-data-source";
    private static final String PERSISTENCE_NON_JTA_DATA_SOURCE = "non-jta-data-source";
    private static final String PERSISTENCE_MAPPING_FILE = "mapping-file";
    private static final String PERSISTENCE_JAR_FILE = "jar-file";
    private static final String PERSISTENCE_CLASS = "class";
    private static final String PERSISTENCE_EXCLUDE_UNLISTED_CLASSES = "exclude-unlisted-classes";
    private static final String PERSISTENCE_SHARED_CACHE_MODE = "shared-cache-mode";
    private static final String PERSISTENCE_VALIDATION_MODE = "validation-mode";
    private static final String PERSISTENCE_PROPERTIES = "properties";
    private static final String PERSISTENCE_PROPERTY_NAME = "name";
    private static final String PERSISTENCE_PROPERTY_VALUE = "value";

    public JPAPersistenceXmlParser() {
    }

    public Map<String, PersistenceUnitInfo> parsePersistenceXml(URL persistenceXml) {

	JPASchemaVersion schemaVersion;
	JPAPersistenceVersion persistenceVersion;
	Map<String, PersistenceUnitInfo> persistenceUnits = new HashMap<String, PersistenceUnitInfo>();

	try {

	    InputStream inputStream = new BufferedInputStream(persistenceXml.openStream());
	    DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document document = builder.parse(inputStream);

	    //
	    String xmlVersion = document.getXmlVersion();
	    String xmlEncoding = document.getXmlEncoding();
	    schemaVersion = new JPASchemaVersion(xmlVersion, xmlEncoding);

	    //
	    Element root = document.getDocumentElement();
	    String xmlPersistenceVersion = root.getAttribute(PERSISTENCE_VERSION);
	    String xmlPersistenceXmlns = root.getAttribute(PERSISTENCE_XMLNS);
	    String xmlPersistenceXmlnsXsi = root.getAttribute(PERSISTENCE_XMLNS_XSI);
	    String xmlPersistenceXsiSchemalocation = root.getAttribute(PERSISTENCE_XSI_SCHEMALOCATION);
	    persistenceVersion = new JPAPersistenceVersion(xmlPersistenceVersion, xmlPersistenceXmlns,
		    xmlPersistenceXmlnsXsi, xmlPersistenceXsiSchemalocation);

	    //
	    String schemaPath = XSD_DIR + persistenceVersion.getFileName();
	    InputStream is = JPAPersistenceXmlParser.class.getClassLoader().getResourceAsStream(schemaPath);
	    Schema schema = newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(is));
	    assertKnowSchema(schema);

	    //
	    NodeList persistenceUnitNodeList = root.getElementsByTagName(PERSISTENCE_UNIT);
	    for (int i = 0; i < persistenceUnitNodeList.getLength(); i++) {
		Node persistenceUnitNode = persistenceUnitNodeList.item(i);
		NamedNodeMap map = persistenceUnitNode.getAttributes();
		String persistenceUnitName = map.getNamedItem(PERSISTENCE_UNIT_NAME).getNodeValue();
		String persistenceUnitTransactionType = map.getNamedItem(PERSISTENCE_UNIT_TRANSACTION_TYPE)
			.getNodeValue();
		PersistenceUnitTransactionType transactionType = persistenceUnitTransactionType.equals("RESOURCE_LOCAL")
			? PersistenceUnitTransactionType.RESOURCE_LOCAL : PersistenceUnitTransactionType.JTA;

		JPAPersistenceUnitInfo persistenceUnitInfo = new JPAPersistenceUnitInfo(persistenceXml, schemaVersion,
			persistenceVersion, persistenceUnitName, transactionType);

		//
		NodeList persistenceUnitElementNodeList = persistenceUnitNode.getChildNodes();
		for (int j = 0; j < persistenceUnitElementNodeList.getLength(); j++) {
		    Node node = persistenceUnitElementNodeList.item(j);
		    String nodeName = node.getNodeName();
		    String nodeContent = node.getTextContent();
		    if (nodeName.equals(PERSISTENCE_DESCRIPTION)) {
			persistenceUnitInfo.setPersistenceDescription(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_PROVIDER)) {
			persistenceUnitInfo.setPersistenceProviderClassName(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_JTA_DATA_SOURCE)) {
			persistenceUnitInfo.setPersistenceJtaDataSource(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_NON_JTA_DATA_SOURCE)) {
			persistenceUnitInfo.setPersistenceNonJtaDataSource(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_MAPPING_FILE)) {
			persistenceUnitInfo.addMappingFilesNames(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_JAR_FILE)) {
			persistenceUnitInfo.addJarFileUrl(new URL(nodeContent));
		    } else if (nodeName.equals(PERSISTENCE_CLASS)) {
			persistenceUnitInfo.addManagedClass(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_EXCLUDE_UNLISTED_CLASSES)) {
			persistenceUnitInfo.setExcludeUnlistedClasses(Boolean.valueOf(nodeContent));
		    } else if (nodeName.equals(PERSISTENCE_SHARED_CACHE_MODE)) {
			persistenceUnitInfo.setValidationMode(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_VALIDATION_MODE)) {
			persistenceUnitInfo.setSharedCacheMode(nodeContent);
		    } else if (nodeName.equals(PERSISTENCE_PROPERTIES)) {
			NodeList persistenceUnitPropertiesNodeList = node.getChildNodes();
			for (int k = 1; k < persistenceUnitPropertiesNodeList.getLength(); k += 2) {
			    Node persistenceUnitPropertyNode = persistenceUnitPropertiesNodeList.item(k);
			    NamedNodeMap namedNodeMap = persistenceUnitPropertyNode.getAttributes();
			    String persistencePropertyName = namedNodeMap.getNamedItem(PERSISTENCE_PROPERTY_NAME)
				    .getNodeValue();
			    String persistencePropertyValue = namedNodeMap.getNamedItem(PERSISTENCE_PROPERTY_VALUE)
				    .getNodeValue();
			    persistenceUnitInfo.setProperty(persistencePropertyName, persistencePropertyValue);
			}
		    }
		}
		persistenceUnits.put(persistenceUnitName, persistenceUnitInfo);
	    }

	    is.close();

	} catch (Exception e) {
	    throw new PersistenceException("Error parsing " + persistenceXml, e);
	}
	return persistenceUnits;
    }

    private void assertKnowSchema(Schema schema) {
	if (schema == null) {
	    throw new PersistenceException("Schema is unknown");
	}
    }
}
