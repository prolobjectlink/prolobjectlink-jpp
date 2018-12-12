/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2018 Logicware Project
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
package org.logicware.database.jpa;

import static org.logicware.database.jpa.spi.JPAPersistenceXmlParser.XML;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import org.logicware.AbstractWrapper;
import org.logicware.database.EmbeddedDatabase;
import org.logicware.database.jpa.spi.JPAPersistenceXmlParser;
import org.logicware.database.persistent.EmbeddedHierarchical;

public abstract class JpaAbstractProvider extends AbstractWrapper implements PersistenceProvider {

	protected final ProviderUtil providerUtil = new JPAProviderUtil();

	protected final void checkPersistenceUnitExistence(String emName, Map<String, PersistenceUnitInfo> m) {
		if (m.get(emName) == null) {
			throw new PersistenceException("There are not persistence unit named " + emName);
		}
	}

	public final EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
		checkPersistenceUnitExistence(emName, m);
		PersistenceUnitInfo unit = m.get(emName);
		return createContainerEntityManagerFactory(unit, map);
	}

	public final EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		EmbeddedDatabase database = EmbeddedHierarchical.newInstance(info.getPersistenceUnitName());
		System.out.println("Go fine Camilo???");
		return new JPAEntityManagerFactory(database);
	}

	public final void generateSchema(PersistenceUnitInfo info, Map map) {
		// TODO Auto-generated method stub

	}

	public final boolean generateSchema(String persistenceUnitName, Map map) {
		JPAPersistenceXmlParser p = new JPAPersistenceXmlParser();
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		Map<String, PersistenceUnitInfo> m = p.parsePersistenceXml(loader.getResource(XML));
		checkPersistenceUnitExistence(persistenceUnitName, m);
		PersistenceUnitInfo unit = m.get(persistenceUnitName);
		generateSchema(unit, map);
		return true;
	}

	public final ProviderUtil getProviderUtil() {
		return providerUtil;
	}

}
