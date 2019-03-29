/*-
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2019 Prolobjectlink Project
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
