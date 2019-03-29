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
package org.prolobjectlink.db.spi;

import static javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI;
import static javax.xml.validation.SchemaFactory.newInstance;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;

import org.prolobjectlink.db.DatabaseUnitInfo;
import org.prolobjectlink.db.XmlParser;
import org.prolobjectlink.db.xml.AbstractXmlParser;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public final class PersistenceXmlParser extends AbstractXmlParser implements XmlParser {

	public Map<String, DatabaseUnitInfo> parsePersistenceXml(URL persistenceXml) {

		PersistenceSchemaVersion schemaVersion;
		PersistenceVersion persistenceVersion;
		Map<String, DatabaseUnitInfo> persistenceUnits = new HashMap<String, DatabaseUnitInfo>();

		try {

			InputStream inputStream = new BufferedInputStream(persistenceXml.openStream());
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(inputStream);

			//
			String xmlVersion = document.getXmlVersion();
			String xmlEncoding = document.getXmlEncoding();
			schemaVersion = new PersistenceSchemaVersion(xmlVersion, xmlEncoding);

			//
			Element root = document.getDocumentElement();
			String xmlPersistenceVersion = root.getAttribute(PERSISTENCE_VERSION);
			String xmlPersistenceXmlns = root.getAttribute(PERSISTENCE_XMLNS);
			String xmlPersistenceXmlnsXsi = root.getAttribute(PERSISTENCE_XMLNS_XSI);
			String xmlPersistenceXsiSchemalocation = root.getAttribute(PERSISTENCE_XSI_SCHEMALOCATION);
			persistenceVersion = new PersistenceVersion(xmlPersistenceVersion, xmlPersistenceXmlns,
					xmlPersistenceXmlnsXsi, xmlPersistenceXsiSchemalocation);

			//
			String schemaPath = XSD_DIR + persistenceVersion.getFileName();
			InputStream is = PersistenceXmlParser.class.getClassLoader().getResourceAsStream(schemaPath);
			assert newInstance(W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(is)) != null;

			//
			NodeList persistenceUnitNodeList = root.getElementsByTagName(PERSISTENCE_UNIT);
			for (int i = 0; i < persistenceUnitNodeList.getLength(); i++) {
				Node persistenceUnitNode = persistenceUnitNodeList.item(i);
				NamedNodeMap map = persistenceUnitNode.getAttributes();
				String persistenceUnitName = map.getNamedItem(PERSISTENCE_UNIT_NAME).getNodeValue();
				String persistenceUnitTransactionType = map.getNamedItem(PERSISTENCE_UNIT_TRANSACTION_TYPE)
						.getNodeValue();

//				PersistenceUnitTransactionType transactionType = persistenceUnitTransactionType.equals("RESOURCE_LOCAL")
//						? PersistenceUnitTransactionType.RESOURCE_LOCAL
//						: PersistenceUnitTransactionType.JTA;

				PersistenceUnitInformation persistenceUnitInfo = new PersistenceUnitInformation(persistenceXml,
						schemaVersion, persistenceVersion, persistenceUnitName, persistenceUnitTransactionType);

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
			throw new RuntimeException("Error parsing " + persistenceXml, e);
		}
		return persistenceUnits;
	}

}
