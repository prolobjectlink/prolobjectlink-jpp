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
	public static final String SECRET = Settings.class.getPackage().getName().concat(".password");
	public static final String USER = Settings.class.getPackage().getName().concat(".user");

	public static final String STORAGE = Settings.class.getPackage().getName().concat(".storage.mode");
	public static final String TIME_GRANULARITY = Settings.class.getPackage().getName().concat(".lock.granularity");
	public static final String SLEEP_GAP = Settings.class.getPackage().getName().concat(".lock.timegap");
	public static final String LOCK_SLEEP = Settings.class.getPackage().getName().concat(".lock.sleep");

	public static final String CONFIG = "etc" + File.separator + "prolobjectlink.xml";

	public static final String DEFAULT_USER = "sa";
	public static final String DEFAULT_SECRET = "";

	private static final int DEFAULT_TIME_GRANULARITY = 80;
	private static final int DEFAULT_LOCK_SLEEP = 40;
	private static final int DEFAULT_SLEEP_GAP = 1;

	private ContainerFactory containerFactory;
	private PrologProvider prologProvider;
	private StorageMode storageMode;
	private Properties properties;

	private String username;
	private String password;

	private int time_granularity;
	private int lock_sleep;
	private int sleep_gap;

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
		this.time_granularity = DEFAULT_TIME_GRANULARITY;
		this.lock_sleep = DEFAULT_LOCK_SLEEP;
		this.sleep_gap = DEFAULT_SLEEP_GAP;
		this.storageMode = StorageMode.STORAGE_POOL;
		this.password = DEFAULT_SECRET;
		this.username = DEFAULT_USER;
		prologProvider = containerFactory.getProvider();
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		properties.put(TIME_GRANULARITY, DEFAULT_TIME_GRANULARITY);
		properties.put(STORAGE, StorageMode.STORAGE_POOL);
		properties.put(LOCK_SLEEP, DEFAULT_LOCK_SLEEP);
		properties.put(SLEEP_GAP, DEFAULT_SLEEP_GAP);
		properties.put(SECRET, DEFAULT_SECRET);
		properties.put(USER, DEFAULT_USER);
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

			this.time_granularity = Integer
					.valueOf((String) properties.getOrDefault(TIME_GRANULARITY, DEFAULT_TIME_GRANULARITY));
			this.lock_sleep = Integer.valueOf((String) properties.getOrDefault(LOCK_SLEEP, DEFAULT_LOCK_SLEEP));
			this.sleep_gap = Integer.valueOf((String) properties.getOrDefault(SLEEP_GAP, DEFAULT_SLEEP_GAP));
			this.password = properties.getProperty(SECRET, DEFAULT_SECRET);
			this.username = properties.getProperty(USER, DEFAULT_USER);
			String storage = properties.getProperty(STORAGE);
			if (storage.equals("" + StorageMode.SINGLE_STORAGE + "")) {
				this.storageMode = StorageMode.SINGLE_STORAGE;
			} else {
				this.storageMode = StorageMode.STORAGE_POOL;
			}
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
			properties.put(TIME_GRANULARITY, "" + time_granularity + "");
			properties.put(LOCK_SLEEP, "" + lock_sleep + "");
			properties.put(SLEEP_GAP, "" + sleep_gap + "");
			properties.put(STORAGE, "" + storageMode + "");
			properties.put(SECRET, password);
			properties.put(USER, username);
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

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public HierarchicalDatabase createHierarchicalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return getContainerFactory().createHierarchicalDatabase(storageMode, name, user);
	}

	/**
	 * @deprecated Use {@link MemoryHierarchical,EmbeddedHierarchical,
	 *             RemoteHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
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

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
	public RelationalDatabase createRelationalDatabase(StorageMode storageMode, String name, DatabaseUser user) {
		return getContainerFactory().createRelationalDatabase(storageMode, name, user);
	}

	/**
	 * @deprecated Use {@link MemoryRelational,EmbeddedRelational,
	 *             RemoteRelationalHierarchical} instead
	 * @author Jose Zalacain
	 *
	 */
	@Deprecated
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

	public final StorageMode getStorageMode() {
		return storageMode;
	}

	public final void setStorageMode(StorageMode storageMode) {
		this.storageMode = storageMode;
	}

	public final String getUsername() {
		return username;
	}

	public final void setUsername(String username) {
		this.username = username;
	}

	public final String getPassword() {
		return password;
	}

	public final void setPassword(String password) {
		this.password = password;
	}

	public final int getTimeGranularity() {
		return time_granularity;
	}

	public final void setTimeGranularity(int time_granularity) {
		this.time_granularity = time_granularity;
	}

	public final int getLockSleep() {
		return lock_sleep;
	}

	public final void setLockSleep(int lock_sleep) {
		this.lock_sleep = lock_sleep;
	}

	public final int getSleepGap() {
		return sleep_gap;
	}

	public final void setSleepGap(int sleep_gap) {
		this.sleep_gap = sleep_gap;
	}

}
