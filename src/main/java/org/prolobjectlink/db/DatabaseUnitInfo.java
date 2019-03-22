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

package org.prolobjectlink.db;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

public interface DatabaseUnitInfo {

	public String getPersistenceUnitName();

	public String getPersistenceProviderClassName();

	public DataSource getJtaDataSource();

	public DataSource getNonJtaDataSource();

	public List<String> getMappingFileNames();

	public List<URL> getJarFileUrls();

	public URL getPersistenceUnitRootUrl();

	public List<String> getManagedClassNames();

	public boolean excludeUnlistedClasses();

	public Properties getProperties();

	public String getPersistenceXMLSchemaVersion();

	public ClassLoader getClassLoader();

	public ClassLoader getNewTempClassLoader();
}
