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
package org.prolobjectlink.db.spi;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.persistence.SharedCacheMode;
import javax.persistence.ValidationMode;
import javax.persistence.spi.ClassTransformer;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.sql.DataSource;

import org.prolobjectlink.db.DatabaseProperties;

public final class JPAPersistenceUnitInfo implements PersistenceUnitInfo {

	private static final String ALL = "ALL";
	private static final String NONE = "NONE";
	private static final String AUTO = "AUTO";
	private static final String CALLBACK = "CALLBACK";
	private static final String UNSPECIFIED = "UNSPECIFIED";
	private static final String ENABLE_SELECTIVE = "ENABLE_SELECTIVE";
	private static final String DISABLE_SELECTIVE = "DISABLE_SELECTIVE";

	// persistence xml location
	private final URL persistenceUnitRootUrl;

	// xml headers
	private final PersistenceSchemaVersion xmlVersion;
	private final PersistenceVersion persistenceVersion;

	// persistence unit attributes
	private final String unitName;
	private final PersistenceUnitTransactionType transactionType;

	//
	private final List<URL> jarFileUrls = new ArrayList<URL>();
	private final List<String> managedClasses = new ArrayList<String>();
	private final Properties properties = new DatabaseProperties();
	private final List<String> mappingFileNames = new ArrayList<String>();
	private final Set<ClassTransformer> classTransformers = new HashSet<ClassTransformer>();

	//
	private String persistenceDescription;
	private String persistenceProviderClassName;
	private String persistenceJtaDataSource;
	private String persistenceNonJtaDataSource;
	private boolean excludeUnlistedClasses = true;
	private ValidationMode validationMode = ValidationMode.AUTO;
	private SharedCacheMode sharedCacheMode = SharedCacheMode.UNSPECIFIED;

	public JPAPersistenceUnitInfo(URL unitRootUrl, PersistenceSchemaVersion xmlVersion,
			PersistenceVersion persistenceVersion, String unitName, PersistenceUnitTransactionType transactionType) {
		this.persistenceUnitRootUrl = unitRootUrl;
		this.xmlVersion = xmlVersion;
		this.persistenceVersion = persistenceVersion;
		this.unitName = unitName;
		this.transactionType = transactionType;
	}

	public String getPersistenceUnitName() {
		return unitName;
	}

	public String getPersistenceProviderClassName() {
		return persistenceProviderClassName;
	}

	public PersistenceUnitTransactionType getTransactionType() {
		return transactionType;
	}

	public DataSource getJtaDataSource() {
		return null;
	}

	public DataSource getNonJtaDataSource() {
		return null;
	}

	public List<String> getMappingFileNames() {
		return mappingFileNames;
	}

	public List<URL> getJarFileUrls() {
		return jarFileUrls;
	}

	public URL getPersistenceUnitRootUrl() {
		return persistenceUnitRootUrl;
	}

	public List<String> getManagedClassNames() {
		return managedClasses;
	}

	public boolean excludeUnlistedClasses() {
		return excludeUnlistedClasses;
	}

	public SharedCacheMode getSharedCacheMode() {
		return sharedCacheMode;
	}

	public ValidationMode getValidationMode() {
		return validationMode;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getPersistenceXMLSchemaVersion() {
		return xmlVersion.getVersion();
	}

	public ClassLoader getClassLoader() {
		return ThreadLocal.class.getClassLoader();
	}

	public void addTransformer(ClassTransformer transformer) {
		classTransformers.add(transformer);
	}

	public ClassLoader getNewTempClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	void setProperty(String name, String value) {
		properties.put(name, value);
	}

	void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
		this.excludeUnlistedClasses = excludeUnlistedClasses;
	}

	void addManagedClass(String clazz) {
		managedClasses.add(clazz);
	}

	void addJarFileUrl(URL jarFileUrl) {
		jarFileUrls.add(jarFileUrl);
	}

	void addMappingFilesNames(String mappingFilesName) {
		mappingFileNames.add(mappingFilesName);
	}

	String getPersistenceDescription() {
		return persistenceDescription;
	}

	void setPersistenceDescription(String persistenceDescription) {
		this.persistenceDescription = persistenceDescription;
	}

	void setPersistenceProviderClassName(String persistenceProviderClassName) {
		this.persistenceProviderClassName = persistenceProviderClassName;
	}

	String getPersistenceJtaDataSource() {
		return persistenceJtaDataSource;
	}

	void setPersistenceJtaDataSource(String persistenceJtaDataSource) {
		this.persistenceJtaDataSource = persistenceJtaDataSource;
	}

	String getPersistenceNonJtaDataSource() {
		return persistenceNonJtaDataSource;
	}

	void setPersistenceNonJtaDataSource(String persistenceNonJtaDataSource) {
		this.persistenceNonJtaDataSource = persistenceNonJtaDataSource;
	}

	PersistenceVersion getPersistenceVersion() {
		return persistenceVersion;
	}

	void setValidationMode(String mode) {
		if (mode.equals(NONE)) {
			validationMode = ValidationMode.NONE;
		} else if (mode.equals(AUTO)) {
			validationMode = ValidationMode.AUTO;
		} else if (mode.equals(CALLBACK)) {
			validationMode = ValidationMode.CALLBACK;
		}
	}

	void setSharedCacheMode(String mode) {
		if (mode.equals(ALL)) {
			sharedCacheMode = SharedCacheMode.ALL;
		} else if (mode.equals(NONE)) {
			sharedCacheMode = SharedCacheMode.NONE;
		} else if (mode.equals(UNSPECIFIED)) {
			sharedCacheMode = SharedCacheMode.UNSPECIFIED;
		} else if (mode.equals(ENABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.ENABLE_SELECTIVE;
		} else if (mode.equals(DISABLE_SELECTIVE)) {
			sharedCacheMode = SharedCacheMode.DISABLE_SELECTIVE;
		}
	}

	public final Set<ClassTransformer> getClassTransformers() {
		return classTransformers;
	}

	@Override
	public String toString() {
		return "JPAPersistenceUnitInfo [unitName=" + unitName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unitName == null) ? 0 : unitName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JPAPersistenceUnitInfo other = (JPAPersistenceUnitInfo) obj;
		if (unitName == null) {
			if (other.unitName != null)
				return false;
		} else if (!unitName.equals(other.unitName))
			return false;
		return true;
	}

}
