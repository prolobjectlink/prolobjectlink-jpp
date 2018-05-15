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

import static org.logicware.pdb.jpa.spi.JPAPersistenceXmlParser.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.logicware.pdb.logging.LoggerConstants;
import org.logicware.pdb.logging.LoggerUtils;
import org.logicware.pdb.prolog.PrologConverter;
import org.logicware.pdb.prolog.PrologProvider;
import org.logicware.pdb.prolog.PrologTerm;
import org.logicware.pdb.util.JavaReflect;

public final class Settings extends AbstractMap<Object, Object>
		implements Map<Object, Object>, ContainerFactory, SaveLoad<Settings> {

	protected URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	public static final String PROVIDER = Settings.class.getPackage().getName().concat(".prologProvider");
	public static final String FACTORY = Settings.class.getPackage().getName().concat(".containerFactory");
	public static final String CONFIG = "etc" + File.separator + "prolobjectlink.xml";

	private ContainerFactory containerFactory;
	private PrologProvider prologProvider;
	private Properties properties;

	public Settings() {

	}

	public Settings(String driver) {
		this((Class<? extends ContainerFactory>) JavaReflect.classForName(driver));
	}

	public Settings(Class<? extends ContainerFactory> driver) {
		this((ContainerFactory) JavaReflect.newInstance(driver));
	}

	public Settings(ContainerFactory containerFactory) {
		this.properties = new Properties();
		this.containerFactory = containerFactory;
		this.containerFactory.setSettings(this);
		prologProvider = containerFactory.getProvider();
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
	}

	public ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public Settings load() {
		try {
			properties.loadFromXML(new FileInputStream(CONFIG));
			String driver = properties.getProperty(FACTORY);
			String provider = properties.getProperty(PROVIDER);
			Class<?> clazzDriver = JavaReflect.classForName(driver);
			Class<?> clazzProvider = JavaReflect.classForName(provider);
			prologProvider = (PrologProvider) JavaReflect.newInstance(clazzProvider);
			containerFactory = (ContainerFactory) JavaReflect.newInstance(clazzDriver);
			containerFactory.setProvider(prologProvider);
			containerFactory.setSettings(this);
		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return this;
	}

	public Settings save() {
		try {
			properties.put(PROVIDER, prologProvider.getClass().getName());
			properties.put(FACTORY, containerFactory.getClass().getName());
			properties.storeToXML(new FileOutputStream(CONFIG), null);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO, e);
		}
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((properties == null) ? 0 : properties.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Settings other = (Settings) obj;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		return true;
	}

	public HierarchicalCache createHierarchicalCache() {
		return getContainerFactory().createHierarchicalCache();
	}

	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return getContainerFactory().createHierarchicalDatabase(storageMode, name, user);
	}

	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, String username,
			String password) {
		return containerFactory.createHierarchicalDatabase(storageMode, name, username, password);
	}

	public Storage createStorage(String location) {
		return getContainerFactory().createStorage(location);
	}

	public StoragePool createStoragePool(String poolRoot, String poolName) {
		return getContainerFactory().createStoragePool(poolRoot, poolName);
	}

	public StorageGraph createStorageGraph(String path, Schema schema, StorageMode storageMode) {
		return containerFactory.createStorageGraph(path, schema, storageMode);
	}

	public StorageManager createStorageManager(String path, StorageMode storageMode) {
		return getContainerFactory().createStorageManager(path, storageMode);
	}

	public RelationalCache createRelationalCache(Schema schema) {
		return containerFactory.createRelationalCache(schema);
	}

	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return getContainerFactory().createRelationalDatabase(storageMode, name, user);
	}

	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, String username,
			String password) {
		return containerFactory.createRelationalDatabase(storageMode, name, username, password);
	}

	public ObjectConverter<PrologTerm> getObjectConverter() {
		return containerFactory.getObjectConverter();
	}

	public void setProvider(PrologProvider provider) {
		this.prologProvider = provider;
	}

	public void setSettings(Settings s) {
		containerFactory = s.containerFactory;
		prologProvider = s.prologProvider;
		properties = s.properties;
	}

	public Settings getSettings() {
		return this;
	}

	public PrologProvider getProvider() {
		return prologProvider;
	}

	@Override
	public Object put(Object key, Object value) {
		return properties.put(key, value);
	}

	public <K> PrologConverter<K> getConverter() {
		return containerFactory.getConverter();
	}

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return properties.entrySet();
	}

	public Map<Object, Object> asMap() {
		return this;
	}

}
