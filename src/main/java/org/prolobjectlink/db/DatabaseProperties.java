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
package org.prolobjectlink.db;

import java.util.Map;
import java.util.Properties;

public final class DatabaseProperties extends Properties {

	private static final long serialVersionUID = -2013758241662724837L;

	public static final String DRIVER = "javax.persistence.jdbc.driver";
	public static final String URL = "javax.persistence.jdbc.url";
	public static final String USER = "javax.persistence.jdbc.user";
	public static final String PASSWORD = "javax.persistence.jdbc.password";

	public DatabaseProperties() {
	}

	/**
	 * Checks properties
	 * 
	 * @param properties
	 */
	public DatabaseProperties(final Map<String, Object> properties) {
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
