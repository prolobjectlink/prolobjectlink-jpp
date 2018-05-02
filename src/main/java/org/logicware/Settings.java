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
package org.logicware;

import static org.logicware.provider.PersistenceXmlParser.XML;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.persistence.spi.PersistenceUnitInfo;

import org.logicware.logging.LoggerConstants;
import org.logicware.logging.LoggerUtils;
import org.logicware.prolog.PrologProvider;
import org.logicware.provider.PersistenceXmlParser;

public final class Settings extends AbstractMap<Object, Object>
		implements Map<Object, Object>, ContainerFactory, SaveLoad<Settings> {

	protected URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	public static final String PROVIDER = Settings.class.getPackage().getName().concat(".prologProvider");
	public static final String FACTORY = Settings.class.getPackage().getName().concat(".containerFactory");
	public static final String CONFIG = "etc" + File.separator + "settings.xml";
	public static final String PASSWORD = "javax.persistence.jdbc.password";
	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String URL = "javax.persistence.jdbc.url";

	private Map<String, PersistenceUnitInfo> units = new HashMap<String, PersistenceUnitInfo>();
	private ContainerFactory containerFactory;
	private PrologProvider prologProvider;
	private Properties properties;

	public Settings(Settings s) {
		containerFactory = s.containerFactory;
		prologProvider = s.prologProvider;
		properties = s.properties;
		units = s.units;
	}

	// TODO minimize code instructions on constructors

	public Settings(Class<? extends ContainerFactory> driver, PrologProvider provider) {
		this.prologProvider = provider;
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		containerFactory = (ContainerFactory) o.newInstance(driver);
		containerFactory.setProvider(provider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public Settings(String driver, PrologProvider provider) {
		this.prologProvider = provider;
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		Class<?> clazz = ReflectionUtils.classForName(driver);
		containerFactory = (ContainerFactory) o.newInstance(clazz);
		containerFactory.setProvider(provider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public Settings(Class<? extends ContainerFactory> driver, Class<? extends PrologProvider> provider) {
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		prologProvider = (PrologProvider) o.newInstance(provider);
		containerFactory = (ContainerFactory) o.newInstance(driver);
		containerFactory.setProvider(prologProvider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public Settings(String driver, Class<? extends PrologProvider> provider) {
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		Class<?> clazz = ReflectionUtils.classForName(driver);
		prologProvider = (PrologProvider) o.newInstance(provider);
		containerFactory = (ContainerFactory) o.newInstance(clazz);
		containerFactory.setProvider(prologProvider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public Settings(Class<? extends ContainerFactory> driver, String provider) {
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		Class<?> clazz = ReflectionUtils.classForName(provider);
		prologProvider = (PrologProvider) o.newInstance(clazz);
		containerFactory = (ContainerFactory) o.newInstance(driver);
		containerFactory.setProvider(prologProvider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public Settings(String driver, String provider) {
		this.properties = new Properties();
		ObjectReflector o = new ObjectReflector();
		Class<?> clazzDriver = ReflectionUtils.classForName(driver);
		Class<?> clazzProvider = ReflectionUtils.classForName(provider);
		prologProvider = (PrologProvider) o.newInstance(clazzProvider);
		containerFactory = (ContainerFactory) o.newInstance(clazzDriver);
		containerFactory.setProvider(prologProvider);
		containerFactory.setSettings(this);
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
	}

	public ContainerFactory getContainerFactory() {
		return containerFactory;
	}

	public Map<String, PersistenceUnitInfo> getPersistenceUnits() {
		return units;
	}

	public Settings load() {
		try {
			ObjectReflector o = new ObjectReflector();
			properties.loadFromXML(new FileInputStream(CONFIG));
			String driver = properties.getProperty(FACTORY);
			String provider = properties.getProperty(PROVIDER);
			Class<?> clazzDriver = ReflectionUtils.classForName(driver);
			Class<?> clazzProvider = ReflectionUtils.classForName(provider);
			prologProvider = (PrologProvider) o.newInstance(clazzProvider);
			containerFactory = (ContainerFactory) o.newInstance(clazzDriver);
			containerFactory.setProvider(prologProvider);
			containerFactory.setSettings(this);
			units = new PersistenceXmlParser().parsePersistenceXml(persistenceXml);
		} catch (FileNotFoundException e) {
			LoggerUtils.error(getClass(), LoggerConstants.FILE_NOT_FOUND, e);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
		}
		return this;
	}

	public Settings save() {
		try {
			properties.put(PROVIDER, prologProvider.getClass().getName());
			properties.put(FACTORY, containerFactory.getClass().getName());
			properties.storeToXML(new FileOutputStream(CONFIG), null);
		} catch (IOException e) {
			LoggerUtils.error(getClass(), LoggerConstants.IO_ERROR, e);
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

	@Override
	public Set<Entry<Object, Object>> entrySet() {
		return properties.entrySet();
	}

	public String getPassword() {
		return properties.getProperty(PASSWORD);
	}

	public String getDriver() {
		return properties.getProperty(DRIVER);
	}

	public String getUser() {
		return properties.getProperty(USER);
	}

	public String getURL() {
		return properties.getProperty(URL);
	}

}
