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
package org.prolobjectlink.db.etc;

import static org.prolobjectlink.db.XmlParser.XML;

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

import org.prolobjectlink.db.ContainerFactory;
import org.prolobjectlink.db.DatabaseUser;
import org.prolobjectlink.db.HierarchicalCache;
import org.prolobjectlink.db.HierarchicalDatabase;
import org.prolobjectlink.db.ObjectConverter;
import org.prolobjectlink.db.RelationalCache;
import org.prolobjectlink.db.RelationalDatabase;
import org.prolobjectlink.db.SaveLoad;
import org.prolobjectlink.db.Schema;
import org.prolobjectlink.db.Storage;
import org.prolobjectlink.db.StorageGraph;
import org.prolobjectlink.db.StorageManager;
import org.prolobjectlink.db.StorageMode;
import org.prolobjectlink.db.StoragePool;
import org.prolobjectlink.db.util.JavaReflect;
import org.prolobjectlink.logging.LoggerConstants;
import org.prolobjectlink.logging.LoggerUtils;
import org.prolobjectlink.prolog.PrologConverter;
import org.prolobjectlink.prolog.PrologProvider;
import org.prolobjectlink.prolog.PrologTerm;

public final class Settings extends AbstractMap<Object, Object>
		implements Map<Object, Object>, ContainerFactory, SaveLoad<Settings> {

	protected URL persistenceXml = Thread.currentThread().getContextClassLoader().getResource(XML);

	public static final String PROVIDER = Settings.class.getPackage().getName().concat(".prologProvider");
	public static final String FACTORY = Settings.class.getPackage().getName().concat(".containerFactory");
	public static final String SECRET = Settings.class.getPackage().getName().concat(".password");
	public static final String USER = Settings.class.getPackage().getName().concat(".user");

	public static final String PORT = Settings.class.getPackage().getName().concat(".server.port");
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
	private static final int DEFAULT_PORT = 5370;

	private ContainerFactory containerFactory;
	private PrologProvider prologProvider;
	private StorageMode storageMode;
	private Properties properties;

	private String username;
	private String password;

	private int timeGranularity;
	private int lockSleep;
	private int sleepGap;
	private int port;

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
		this.timeGranularity = DEFAULT_TIME_GRANULARITY;
		this.lockSleep = DEFAULT_LOCK_SLEEP;
		this.sleepGap = DEFAULT_SLEEP_GAP;
		this.storageMode = StorageMode.STORAGE_POOL;
		this.password = DEFAULT_SECRET;
		this.username = DEFAULT_USER;
		this.port = DEFAULT_PORT;
		prologProvider = containerFactory.getProvider();
		properties.put(PROVIDER, prologProvider.getClass().getName());
		properties.put(FACTORY, containerFactory.getClass().getName());
		properties.put(TIME_GRANULARITY, DEFAULT_TIME_GRANULARITY);
		properties.put(STORAGE, StorageMode.STORAGE_POOL);
		properties.put(LOCK_SLEEP, DEFAULT_LOCK_SLEEP);
		properties.put(SLEEP_GAP, DEFAULT_SLEEP_GAP);
		properties.put(SECRET, DEFAULT_SECRET);
		properties.put(USER, DEFAULT_USER);
		properties.put(PORT, DEFAULT_PORT);
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

			this.timeGranularity = Integer
					.valueOf((String) properties.getProperty(TIME_GRANULARITY, "" + DEFAULT_TIME_GRANULARITY + ""));
			this.lockSleep = Integer.valueOf((String) properties.getProperty(LOCK_SLEEP, "" + DEFAULT_LOCK_SLEEP + ""));
			this.sleepGap = Integer.valueOf((String) properties.getProperty(SLEEP_GAP, "" + DEFAULT_SLEEP_GAP + ""));
			this.port = Integer.valueOf((String) properties.getProperty(PORT, "" + DEFAULT_PORT + ""));
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
			properties.put(TIME_GRANULARITY, "" + timeGranularity + "");
			properties.put(LOCK_SLEEP, "" + lockSleep + "");
			properties.put(SLEEP_GAP, "" + sleepGap + "");
			properties.put(STORAGE, "" + storageMode + "");
			properties.put(PORT, "" + port + "");
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
		} else if (!properties.equals(other.properties)) {
			return false;
		}
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

//	public <K> PrologConverter<K> getConverter() {
//		return containerFactory.getConverter();
//	}

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
		return timeGranularity;
	}

	public final void setTimeGranularity(int timeGranularity) {
		this.timeGranularity = timeGranularity;
	}

	public final int getLockSleep() {
		return lockSleep;
	}

	public final void setLockSleep(int lockSleep) {
		this.lockSleep = lockSleep;
	}

	public final int getSleepGap() {
		return sleepGap;
	}

	public final void setSleepGap(int sleepGap) {
		this.sleepGap = sleepGap;
	}

	public final int getPort() {
		return port;
	}

	public final void setPort(int port) {
		this.port = port;
	}

}
