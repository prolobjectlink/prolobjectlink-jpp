/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.prolobjectlink.db.DatabaseProperties;
import org.prolobjectlink.db.DatabaseUnitInfo;

public abstract class AbstractUnitInfo implements DatabaseUnitInfo {

	// persistence xml location
	private final URL persistenceUnitRootUrl;

	// xml headers
	private final PersistenceSchemaVersion xmlVersion;
	private final PersistenceVersion persistenceVersion;

	// persistence unit attributes
	private final String unitName;

	//
	private final List<URL> jarFileUrls = new ArrayList<URL>();
	private final List<String> managedClasses = new ArrayList<String>();
	private final Properties properties = new DatabaseProperties();
	private final List<String> mappingFileNames = new ArrayList<String>();

	//
	private String persistenceDescription;
	private String persistenceProviderClassName;
	private String persistenceJtaDataSource;
	private String persistenceNonJtaDataSource;
	private boolean excludeUnlistedClasses = true;

	public AbstractUnitInfo(URL unitRootUrl, PersistenceSchemaVersion xmlVersion, PersistenceVersion persistenceVersion,
			String unitName) {
		this.persistenceVersion = persistenceVersion;
		this.persistenceUnitRootUrl = unitRootUrl;
		this.xmlVersion = xmlVersion;
		this.unitName = unitName;
	}

	public final String getPersistenceUnitName() {
		return unitName;
	}

	public final String getPersistenceProviderClassName() {
		return persistenceProviderClassName;
	}

	public final DataSource getJtaDataSource() {
		return null;
	}

	public final DataSource getNonJtaDataSource() {
		return null;
	}

	public final List<String> getMappingFileNames() {
		return mappingFileNames;
	}

	public final List<URL> getJarFileUrls() {
		return jarFileUrls;
	}

	public final URL getPersistenceUnitRootUrl() {
		return persistenceUnitRootUrl;
	}

	public final List<String> getManagedClassNames() {
		return managedClasses;
	}

	public final boolean excludeUnlistedClasses() {
		return excludeUnlistedClasses;
	}

	public final Properties getProperties() {
		return properties;
	}

	public final String getPersistenceXMLSchemaVersion() {
		return xmlVersion.getVersion();
	}

	public final ClassLoader getClassLoader() {
		return ThreadLocal.class.getClassLoader();
	}

	public final ClassLoader getNewTempClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	public final void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public final void setExcludeUnlistedClasses(boolean excludeUnlistedClasses) {
		this.excludeUnlistedClasses = excludeUnlistedClasses;
	}

	public final void addManagedClass(String clazz) {
		managedClasses.add(clazz);
	}

	public final void addJarFileUrl(URL jarFileUrl) {
		jarFileUrls.add(jarFileUrl);
	}

	public final void addMappingFilesNames(String mappingFilesName) {
		mappingFileNames.add(mappingFilesName);
	}

	public final String getPersistenceDescription() {
		return persistenceDescription;
	}

	public final void setPersistenceDescription(String persistenceDescription) {
		this.persistenceDescription = persistenceDescription;
	}

	public final void setPersistenceProviderClassName(String persistenceProviderClassName) {
		this.persistenceProviderClassName = persistenceProviderClassName;
	}

	public final String getPersistenceJtaDataSource() {
		return persistenceJtaDataSource;
	}

	public final void setPersistenceJtaDataSource(String persistenceJtaDataSource) {
		this.persistenceJtaDataSource = persistenceJtaDataSource;
	}

	public final String getPersistenceNonJtaDataSource() {
		return persistenceNonJtaDataSource;
	}

	public final void setPersistenceNonJtaDataSource(String persistenceNonJtaDataSource) {
		this.persistenceNonJtaDataSource = persistenceNonJtaDataSource;
	}

	public final PersistenceVersion getPersistenceVersion() {
		return persistenceVersion;
	}

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((unitName == null) ? 0 : unitName.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractUnitInfo other = (AbstractUnitInfo) obj;
		if (unitName == null) {
			if (other.unitName != null)
				return false;
		} else if (!unitName.equals(other.unitName)) {
			return false;
		}
		return true;
	}

}
