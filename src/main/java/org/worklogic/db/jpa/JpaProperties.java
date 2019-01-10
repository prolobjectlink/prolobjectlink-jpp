/*
 * #%L
 * prolobjectlink-jpp
 * %%
 * Copyright (C) 2012 - 2017 WorkLogic Project
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
package org.worklogic.db.jpa;

import java.util.Map;
import java.util.Properties;

public final class JpaProperties extends Properties {

	private static final long serialVersionUID = -2013758241662724837L;

	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String URL = "javax.persistence.jdbc.url";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String PASSWORD = "javax.persistence.jdbc.password";

	public JpaProperties() {
	}

	/**
	 * Checks properties
	 * 
	 * @param properties
	 */
	public JpaProperties(final Map<String, Object> properties) {
		if (properties == null) {
			throw new IllegalStateException("Map properties for entity manager should not be null");
		} else if (!properties.containsKey(DRIVER)) {
			throw new IllegalStateException("DRIVER propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(URL)) {
			throw new IllegalStateException("URL propertiy for entity manager should not be null or empty");
		} else if (!properties.containsKey(USER)) {
			throw new IllegalStateException("User propertiy for entity manager should not be null or empty");
		} else {
			putAll(properties);
		}
	}

	public String getDriver() {
		return getProperty(DRIVER);
	}

	public String getURL() {
		return getProperty(URL);
	}

	public String getUser() {
		return getProperty(USER);
	}

	public String getPassword() {
		return getProperty(PASSWORD);
	}

}
